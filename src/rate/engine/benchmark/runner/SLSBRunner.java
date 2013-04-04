package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import rate.engine.task.SLSBTask;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午2:36
 */
public class SLSBRunner extends AbstractRunner {
    private int B4Far;
    private int B4Frr;
    private static final Logger logger = Logger.getLogger(SLSBRunner.class);
    private SLSBTask slsbTask;
    private String tempOutputFilePath;
    private String frrBenchmarkDir;
    private String farBenchmarkDir;


    public void setTask(TaskEntity task) throws Exception{
        super.setTask(task);
        this.slsbTask = new SLSBTask(task);
        tempOutputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");
        frrBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FAR");
        farBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FRR");
        String benchDescFile = FilenameUtils.concat(benchmark.dirPath(), "desc");
        BufferedReader rd = new BufferedReader(new FileReader(benchDescFile));
        String[] params = rd.readLine().trim().split(" ");
        this.B4Frr = Integer.parseInt(params[0]);
        this.B4Far = Integer.parseInt(params[1]);
    }

    public void run() throws Exception {
        logger.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

        if (! benchmark.getType().equals(algorithmVersion.getAlgorithm().getType())) {
            throw new RunnerException("Type does not match.");
        }

        this.prepare();

        logger.trace("Begin: run commands");
        this.runEachBenchmark();
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

    public void runEachBenchmark()throws Exception {
        // run frr
        for (int i = 1; i <= B4Frr; i++) {
            runCommands(getFrrBenchmark(i), slsbTask.getFrrResultDir() + "\\" + i + ".txt");
        }

        // run far
        for (int i = 1; i <= B4Far; i++) {
            runCommands(getFarBenchmark(i), slsbTask.getFarResultDir() + "\\" + i + ".txt");
        }
    }

    public String getFrrBenchmark(int i) {
        return FilenameUtils.concat(frrBenchmarkDir, i + ".txt");
    }

    public String getFarBenchmark(int i) {
        return FilenameUtils.concat(farBenchmarkDir, i + ".txt");
    }

    public void prepare() throws Exception {
        super.prepare();
        prepareFarResult();
        prepareFrrResult();
    }

    public void analyzeAll() {
        calcFarForEach();
        calcFar();
        calcFrrForEach();
        calcFrr();
    }

    public void calcFar(){

    }

    public void calcFarForEach() {

    }

    public void calcFrr() {}

    public void calcFrrForEach() {}

    public void prepareFarResult() {
        new File(slsbTask.getFarResultDir()).mkdir();
    }

    public void prepareFrrResult() {
        new File(slsbTask.getFrrResultDir()).mkdir();
    }

    private void runCommands(String benchmarkFile, String outputFile) throws Exception {
        // TODO: Read the whole txt into memory may lead to OutOfMemoryException.
        List<String> lines = FileUtils.readLines(new File(benchmarkFile));

        File resultFile = new File(outputFile);
        PrintWriter resultPw = new PrintWriter(resultFile);

        logger.debug(String.format("OutputFilePath [%s]", tempOutputFilePath));
        logger.debug(String.format("ResultFilePath [%s]", task.getResultFilePath()));

        boolean enrollFailed = false;

        for (int i=0; i<lines.size(); i+=3) {
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

    private String genCmdFromLines(String imgFilePath, String templateFilePath, int type) {
        // type, 0 for enroll, 1 for match
        String exe = "";
        if (type == 0) {
            exe = slsbTask.getEnrollExeFilePath();
        }
        else if (type == 1) {
            exe = slsbTask.getMatchExeFilePath();
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
        list.add(slsbTask.getStdoutPath());
        list.add(slsbTask.getStderrPath());
        list.add(slsbTask.getPurfPath());
        list.add(exe);
        list.add(imgFilePath);
        list.add(templateFilePath);
        list.add("0");
        list.add(tempOutputFilePath);

        return StringUtils.join(list, " ");
    }
}
