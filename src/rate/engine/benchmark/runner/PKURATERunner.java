package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.SampleEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午4:52
 */
public class PKURATERunner extends AbstractRunner {

    private static final Logger logger = Logger.getLogger(PKURATERunner.class);
    private static final int refreshFreq = 10;
    private static final int ENROLL = 0;
    private static final int MATCH = 1;
    private Date date = new Date();
    private int totalTurn;
    private int passedTurn = 0;
    private long startTime = 0;

    private String tempEnrollResultPath;
    private String tempMatchResultFilePath;
    private String tempOutputFilePath;
    private String taskEnrollResultFilePath;
    private String taskMatchResultFilePath;
    private PrintWriter enrollResultWriter;
    private PrintWriter matchResultWriter;

    public void setTask(TaskEntity task) throws Exception {
        super.setTask(task);
        tempEnrollResultPath = FilenameUtils.concat(task.getTempDirPath(), "enroll.txt");
        tempMatchResultFilePath = FilenameUtils.concat(task.getTempDirPath(), "match.txt");
        tempOutputFilePath = FilenameUtils.concat(task.getTempDirPath(), "temp.txt");
        taskEnrollResultFilePath = FilenameUtils.concat(task.getDirPath(), "enroll.txt");
        taskMatchResultFilePath = task.getResultFilePath();
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
        enrollResultWriter = new PrintWriter(new FileWriter(tempEnrollResultPath));
        matchResultWriter = new PrintWriter(new FileWriter(tempMatchResultFilePath));
    }

    private Boolean enroll(String uuid, String imgPath) throws Exception {
            // Enroll
        String templatePath = FilenameUtils.concat(task.getTempDirPath(), uuid+".t");
        if (!(new File(templatePath).exists())) {
            String enrollCmd = genCmdFromLines(imgPath, templatePath, ENROLL);
            Process process = Runtime.getRuntime().exec(enrollCmd);
            process.waitFor();

            int exitVal = process.exitValue();

            if (exitVal == 0) {
                enrollResultWriter.println(String.format("%s ok", uuid));
                return true;
            } else {
                return false;
            }
        }else {
            return true;
        }
    }

    private Boolean match(String s1uuid, String s2uuid, String GorI) throws Exception {
        String template1Path = FilenameUtils.concat(task.getTempDirPath(), s1uuid + ".t");
        String template2Path = FilenameUtils.concat(task.getTempDirPath(), s2uuid + ".t");
        String matchCmd = genCmdFromLines(template1Path, template2Path, MATCH);

        Process process = Runtime.getRuntime().exec(matchCmd);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();

        Double score = Double.parseDouble(stdInput.readLine().trim());

        if (process.exitValue() != 0) {
            matchResultWriter.println(String.format("%s %s failed", s1uuid, s2uuid));
            return false;
        } else {
            matchResultWriter.println(String.format("%s %s %s ok %f", s1uuid, s2uuid, GorI,score));
            return true;
        }
    }

    private void runCommands() throws Exception {
        // TODO: Read the whole txt into memory may lead to OutOfMemoryException.
        List<String> lines = FileUtils.readLines(new File(benchmark.filePath()));
        totalTurn = lines.size()/3;

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
            String[] spline1 = line1.split(" ");
            String line2 = StringUtils.strip(lines.get(i + 1));
            String line3 = StringUtils.strip(lines.get(i + 2));
            logger.trace(line1);
            DebugUtil.debug(line1);

            String s1uuid = spline1[0];
            String s2uuid = spline1[1];
            String enrollImgPath1 = FilenameUtils.concat(RateConfig.getSampleRootDir(), line2);
            String enrollImgPath2 = FilenameUtils.concat(RateConfig.getSampleRootDir(), line3);

            logger.trace("Begin enroll " + s1uuid + " " + s2uuid);
            enrollFailed = !(enroll(s1uuid, enrollImgPath1) && enroll(s2uuid, enrollImgPath2));
            logger.trace("Enroll finished");
            if (enrollFailed) {
                logger.trace("Enroll failed!");
            };
            logger.trace("Begin match " + s1uuid + " " + s2uuid);
            match(s1uuid, s2uuid, spline1[2]);
            logger.trace("Match finished");
        }
        matchResultWriter.flush();
        enrollResultWriter.flush();
        FileUtils.copyFile(new File(tempMatchResultFilePath), new File(taskMatchResultFilePath));
        FileUtils.copyFile(new File(tempEnrollResultPath), new File(taskEnrollResultFilePath));

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

    private String genCmdFromLines(String filePath1, String filePath2, int type) {
        // type, 0 for enroll, 1 for match
        String exe = "";
        // This is a enroll task
        if (type == ENROLL) {
            exe = taskResult.getEnrollExeFilePath();
        } else if (type == MATCH) {
            exe = taskResult.getMatchExeFilePath();
        } else {
            return "";
        }

        // enroll.exe image template config output
        // match.exe image template config output
        List<String> list = new ArrayList<String>();
        list.add(AbstractRunner.getRateRunnerPath());
        list.add(getTimeLimit());
        list.add(getMemLimit());
        list.add(exe);
        list.add(filePath1);
        list.add(filePath2);
        return StringUtils.join(list, " ");
    }

    private String templatePath(String uuid) {
        return FilenameUtils.concat(task.getTempDirPath(), uuid+".t");
    }



    public String genLog(String uuid1, String uuid2) throws Exception {
        logger.trace(String.format("Generate log for sample %s and %s", uuid1, uuid2));
        Random random = new Random();
        String tempOutputPath = FilenameUtils.concat(RateConfig.getTempRootDir(), String.format("task-log-%d", random.nextInt()));
        File file = new File(tempOutputPath);

        SampleEntity sample1 = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", uuid1).list().get(0);
        SampleEntity sample2 = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", uuid2).list().get(0);

        String cmd = genCmdFromLines(sample1.getFilePath(), templatePath(sample1.getUuid()), ENROLL);
        String cmd2 = genCmdFromLines(sample2.getFilePath(), templatePath(sample2.getUuid()), ENROLL);
        String cmd3 = genCmdFromLines(sample1.getFilePath(), templatePath(sample2.getFilePath()), MATCH);

        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        process = Runtime.getRuntime().exec(cmd2);
        process.waitFor();
        process = Runtime.getRuntime().exec(cmd3);

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

    public void cleanUp() throws Exception {
        enrollResultWriter.close();
        matchResultWriter.close();
        super.cleanUp();
    }
}
