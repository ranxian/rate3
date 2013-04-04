package rate.engine.benchmark.runner;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import rate.engine.benchmark.analyzer.FMRAnalyzer;
import rate.engine.benchmark.analyzer.FNMRAnalyzer;
import rate.engine.task.SLSBTask;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        frrBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FRR");
        farBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FAR");
        String benchDescFile = FilenameUtils.concat(benchmark.dirPath(), "desc.txt");
        BufferedReader rd = new BufferedReader(new FileReader(benchDescFile));
        String[] params = rd.readLine().trim().split(" ");
        this.B4Frr = Integer.parseInt(params[0]);
        this.B4Far = Integer.parseInt(params[1]);
        rd.close();
    }

    public void run() throws Exception {
        DebugUtil.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

        if (! benchmark.getType().equals(algorithmVersion.getAlgorithm().getType())) {
            throw new RunnerException("Type does not match.");
        }

        this.prepare();

        DebugUtil.debug("Begin: run commands");
        this.runEachBenchmark();
        DebugUtil.debug("Finished: run commands");

        this.cleanUp();

        DebugUtil.debug("Begin: calculation");
        this.analyzeAll();
        DebugUtil.debug("Finished: calculation");

        task.setFinished(HibernateUtil.getCurrentTimestamp());
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
    }

    public void runEachBenchmark()throws Exception {
        // run frr
        for (int i = 1; i <= B4Frr; i++) {
            runCommands(getFrrBenchmark(i), slsbTask.getFrrResultPathByNum(i));
        }

        // run far
        for (int i = 1; i <= B4Far; i++) {
            for (int j = 1; j <= B4Far; j++) {
                runCommands(getFarBenchmark(i, j), slsbTask.getFarResultPathByNum(i, j));
            }
        }
    }

    public String getFrrBenchmark(int i) {
        return FilenameUtils.concat(frrBenchmarkDir, i + ".txt");
    }

    public String getFarBenchmark(int i, int j) {
        return FilenameUtils.concat(farBenchmarkDir, i +""+j+ ".txt");
    }

    public void prepare() throws Exception {
        super.prepare();
        prepareFarResult();
        prepareFrrResult();
        new File(tempOutputFilePath).createNewFile();
    }

    public void analyzeAll() throws Exception {
        calcFarForEach();
        calcFar();
        calcFrrForEach();
        calcFrr();
    }

    public void calcFar() throws  Exception {
        // Calcu subset far
        BufferedReader rd = new BufferedReader(new FileReader(slsbTask.getFarResultPath()));
        BufferedWriter wr = new BufferedWriter(new FileWriter(slsbTask.getResultFilePath()));
        List<Double> lowers = new ArrayList<Double>();
        List<Double> uppers = new ArrayList<Double>();
        int lowerBound = (int) (B4Far * 0.05 + 0.5);
        int upperBound = (int) (B4Frr * 0.95);

        for (int i = 1; i <= B4Far; i++) {
            List<Double> farList = new ArrayList<Double>();
            for (int j = 1; j <= B4Far; j++) {
                farList.add(Double.parseDouble(rd.readLine()));
            }
            Collections.sort(farList);
            lowers.add(farList.get(lowerBound));
            uppers.add(farList.get(upperBound));
        }

        double sum = 0;
        for (double l : lowers) {
            sum += l;
        }
        wr.append(sum/B4Far + "\r\n");
        sum = 0;
        for (double u : uppers) {
            sum += u;
        }
        wr.append(sum/B4Frr + "\r\n");
        wr.close();
        rd.close();
    }

    public void calcFarForEach() throws Exception {
        for (int i = 1; i <= B4Far; i++) {
            for (int j = 1; j <= B4Far; j++) {
                FNMRAnalyzer analyzer = new FNMRAnalyzer(slsbTask.getFarResultPathByNum(i, j), slsbTask.getFarResultPath());
                analyzer.analyze();
            }
        }
    }

    public void calcFrr() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(slsbTask.getResultFilePath()));
        List<String> frrs = FileUtils.readLines(new File(slsbTask.getFarResultPath()));
        ArrayList<Double> arrayList = new ArrayList();
        for (String line : frrs) {
            arrayList.add(Double.parseDouble(line));
        }
        Collections.sort(arrayList);

        int lowerBound = (int)(B4Frr * 0.05 + 0.5);
        int upperBound = (int)(B4Frr * 0.95);

        writer.append(arrayList.get(lowerBound).toString());
        writer.append("\r\n");
        writer.append(arrayList.get(upperBound).toString());
        writer.append("\r\n");
        writer.close();
    }

    public void calcFrrForEach() throws Exception {
        for (int i = 1; i <= B4Frr; i++) {
            FMRAnalyzer analyzer = new FMRAnalyzer(slsbTask.getFrrResultPathByNum(i), slsbTask.getFrrResultPath());
            analyzer.analyze();
        }
    }

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
                        // resultPw.println(String.format("%s 0", line1));
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
