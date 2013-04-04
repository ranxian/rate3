package rate.engine.task;

import org.apache.commons.io.FilenameUtils;
import rate.model.TaskEntity;

import java.io.FilenameFilter;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午2:44
 */
public class SLSBTask extends TaskEntity {
    private String farResultDir;
    private String frrResultDir;
    private String taskStateFilePath;
    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String resultFilePath;
    private String stdoutPath;
    private String stderrPath;
    private String purfPath;

    public String getStdoutPath() {
        return stdoutPath;
    }

    public void setStdoutPath(String stdoutPath) {
        this.stdoutPath = stdoutPath;
    }

    public String getStderrPath() {
        return stderrPath;
    }

    public void setStderrPath(String stderrPath) {
        this.stderrPath = stderrPath;
    }

    public String getPurfPath() {
        return purfPath;
    }

    public void setPurfPath(String purfPath) {
        this.purfPath = purfPath;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    private TaskEntity task;

    public SLSBTask(TaskEntity task) throws Exception {
        this.task = task;
        farResultDir = FilenameUtils.concat(task.getDirPath(), "FAR");
        frrResultDir = FilenameUtils.concat(task.getDirPath(), "FRR");
        taskStateFilePath = FilenameUtils.concat(task.getDirPath(), "state.txt");
        resultFilePath = FilenameUtils.concat(task.getDirPath(), "result.txt");
        enrollExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "match.exe");
    }

    public String getFarResultDir() {
        return farResultDir;
    }

    public void setFarResultDir(String farResultDir) {
        this.farResultDir = farResultDir;
    }

    public String getFrrResultDir() {
        return frrResultDir;
    }

    public void setFrrResultDir(String frrResultDir) {
        this.frrResultDir = frrResultDir;
    }

    public String getEnrollExeFilePath() {
        return enrollExeFilePath;
    }

    public void setEnrollExeFilePath(String enrollExeFilePath) {
        this.enrollExeFilePath = enrollExeFilePath;
    }

    public String getMatchExeFilePath() {
        return matchExeFilePath;
    }

    public void setMatchExeFilePath(String matchExeFilePath) {
        this.matchExeFilePath = matchExeFilePath;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }

    public void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public String getTaskStateFilePath() {
        return taskStateFilePath;
    }
}
