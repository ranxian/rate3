package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.task.FVC2006Task;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */

public class FVC2006Runner
        extends AbstractRunner
        implements Comparator<String>
{

    private static final Logger logger = Logger.getLogger(FVC2006Runner.class);

    private final Session session = HibernateUtil.getSession();

    private FVC2006Task fvc2006Task;

    private String tempOutputFilePath;
    private String templateFilePath;
    private String imageFilePath;

    private double EER_l;
    private double EER_h;
    private double EER;

    public FVC2006Runner() {
    }

    public void setTask(TaskEntity task) throws Exception {
        super.setTask(task);
        fvc2006Task = new FVC2006Task(task);
        tempOutputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");
        templateFilePath = FilenameUtils.concat(task.getTempDirPath(), "template");
    }

    public void run() throws Exception {
        logger.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

        if (! benchmark.getProtocol().equals(algorithmVersion.getAlgorithmByAlgorithmUuid().getProtocol())) {
            throw new RunnerException("Protocol does not match.");
        }

        this.prepare();

        logger.trace("Begin: run commands");
        this.runCommands();
        logger.trace("Finished: run commands");

        this.cleanUp();

        logger.trace("Begin: calculation");
        this.analyzeAll();
        logger.trace("Finished: calculation");

        task.setFinished(HibernateUtil.getCurrentTimestamp());
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
    }

    private void runCommands() throws Exception {
        // TODO: Read the whole txt into memory may lead to OutOfMemoryException.
        List<String> lines = FileUtils.readLines(new File(benchmark.filePath()));

        File resultFile = new File(fvc2006Task.getResultFilePath());
        resultFile.createNewFile();
        PrintWriter resultPw = new PrintWriter(resultFile);

        logger.debug(String.format("OutputFilePath [%s]", tempOutputFilePath));
        logger.debug(String.format("ResultFilePath [%s]", task.getResultFilePath()));

        boolean enrollFailed = false;

        for (int i=0; i<lines.size(); i+=2) {
            String line1 = StringUtils.strip(lines.get(i));
            String line2 = StringUtils.strip(lines.get(i + 1));

            logger.trace(line1);

            if (line1.startsWith("E")) {
                // try to delete the template file generated last time
                if (new File(templateFilePath).exists()) {
                    new File(templateFilePath).delete();
                }
            }
            else if (line1.startsWith("M")) {
                if (!(new File(templateFilePath).exists()) || enrollFailed) {
                    //resultPw.println(String.format("%s -1", line1));
                    continue;
                }
            }

            String cmd = this.genCmdFromLines(line1, line2);
            logger.trace("Run command ["+cmd+"]");

            Process process = Runtime.getRuntime().exec(String.format("%s", cmd));
            process.waitFor();

            String outputLine = StringUtils.strip(RateConfig.getLastLine(tempOutputFilePath));
//            logger.trace(outputLine);
            if (line1.startsWith("E")) {
                try {
                    String rs[] = StringUtils.split(outputLine, " ");
                    if (rs[0].equals(imageFilePath)
                            && rs[1].equals(templateFilePath)
                            && rs[2].equals("OK")
                            ) {
                        resultPw.println(String.format("%s 0", line1));
                        enrollFailed = false;
                        resultPw.flush();
                    }
                    else throw new Exception(String.format("Enroll failed %s", line2));
                }
                catch (Exception ex) {
                    logger.error("", ex);
                    resultPw.println(String.format("%s -1", line1));
                    enrollFailed = true;
                }
            }
            else if (line1.startsWith("M")) {
                try {
                    String rs[] = StringUtils.split(outputLine, " ");
                    if (rs[0].equals(imageFilePath)
                            && rs[1].equals(templateFilePath)
                            && rs[2].equals("OK")
                            ) {
                        double matchScore = Double.parseDouble(rs[3]);
                        resultPw.println(String.format("%s 0 %s", line1, String.valueOf(matchScore)));
                        resultPw.flush();
                    }
                }
                catch (Exception ex) {
                    logger.error("", ex);
                    resultPw.println(String.format("%s -1", line1));
                }
            }
        }
    }

    public void prepare() throws Exception {
        super.prepare();
    }

    private String genCmdFromLines(String line1, String line2) {
        String exe = "";
        if (line1.startsWith("E")) {
            exe = fvc2006Task.getEnrollExeFilePath();
        }
        else if (line1.startsWith("M")) {
            exe = fvc2006Task.getMatchExeFilePath();
        }
        else {
            return "";
        }


        // enroll.exe image template config output
        // match.exe image template config output
        // config is 0 now
        List<String> list = new ArrayList<String>();
        list.add(exe);
        imageFilePath = FilenameUtils.concat(RateConfig.getSampleRootDir(), StringUtils.strip(line2));
        list.add(imageFilePath);
        list.add(templateFilePath);
        list.add("0");
        list.add(tempOutputFilePath);

        return StringUtils.join(list, " ");
    }

    public void analyzeAll() throws Exception {
        this.splitAndSortResult();
        logger.trace("Begin: calc FMR");
        this.calcFMR();
        logger.trace("Finished: calc FMR");
        logger.trace("Begin: calc FNMR");
        this.calcFNMR();
        logger.trace("Finished: calc FNMR");
        logger.trace("Begin: calc error rates");
        this.calcErrorRates();
        logger.trace("Finished: calc error rates");
        logger.trace("Begin: calc roc");
        this.calcRoc();
        logger.trace("Finished: calc roc");
    }

    // for sorting genuine.txt and imposter.txt
    public int compare(String s1, String s2) {
        String ss1[] = s1.split(" ");
        String ss2[] = s2.split(" ");
        double d1 = Double.parseDouble(ss1[ss1.length-1]);
        double d2 = Double.parseDouble(ss2[ss2.length-1]);
        return (d1<d2 ? -1 : (d1==d2 ? 0 : 1));
    }

    private void splitAndSortResult() throws Exception {
//        logger.trace(String.format("Result file [%s]", resultFilePath));
//        logger.trace(String.format("Genuine file [%s]", genuineFilePath));
//        logger.trace(String.format("Imposter file [%s]", imposterFilePath));

        // split and store in List<String>
        BufferedReader resultReader = new BufferedReader(new FileReader(fvc2006Task.getResultFilePath()));
        List<String> genuineList = new ArrayList<String>();
        List<String> imposterList = new ArrayList<String>();
        while (true) {
            String line = resultReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);

//            logger.trace(String.format("splitting: [%s]", line));

            String rs[] = line.split(" ");
            if (rs[0].equals("E")) continue;
            else if (! rs[0].equals("M")) throw new Exception("Unknown operator type in result.txt");
            // rs[0] is "M", rs[5] is 0 on success match and -1 on failed match
            String class1Uuid = rs[1], sample1Uuid = rs[2], class2Uuid = rs[3], sample2Uuid = rs[4];
            double matchScore = Double.parseDouble(rs[6]);
            String newLine = String.format("%s %s %s %s %f", class1Uuid, sample1Uuid, class2Uuid, sample2Uuid, matchScore);
            if (class1Uuid.equals(class2Uuid)) {
                genuineList.add(newLine);
            }
            else {
                imposterList.add(newLine);
            }
        }
        resultReader.close();

        // sort results
        Collections.sort(genuineList, this);
        Collections.sort(imposterList, this);

        // write to genuine.txt and imposter.txt
        File genuineFile = new File(fvc2006Task.getGenuineFilePath());
        File imposterFile = new File(fvc2006Task.getImposterFilePath());
        genuineFile.createNewFile();
        imposterFile.createNewFile();
        PrintWriter genuinePw = new PrintWriter(genuineFile);
        PrintWriter imposterPw = new PrintWriter(imposterFile);
        for (String line: genuineList) {
            genuinePw.println(line);
        }
        for (String line: imposterList) {
            imposterPw.println(line);
        }
        genuinePw.close();
        imposterPw.close();
    }

    private void calcFMR() throws Exception {
//        logger.trace(String.format("fmrFilePath is set to [%s]", fmrFilePath));
        BufferedReader imposterReader = new BufferedReader(new FileReader(fvc2006Task.getImposterFilePath()));
        File fmrFile = new File(fvc2006Task.getFmrFilePath());
        fmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fmrFile);

        int countOfLines = RateConfig.getCountOfLines(fvc2006Task.getImposterFilePath());
        int i=0;
        double p=-1, matchScore=0;
        while (true) {
            String line = imposterReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            matchScore = Double.parseDouble(rs[rs.length-1]);

            if (matchScore>p) {
                if (p!=-1)
                    fmrPw.println(String.format("%f %f", p, 1 - (double)i/countOfLines));
                p = matchScore;
            }
            i++;
        }
        fmrPw.println(String.format("%f 0", matchScore));
        fmrPw.close();
    }

    private void calcFNMR() throws Exception {

        BufferedReader genuineReader = new BufferedReader(new FileReader(fvc2006Task.getGenuineFilePath()));
        File fnmrFile = new File(fvc2006Task.getFnmrFilePath());
        fnmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fnmrFile);

        int countOfLines = RateConfig.getCountOfLines(fvc2006Task.getGenuineFilePath());
        int i=0;
        double p=-1, matchScore=0;
        while (true) {
            String line = genuineReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            matchScore = Double.parseDouble(rs[rs.length-1]);

            if (matchScore>p) {
                if (p!=-1)
                    fmrPw.println(String.format("%f %f", p, (double)i/countOfLines));
                p = matchScore;
            }
            i++;
        }
        fmrPw.println(String.format("%f 0", matchScore));
        fmrPw.close();
    }

    private double getErrorRateOnThreshold(double thresholdIn, String filePath) throws Exception {
//        logger.trace(String.format("filePath [%s] thresholdIn [%f]", filePath, thresholdIn));
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        double threshold = 0, errorRate = 0;
        while ((line=reader.readLine())!=null) {
            String rs[] = StringUtils.strip(line).split(" ");
            threshold = Double.parseDouble(rs[0]);
            errorRate = Double.parseDouble(rs[1]);

//            logger.trace(String.format("getErrorRateOnThreshold: filePath [%s] threshold [%f] errorRate [%f]", filePath, threshold, errorRate));

            if (threshold>=thresholdIn) break;
        }
        return errorRate;
    }

    private double getFMRonThreshold(double thresholdIn) throws Exception {
        return getErrorRateOnThreshold(thresholdIn, fvc2006Task.getFmrFilePath());
    }

    private double getFNMRonThreshold(double thresholdIn) throws Exception {
        return getErrorRateOnThreshold(thresholdIn, fvc2006Task.getFnmrFilePath());
    }

    // When FNMR is set to fnmr, what is the FMR
    private double findFMRonFNMR(double fnmr) throws Exception {
        BufferedReader fnmrReader = new BufferedReader(new FileReader(fvc2006Task.getFnmrFilePath()));
        String line;
        double threshold = 0, errorRate = 0;
        while ((line=fnmrReader.readLine())!=null) {
            String rs[] = StringUtils.strip(line).split(" ");
            threshold = Double.parseDouble(rs[0]);
            errorRate = Double.parseDouble(rs[1]);

//            logger.trace(String.format("finFMRonFNMR: filePath [%s] threshold [%f] errorRate [%f]", fnmrFilePath, threshold, errorRate));

            if (errorRate>=fnmr) break;
        }
        if (errorRate<fnmr) {
            threshold = 1;
        }

        fnmrReader.close();

        return getFMRonThreshold(threshold);
    }

    private double findFNMRonFMR(double fmr) throws Exception {
        BufferedReader fmrReader = new BufferedReader(new FileReader(fvc2006Task.getFmrFilePath()));
        String line;
        double threshold = 0, errorRate = 0;
        while ((line=fmrReader.readLine())!=null) {
            String rs[] = StringUtils.strip(line).split(" ");
            threshold = Double.parseDouble(rs[0]);
            errorRate = Double.parseDouble(rs[1]);

            if (errorRate<=fmr) break;
        }
        if (errorRate>fmr) {
            threshold = 1;
        }

        fmrReader.close();

        return getFNMRonThreshold(threshold);
    }


    // copy from Old source
    private void calcEER() throws Exception {
        Scanner fIn1 = new Scanner(new FileInputStream(fvc2006Task.getFmrFilePath()));
        Scanner fIn2 = new Scanner(new FileInputStream(fvc2006Task.getFnmrFilePath()));

        double t1 = 0.0, fmr1 = 1.0, fnmr1 = 0.0;
        double t2 = 0.0, fmr2 = 1.0, fnmr2 = 0.0;
        double t_fmr = 0.0;
        double t_fnmr = 0.0;

        if (fIn1.hasNext()) {
            t_fmr = fIn1.nextDouble();
        } else {
            t_fmr = 1.0;
        }

        if (fIn2.hasNext()) {
            t_fnmr = fIn2.nextDouble();
        } else {
            t_fnmr = 1.0;
        }

        while (fmr2 > fnmr2) {
            t1 = t2;
            fmr1 = fmr2;
            fnmr1 = fnmr2;

            if (t_fmr < t_fnmr) {
                t2 = t_fmr;

                if (t2 != 1.0) {
                    fmr2 = fIn1.nextDouble();
                } else {
                    fmr2 = 0.0;
                }

                if (fIn1.hasNext()) {
                    t_fmr = fIn1.nextDouble();
                } else {
                    t_fmr = 1.0;
                }

            } else if (t_fmr > t_fnmr) {
                t2 = t_fnmr;

                if (t2 != 1.0) {
                    fnmr2 = fIn2.nextDouble();
                } else {
                    fnmr2 = 1.0;
                }

                if (fIn2.hasNext()) {
                    t_fnmr = fIn2.nextDouble();
                } else {
                    t_fnmr = 1.0;
                }

            } else {
                t2 = t_fmr;

                if (t2 != 1.0) {
                    fmr2 = fIn1.nextDouble();
                } else {
                    fmr2 = 0.0;
                }

                if (fIn1.hasNext()) {
                    t_fmr = fIn1.nextDouble();
                } else {
                    t_fmr = 1.0;
                }

                if (t2 != 1.0) {
                    fnmr2 = fIn2.nextDouble();
                } else {
                    fnmr2 = 1.0;
                }

                if (fIn2.hasNext()) {
                    t_fnmr = fIn2.nextDouble();
                } else {
                    t_fnmr = 1.0;
                }
            }
        }

        fIn1.close();
        fIn2.close();

        if (fmr1 + fnmr1 < fmr2 + fnmr2) {
            EER_l = fnmr1;
            EER_h = fmr1;
        } else {
            EER_l = fmr2;
            EER_h = fnmr2;
        }
        EER = (EER_l+EER_h)/2;
    }

    private void calcErrorRates() throws Exception {
//        logger.trace(findFMRonFNMR(0.05));
        double FMR100 = findFMRonFNMR(0.01);
        double FMR1000 = findFMRonFNMR(0.001);
        double zeroFMR = findFMRonFNMR(0);
        double zeroFNMR = findFNMRonFMR(0);
        this.calcEER();

        logger.trace(String.format("FMR100 %f", FMR100));
        logger.trace(String.format("FMR1000 %f", FMR1000));
        logger.trace(String.format("zeroFMR %f", zeroFMR));
        logger.trace(String.format("zeroFNMR %f", zeroFNMR));
        logger.trace(String.format("EER_l %f", EER_l));
        logger.trace(String.format("EER_h %f", EER_h));

        PrintWriter errorRatePw = new PrintWriter(new File(fvc2006Task.getErrorRateFilePath()));
        errorRatePw.println(String.format("%f %f %f", EER, EER_l, EER_h));
        errorRatePw.println(FMR100);
        errorRatePw.println(FMR1000);
        errorRatePw.println(zeroFMR);
        errorRatePw.println(zeroFNMR);
        errorRatePw.close();
    }

    private void calcRoc() throws Exception {
        Scanner fIn1 = new Scanner(new FileInputStream(fvc2006Task.getFmrFilePath()));
        Scanner fIn2 = new Scanner(new FileInputStream(fvc2006Task.getFnmrFilePath()));

        PrintWriter fOut = new PrintWriter(
                new FileWriter(new File(fvc2006Task.getRocFilePath())));

        double t1 = 0.0, r1 = 1.0;
        double t2 = 0.0, r2 = 1.0;
        double t3 = 0.0, r3 = 0.0;
        double t4 = 0.0, r4 = 0.0;
        double fmr, fnmr;

        while (t2 < 1.0 || t4 < 1.0) {

            if (t2 < t4) {
                if (fIn1.hasNext()) {
                    t1 = t2;
                    r1 = r2;
                    t2 = fIn1.nextDouble();
                    r2 = fIn1.nextDouble();

                } else if (t2 < 1.0) {
                    t1 = t2;
                    r1 = r2;
                    t2 = 1.0;
                    r2 = 0.0;
                }

            } else if (t2 > t4) {

                if (fIn2.hasNext()) {
                    t3 = t4;
                    r3 = r4;
                    t4 = fIn2.nextDouble();
                    r4 = fIn2.nextDouble();
                } else if (t4 < 1.0) {
                    t3 = t4;
                    r3 = r4;
                    t4 = 1.0;
                    r4 = 1.0;
                }
            }

            if (t2 == t4) {

                if (fIn1.hasNext()) {
                    t1 = t2;
                    r1 = r2;
                    t2 = fIn1.nextDouble();
                    r2 = fIn1.nextDouble();
                } else if (t2 < 1.0) {
                    t1 = t2;
                    r1 = r2;
                    t2 = 1.0;
                    r2 = 0.0;
                }
                if (t2 == 0.0) {
                    r1 = r2;
                    continue;
                }

                if (fIn2.hasNext()) {
                    t3 = t4;
                    r3 = r4;
                    t4 = fIn2.nextDouble();
                    r4 = fIn2.nextDouble();
                } else if (t4 < 1.0) {
                    t3 = t4;
                    r3 = r4;
                    t4 = 1.0;
                    r4 = 1.0;
                }
                if (t4 == 0.0) {
                    r3 = r4;
                    continue;
                }

                fmr = r1;
                fnmr = r3;
                fOut.println(fmr + " " + fnmr);
            }

            if (t2 < t4) {
                fmr = r2;
                fnmr = (t2 - t3) * (r4 - r3) / (t4 - t3) + r3;
            } else {
                fmr = (t4 - t1) * (r2 - r1) / (t2 - t1) + r1;
                fnmr = r4;
            }

            fOut.println(fmr + " " + fnmr);
        }

        fOut.close();
        fIn2.close();
        fIn1.close();
    }
}


