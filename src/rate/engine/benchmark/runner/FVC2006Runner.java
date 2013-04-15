package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import rate.engine.task.GeneralTask;
import rate.engine.task.TaskResult;
import rate.model.SampleEntity;
import rate.model.TaskEntity;
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

public class FVC2006Runner extends AbstractRunner
{

    private static final Logger logger = Logger.getLogger(FVC2006Runner.class);
    private static final int refreshFreq = 10;
    private Date date = new Date();
    private int totalTurn;
    private int passedTurn = 0;
    private long startTime = 0;

    private String tempOutputFilePath;

    public void setTask(TaskEntity task) throws Exception {
        super.setTask(task);
        tempOutputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");
    }

    public void run() throws Exception {
        logger.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

        this.prepare();

        logger.trace("Begin: run commands");
        this.runCommands();
        logger.trace("Finished: run commands");

        this.cleanUp();
    }

    public void prepare() throws Exception {
        super.prepare();

        new File(taskResult.getTaskStatePath()).createNewFile();
    }

    private void runCommands() throws Exception {
        // TODO: Read the whole txt into memory may lead to OutOfMemoryException.
        List<String> lines = FileUtils.readLines(new File(benchmark.filePath()));
        totalTurn = lines.size()/3;

        File resultFile = new File(taskResult.getResultFilePath());
        PrintWriter resultPw = new PrintWriter(resultFile);

        logger.debug(String.format("OutputFilePath [%s]", tempOutputFilePath));
        logger.debug(String.format("ResultFilePath [%s]", task.getResultFilePath()));

        boolean enrollFailed = false;
        startTime = System.currentTimeMillis();

        for (int i=0; i<lines.size(); i+=3) {
            passedTurn = (i+3)/3;
            if (passedTurn % refreshFreq == 0) {
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

            if (enrollFailed) {
                continue;
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
        resultPw.close();
    }

    public void updateTaskState(int finishedTurn, int totalTurn, String updated_time) throws Exception {
        File taskStateFile = new File(taskResult.getTaskStatePath());
        if (updated_time == null) {
            BufferedReader reader = new BufferedReader(new FileReader(taskStateFile));
            updated_time = reader.readLine();
            reader.close();
        }
        writeTaskState(finishedTurn, totalTurn, updated_time);
    }

    public void writeTaskState(int finishedTurn, int totalTurn, String updated_time) throws Exception {
        File taskStateFile = new File(taskResult.getTaskStatePath());
        FileWriter writer = new FileWriter(taskStateFile);

        // 清空原纪录
        writer.write("");
        writer.append(updated_time + "\r\n");
        writer.append(String.valueOf(finishedTurn));
        writer.append(" " + totalTurn);
        writer.append(" " + startTime);
        writer.close();
    }

    private String genCmdFromLines(String imgFilePath, String templateFilePath, int type) {
        // type, 0 for enroll, 1 for match
        String exe = "";
        if (type == 0) {
            exe = taskResult.getEnrollExeFilePath();
        }
        else if (type == 1) {
            exe = taskResult.getMatchExeFilePath();
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
        list.add(taskResult.getStdoutPath());
        list.add(taskResult.getStderrPath());
        list.add(taskResult.getPurfPath());
        list.add(exe);
        list.add(imgFilePath);
        list.add(templateFilePath);
        list.add("0");
        list.add(tempOutputFilePath);

        return StringUtils.join(list, " ");
    }

    public String genLog(String uuid1, String uuid2) throws Exception {
        logger.trace(String.format("Generate log for sample %s and %s", uuid1, uuid2));
        SampleEntity sample1 = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", uuid1).list().get(0);
        SampleEntity sample2 = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", uuid2).list().get(0);
        String filePath1 = FilenameUtils.concat(RateConfig.getSampleRootDir(), sample1.getFilePath());
        String filePath2 = FilenameUtils.concat(RateConfig.getSampleRootDir(), sample2.getFilePath());
        Random random = new Random();
        String tempOutputPath = FilenameUtils.concat(RateConfig.getTempRootDir(), String.format("task-log-%d", random.nextInt()));
        File file = new File(tempOutputPath);
        // 为了满足程序需要的参数
        file.createNewFile();
        String templateFilePath = FilenameUtils.concat(task.getTempDirPath(), "template-"+uuid1);
        String enrollCmd = this.genCmdFromLines(filePath1, templateFilePath, 0);
        String matchCmd  = this.genCmdFromLines(filePath2, templateFilePath, 1);

        Process process = Runtime.getRuntime().exec(enrollCmd);
        process.waitFor();

        process = Runtime.getRuntime().exec(matchCmd);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
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
}
