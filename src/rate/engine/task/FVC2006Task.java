package rate.engine.task;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import rate.model.TaskEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午10:12
 * To change this template use File | Settings | File Templates.
 */
public class FVC2006Task extends TaskEntity {
    private TaskEntity task;
    public FVC2006Task(TaskEntity taskEntity) throws Exception {
        this.task = taskEntity;
        enrollExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "match.exe");

        resultFilePath = task.getResultFilePath();
        errorRateFilePath = FilenameUtils.concat(task.getDirPath(), "rate.txt");
        rocFilePath = FilenameUtils.concat(task.getDirPath(), "roc.txt");
        genuineFilePath = FilenameUtils.concat(task.getDirPath(), "genuine.txt");
        imposterFilePath = FilenameUtils.concat(task.getDirPath(), "imposter.txt");
        fnmrFilePath = FilenameUtils.concat(task.getDirPath(), "fnmr.txt");
        fmrFilePath = FilenameUtils.concat(task.getDirPath(), "fmr.txt");
        taskStatePath = FilenameUtils.concat(task.getDirPath(), "state.txt");

        File stateFile = new File(getTaskStatePath());
        if (stateFile.exists()) {
            this.getStatistics();
        }
    }

    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String resultFilePath;
    private String errorRateFilePath;
    private String rocFilePath;
    private String genuineFilePath;
    private String imposterFilePath;
    private String fmrFilePath;
    private String fnmrFilePath;
    private String taskStatePath;
    private long startTime;

    public String getTaskStatePath() {
        return taskStatePath;
    }

    public String getErrorRateFilePath() {
        return errorRateFilePath;
    }

    public TaskEntity getTask() {
        return task;
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

    public String getRocFilePath() {
        return rocFilePath;
    }

    public String getGenuineFilePath() {
        return genuineFilePath;
    }

    public String getImposterFilePath() {
        return imposterFilePath;
    }

    public String getFmrFilePath() {
        return fmrFilePath;
    }

    public String getFnmrFilePath() {
        return fnmrFilePath;
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

    private double EER;
    private double EER_l;
    private double EER_h;
    private double FMR100;
    private double FMR1000;
    private double zeroFMR;
    private double zeroFNMR;
    private double finishedTurn = 0;
    private double totalTurn = 0;
    private String updated;

    private void getStatistics() throws Exception {
        BufferedReader errorRateReader = new BufferedReader(new FileReader(getErrorRateFilePath()));
        String line = StringUtils.strip(errorRateReader.readLine());
        String rs[] = line.split(" ");
        EER = Double.parseDouble(rs[0]);
        EER_l = Double.parseDouble(rs[1]);
        EER_h = Double.parseDouble(rs[2]);
        line = StringUtils.strip(errorRateReader.readLine());
        FMR100 = Double.parseDouble(line);
        line = StringUtils.strip(errorRateReader.readLine());
        FMR1000 = Double.parseDouble(line);
        line = StringUtils.strip(errorRateReader.readLine());
        zeroFMR = Double.parseDouble(line);
        line = StringUtils.strip(errorRateReader.readLine());
        zeroFNMR = Double.parseDouble(line);

        getTaskState();
    }

    private void getTaskState() throws Exception{
        BufferedReader stateReader = new BufferedReader(new FileReader(getTaskStatePath()));
        updated = StringUtils.strip(stateReader.readLine());
        String state[] = StringUtils.strip(stateReader.readLine()).split(" ");
        finishedTurn = Double.parseDouble(state[0]);
        totalTurn = Double.parseDouble(state[1]);
        startTime = Long.parseLong(state[2]);
    }

    public double getPercentage() {
        if (totalTurn == 0) return 0;
        return finishedTurn / totalTurn;
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

    public double getEER() {
        return EER;
    }

    public double getEER_l() {
        return EER_l;
    }

    public double getEER_h() {
        return EER_h;
    }

    public double getFMR100() {
        return FMR100;
    }

    public double getFMR1000() {
        return FMR1000;
    }

    public double getZeroFMR() {
        return zeroFMR;
    }

    public double getZeroFNMR() {
        return zeroFNMR;
    }
}
