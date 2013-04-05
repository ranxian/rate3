package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import rate.engine.benchmark.analyzer.FMRAnalyzer;
import rate.engine.benchmark.analyzer.FNMRAnalyzer;
import rate.engine.task.GeneralTask;
import rate.model.SampleEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
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

public class GeneralRunner
        extends AbstractRunner
        implements Comparator<String>
{

    private static final Logger logger = Logger.getLogger(GeneralRunner.class);
    private static final int refreshFreq = 10;
    private Date date = new Date();
    private int totalTurn;
    private int passedTurn = 0;
    private long startTime = 0;

    private GeneralTask GeneralTask;

    private String tempOutputFilePath;

    private double EER_l;
    private double EER_h;
    private double EER;

    public GeneralRunner() {
    }

    public void setTask(TaskEntity task) throws Exception {
        super.setTask(task);
        GeneralTask = new GeneralTask(task);
        tempOutputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");
    }

    public void run() throws Exception {
        DebugUtil.debug("I am here");
        logger.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

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
        totalTurn = lines.size()/3;
        int nRefreshTurn = totalTurn / refreshFreq    ;

        File resultFile = new File(GeneralTask.getResultFilePath());
        PrintWriter resultPw = new PrintWriter(resultFile);

        logger.debug(String.format("OutputFilePath [%s]", tempOutputFilePath));
        logger.debug(String.format("ResultFilePath [%s]", task.getResultFilePath()));

        boolean enrollFailed = false;
        startTime = System.currentTimeMillis();

        for (int i=0; i<lines.size(); i+=3) {
            passedTurn = (i+3)/3;
            if (passedTurn % refreshFreq == 0) {
                analyzeAll();
                updateTaskState(passedTurn, totalTurn, date.toString());
            } else {
                updateTaskState(passedTurn, totalTurn, null);
            }
            String line1 = StringUtils.strip(lines.get(i));
            String line2 = StringUtils.strip(lines.get(i + 1));
            String line3 = StringUtils.strip(lines.get(i + 2));
            String outputLine;
            logger.trace(line1);

            String s1uuid = line1.split(" ")[0];
            String enrollImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), line2);
            String matchImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), line3);
            String templateFilePath = FilenameUtils.concat(task.getTempDirPath(), "template-"+s1uuid);

            String enrollCmd = this.genCmdFromLines(enrollImgPath, templateFilePath, 0);
            String matchCmd  = this.genCmdFromLines(matchImgPath, templateFilePath, 1);

            if (!(new File(templateFilePath).exists())) {
                // Enroll
                Process process = Runtime.getRuntime().exec(enrollCmd);
                process.waitFor();
                outputLine = StringUtils.strip(RateConfig.getLastLine(tempOutputFilePath));
                try {
                    String rs[] = StringUtils.split(outputLine, " ");
                    if (rs[0].equals(enrollImgPath)
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

            // Match
            Process process2 = Runtime.getRuntime().exec(matchCmd);
            process2.waitFor();
            outputLine = StringUtils.strip(RateConfig.getLastLine(tempOutputFilePath));
            try {
                String rs[] = StringUtils.split(outputLine, " ");
                if (rs[0].equals(matchImgPath)
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

    public void updateTaskState(int finishedTurn, int totalTurn, String updated_time) throws Exception {
        File taskStateFile = new File(GeneralTask.getTaskStatePath());
        if (updated_time == null) {
            BufferedReader reader = new BufferedReader(new FileReader(taskStateFile));
            updated_time = reader.readLine();
            reader.close();
        }
        writeTaskState(finishedTurn, totalTurn, updated_time);
    }

    public void writeTaskState(int finishedTurn, int totalTurn, String updated_time) throws Exception {
        File taskStateFile = new File(GeneralTask.getTaskStatePath());
        FileWriter writer = new FileWriter(taskStateFile);

        // 清空原纪录
        writer.write("");
        writer.append(updated_time + "\r\n");
        writer.append(String.valueOf(finishedTurn));
        writer.append(" " + totalTurn);
        writer.append(" " + startTime);
        writer.close();
    }

    public void prepare() throws Exception {
        super.prepare();
        prepareBadResultDir();
        createResultFile();
        prepareTaskState();
    }

    public void createResultFile() throws IOException{
        File resultFile = new File(GeneralTask.getResultFilePath());
        resultFile.createNewFile();
    }

    public void prepareTaskState() throws Exception{
        File taskStateFile = new File(GeneralTask.getTaskStatePath());
        taskStateFile.createNewFile();
        writeTaskState(0, totalTurn, date.toString());
        analyzeAll();
    }

    public void prepareBadResultDir() throws Exception {
        logger.debug("preparing bad result path");
        File genuineResultPath = new File(GeneralTask.getGenuineResultPath());
        File imposterResultPath = new File(GeneralTask.getImposterResultPath());

        FileUtils.forceMkdir(genuineResultPath);
        FileUtils.forceMkdir(imposterResultPath);
        logger.debug(genuineResultPath.getAbsolutePath());
        logger.debug(imposterResultPath.getName());
    }

    private String genCmdFromLines(String imgFilePath, String templateFilePath, int type) {
        // type, 0 for enroll, 1 for match
        String exe = "";
        if (type == 0) {
            exe = GeneralTask.getEnrollExeFilePath();
        }
        else if (type == 1) {
            exe = GeneralTask.getMatchExeFilePath();
        }
        else {
            return "";
        }

        // enroll.exe image template config output
        // match.exe image template config output
        // config is 0 now
        List<String> list = new ArrayList<String>();
        list.add(AbstractRunner.getRateRunnerPath());
        list.add(getTimeLimit());
        list.add(getMemLimit());
        list.add(GeneralTask.getStdoutPath());
        list.add(GeneralTask.getStderrPath());
        list.add(GeneralTask.getPurfPath());
        list.add(exe);
        list.add(imgFilePath);
        list.add(templateFilePath);
        list.add("0");
        list.add(tempOutputFilePath);

        return StringUtils.join(list, " ");
    }

    private String genLog(String s1, String s2, String config) throws Exception {
        logger.trace(String.format("Generate log for sample %s and %s", s1, s2));
        SampleEntity sample1 = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", s1).list().get(0);
        SampleEntity sample2 = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", s2).list().get(0);
        String filePath1 = FilenameUtils.concat(RateConfig.getSampleRootDir(), sample1.getFilePath());
        String filePath2 = FilenameUtils.concat(RateConfig.getSampleRootDir(), sample2.getFilePath());
        Random random = new Random();
        String tempOutputPath = FilenameUtils.concat(RateConfig.getTempRootDir(), String.format("task-log-%d", random.nextInt()));
        File file = new File(tempOutputPath);
        // 为了满足程序需要的参数
        file.createNewFile();
        String cmd = GeneralTask.getMatchExeFilePath() + " " + filePath1 + " " + filePath2 + " " + config + " " + tempOutputPath;
        logger.debug("run command " + cmd);
        Process process = Runtime.getRuntime().exec(cmd);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();
        StringBuffer lines = new StringBuffer();
        String line;
        while ((line = stdInput.readLine()) != null) {
            lines.append(line).append("\r\n");
        }
        stdInput.close();
        file.delete();
        return lines.toString();
    }

    public void analyzeAll() throws Exception {
        this.splitAndSortResult();
        // logger.trace("Begin: calc FMR");
        FMRAnalyzer fmrAnalyzer = new FMRAnalyzer(GeneralTask.getImposterFilePath(), GeneralTask.getFmrFilePath());
        fmrAnalyzer.analyze();
        // logger.trace("Finished: calc FMR");
        // logger.trace("Begin: calc FNMR");
        FNMRAnalyzer fnmrAnalyzer = new FNMRAnalyzer(GeneralTask.getGenuineFilePath(), GeneralTask.getFnmrFilePath());
        fnmrAnalyzer.analyze();
        // logger.trace("Finished: calc FNMR");

        //logger.trace("Begin: calc error rates");
        this.calcErrorRates();
//        logger.trace("Finished: calc error rates");
//        logger.trace("Begin: calc roc");
        this.calcRoc();
//        logger.trace("Finished: calc roc");
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
        BufferedReader resultReader = new BufferedReader(new FileReader(GeneralTask.getResultFilePath()));
        List<String> genuineList = new ArrayList<String>();
        List<String> imposterList = new ArrayList<String>();
        while (true) {
            String line = resultReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);

//            logger.trace(String.format("splitting: [%s]", line));

            String rs[] = line.split(" ");
            if (rs.length == 4) continue;
            if ((!rs[2].equals("G")) && (!rs[2].equals("I"))) throw new Exception("Genuine or Imposter not known in result.txt");

            String sample1Uuid = rs[0], sample2Uuid = rs[1], type = rs[2];
            double matchScore = Double.parseDouble(rs[4]);
            String newLine = String.format("%s %s %f", sample1Uuid, sample2Uuid, matchScore);
            if (type.equals("G")) {
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
        File genuineFile = new File(GeneralTask.getGenuineFilePath());
        File imposterFile = new File(GeneralTask.getImposterFilePath());
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

        // 各个事件之间开始有点纠缠了，重构的时候注意，比如下面产生 bad result 的活动应该从统计结果中抽出来
        if (passedTurn == totalTurn) {
            calcBadResult(genuineList, imposterList);
        }
    }

    private void calcFMR() throws Exception {
//        logger.trace(String.format("fmrFilePath is set to [%s]", fmrFilePath));
        BufferedReader imposterReader = new BufferedReader(new FileReader(GeneralTask.getImposterFilePath()));
        File fmrFile = new File(GeneralTask.getFmrFilePath());
        fmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fmrFile);

        int countOfLines = RateConfig.getCountOfLines(GeneralTask.getImposterFilePath());
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
        reader.close();
        return errorRate;
    }

    private double getFMRonThreshold(double thresholdIn) throws Exception {
        return getErrorRateOnThreshold(thresholdIn, GeneralTask.getFmrFilePath());
    }

    private double getFNMRonThreshold(double thresholdIn) throws Exception {
        return getErrorRateOnThreshold(thresholdIn, GeneralTask.getFnmrFilePath());
    }

    // When FNMR is set to fnmr, what is the FMR
    private double findFMRonFNMR(double fnmr) throws Exception {
        BufferedReader fnmrReader = new BufferedReader(new FileReader(GeneralTask.getFnmrFilePath()));
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
        BufferedReader fmrReader = new BufferedReader(new FileReader(GeneralTask.getFmrFilePath()));
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
        Scanner fIn1 = new Scanner(new FileInputStream(GeneralTask.getFmrFilePath()));
        Scanner fIn2 = new Scanner(new FileInputStream(GeneralTask.getFnmrFilePath()));

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

        PrintWriter errorRatePw = new PrintWriter(new File(GeneralTask.getErrorRateFilePath()));
        errorRatePw.println(String.format("%f %f %f", EER, EER_l, EER_h));
        errorRatePw.println(FMR100);
        errorRatePw.println(FMR1000);
        errorRatePw.println(zeroFMR);
        errorRatePw.println(zeroFNMR);
        errorRatePw.close();
    }

    private void calcRoc() throws Exception {
        Scanner fIn1 = new Scanner(new FileInputStream(GeneralTask.getFmrFilePath()));
        Scanner fIn2 = new Scanner(new FileInputStream(GeneralTask.getFnmrFilePath()));

        PrintWriter fOut = new PrintWriter(
                new FileWriter(new File(GeneralTask.getRocFilePath())));

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

    public void calcBadResult(List<String> genuineList, List<String> imposterList) throws Exception{
        logger.debug("Begin Calculate bad result");
        logger.debug("GenuineList size: " + genuineList.size());
        for (int i = 0; i < 10 && i < genuineList.size(); i++) {
            File output = new File(GeneralTask.getLogPathByTypeNumber("genuine", String.valueOf(i + 1)));
            output.createNewFile();
            output = new File(GeneralTask.getLogPathByTypeNumber("imposter", String.valueOf(i + 1)));
            output.createNewFile();
        }
        logger.debug("Bad result file created");
        int i;
        logger.debug("Begin calc genuine bad result");
        for (i = 0; i < 10 && i < genuineList.size(); i++) {
            String line = genuineList.get(i);
            String info[] = line.split(" ");
            String s1 = info[0], s2 = info[1];

            PrintWriter fileWriter = new PrintWriter(GeneralTask.getLogPathByTypeNumber("genuine", String.valueOf(i + 1)));
            fileWriter.println(line);
            String log = genLog(s1, s2, "0");
            fileWriter.println(log);
            fileWriter.close();
        }
        logger.debug("Begin calc imposter bad result");
        for (i = imposterList.size() - 1; i >= 0 && i >= imposterList.size() - 10; i--) {
            String line = imposterList.get(i);
            String info[] = line.split(" ");
            String  s1 = info[0], s2 = info[1];

            PrintWriter fileWriter = new PrintWriter(GeneralTask.getLogPathByTypeNumber("imposter", String.valueOf(imposterList.size() - i)));
            fileWriter.println(line);
            String log = genLog(s1, s2, "0");
            fileWriter.println(log);
            fileWriter.close();
        }
        logger.debug("bad result generated successfully");
    }
}


