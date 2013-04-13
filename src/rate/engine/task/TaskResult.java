package rate.engine.task;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import rate.model.TaskEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午6:35
 */
public class TaskResult {
    protected TaskEntity task;
    protected String enrollExeFilePath;
    protected String matchExeFilePath;
    protected String resultFilePath;
    protected String stdoutPath;
    protected String stderrPath;
    protected String purfPath;
    protected String taskStatePath;
    protected double finishedTurn = 0;
    protected double totalTurn = 0;
    protected String updated;
    protected long startTime;

    public TaskResult(TaskEntity task) throws Exception {
        this.task = task;
        enrollExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "match.exe");
        resultFilePath = task.getResultFilePath();
        stdoutPath = FilenameUtils.concat(task.getDirPath(), "stdout.txt");
        stderrPath = FilenameUtils.concat(task.getDirPath(), "stderr.txt");
        purfPath = FilenameUtils.concat(task.getDirPath(), "purf.txt");
        taskStatePath = FilenameUtils.concat(task.getDirPath(), "state.txt");
        if (new File(getTaskStatePath()).exists()) {
            getTaskState();
        }

    }

    public String getEnrollExeFilePath() {
        return enrollExeFilePath;
    }

    public String getMatchExeFilePath() {
        return matchExeFilePath;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }


    public String getStdoutPath() {
        return stdoutPath;
    }

    public String getStderrPath() {
        return stderrPath;
    }

    public String getPurfPath() {
        return purfPath;
    }


    public String getTaskStatePath() {
        return taskStatePath;
    }

    public double getFinishedTurn() {
        return finishedTurn;
    }

    public double getTotalTurn() {
        return totalTurn;
    }

    public String getUpdated() {
        return updated;
    }

    public String getEstimateLeftTime() {
        long curTime = System.currentTimeMillis();
        long timePast = curTime - startTime;
        int estimateMinute = (int)(timePast / finishedTurn * (totalTurn - finishedTurn) / 1000 / 60);
        int estimateHour = estimateMinute / 60;

        if (estimateHour == 0) return  estimateMinute + "mins";
        else {
            estimateMinute %= estimateHour;
            return estimateHour + "hrs " + estimateMinute + "mins";
        }
    }

    public double getPercentage() {
        if (totalTurn == 0) return 0;
        return finishedTurn / totalTurn;
    }

    public void getTaskState() throws Exception{
        BufferedReader stateReader = new BufferedReader(new FileReader(getTaskStatePath()));
        updated = StringUtils.strip(stateReader.readLine());
        String state[] = StringUtils.strip(stateReader.readLine()).split(" ");
        finishedTurn = Double.parseDouble(state[0]);
        totalTurn = Double.parseDouble(state[1]);
        startTime = Long.parseLong(state[2]);
        stateReader.close();
    }

    public TaskEntity getTask() {
        return task;
    }
}
