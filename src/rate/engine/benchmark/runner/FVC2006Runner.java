package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
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

    private String outputFilePath;
    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String templateFilePath;
    private String imageFilePath;
    private String resultFilePath;

    public FVC2006Runner() {
    }

    public void run() throws Exception {
        logger.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

        if (! benchmark.getProtocol().equals(algorithmVersion.getAlgorithmByAlgorithmUuid().getProtocol())) {
            throw new RunnerException("Protocol does not match.");
        }

        this.prepare();

        // TODO: Read the whole txt into memory may lead to OutOfMemoryException.
        List<String> lines = FileUtils.readLines(new File(benchmark.filePath()));

        File resultFile = new File(resultFilePath);
        resultFile.createNewFile();
        PrintWriter resultPw = new PrintWriter(resultFile);

        logger.debug(String.format("OutputFilePath [%s]", outputFilePath));
        logger.debug(String.format("ResultFilePath [%s]", task.getResultFilePath()));

        boolean enrollFailed = false;
        logger.trace(String.format("Begin to run [%s] commands", lines.size()/2));
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
                    resultPw.println(String.format("%s -1", line1));
                    continue;
                }
            }

            String cmd = this.genCmdFromLines(line1, line2);
            logger.trace("Run command ["+cmd+"]");

            Process process = Runtime.getRuntime().exec(String.format("%s", cmd));
            process.waitFor();

            String outputLine = StringUtils.strip(RateConfig.getLastLine(outputFilePath));
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
        logger.trace("Commands finished");

        this.cleanUp();

        logger.trace("Begin calculation");
        this.analyzeAll();
        logger.trace("Calculation finished");

        task.setFinished(HibernateUtil.getCurrentTimestamp());
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
    }

    public void prepare() throws Exception {
        super.prepare();

        outputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");

        enrollExeFilePath = FilenameUtils.concat(algorithmVersion.dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(algorithmVersion.dirPath(), "match.exe");

        templateFilePath = FilenameUtils.concat(task.getTempDirPath(), "template");

        resultFilePath = task.getResultFilePath();
    }

    private String genCmdFromLines(String line1, String line2) {
        String exe = "";
        if (line1.startsWith("E")) {
            exe = enrollExeFilePath;
        }
        else if (line1.startsWith("M")) {
            exe = matchExeFilePath;
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
        list.add(outputFilePath);

        return StringUtils.join(list, " ");
    }

    public void analyzeAll() throws Exception {
        this.splitAndSortResult();
        logger.trace("Begin to calc FMR");
        this.calcFNMR();
        logger.trace("Finished calc FMR");
        logger.trace("Begin to calc FNMR");
        this.calcFNMR();
        logger.trace("Finished calc FNMR");
    }

    private String genuineFilePath;
    private String imposterFilePath;

    public int compare(String s1, String s2) {
        String ss1[] = s1.split(" ");
        String ss2[] = s2.split(" ");
        double d1 = Double.parseDouble(ss1[ss1.length-1]);
        double d2 = Double.parseDouble(ss2[ss2.length-1]);
        return (d1<d2 ? -1 : (d1==d2 ? 0 : 1));
    }

    private void splitAndSortResult() throws Exception {
        genuineFilePath = FilenameUtils.concat(task.getDirPath(), "genuine.txt");
        imposterFilePath = FilenameUtils.concat(task.getDirPath(), "imposter.txt");
        logger.trace(String.format("Result file [%s]", resultFilePath));
        logger.trace(String.format("Genuine file [%s]", genuineFilePath));
        logger.trace(String.format("Imposter file [%s]", imposterFilePath));

        // split and store in List<String>
        BufferedReader resultReader = new BufferedReader(new FileReader(resultFilePath));
        List<String> genuineList = new ArrayList<String>();
        List<String> imposterList = new ArrayList<String>();
        while (true) {
            String line = resultReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);
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
        File genuineFile = new File(genuineFilePath);
        File imposterFile = new File(imposterFilePath);
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

    private String fmrFilePath;

    private void calcFMR() throws Exception {
        fmrFilePath = FilenameUtils.concat(task.getDirPath(), "fmr.txt");
        BufferedReader imposterReader = new BufferedReader(new FileReader(imposterFilePath));
        File fmrFile = new File(fmrFilePath);
        fmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fmrFile);

        int countOfLines = RateConfig.getCountOfLines(imposterFilePath);
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

    private String fnmrFilePath;
    private void calcFNMR() throws Exception {
        fnmrFilePath = FilenameUtils.concat(task.getDirPath(), "fnmr.txt");
        BufferedReader genuineReader = new BufferedReader(new FileReader(genuineFilePath));
        File fnmrFile = new File(fnmrFilePath);
        fnmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fnmrFile);

        int countOfLines = RateConfig.getCountOfLines(genuineFilePath);
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

}
