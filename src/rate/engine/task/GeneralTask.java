package rate.engine.task;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import rate.model.TaskEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午10:12
 * To change this template use File | Settings | File Templates.
 */
public class GeneralTask extends TaskResult {
    public GeneralTask(TaskEntity taskEntity) throws Exception {
        super(taskEntity);

        errorRateFilePath = FilenameUtils.concat(task.getDirPath(), "rate.txt");
        rocFilePath = FilenameUtils.concat(task.getDirPath(), "roc.txt");
        genuineFilePath = FilenameUtils.concat(task.getDirPath(), "genuine.txt");
        imposterFilePath = FilenameUtils.concat(task.getDirPath(), "imposter.txt");
        fnmrFilePath = FilenameUtils.concat(task.getDirPath(), "fnmr.txt");
        fmrFilePath = FilenameUtils.concat(task.getDirPath(), "fmr.txt");
        badResultDir = FilenameUtils.concat(task.getDirPath(), "bad-result");
        genuineResultPath = FilenameUtils.concat(badResultDir, "genuine");
        imposterResultPath = FilenameUtils.concat(badResultDir, "imposter");

        revImposterPath = FilenameUtils.concat(task.getDirPath(), "revImp.txt");
        if (task.getFinished() != null) {
            getStatistics();
        }

        if (new File(getTaskStatePath()).exists()) {
            getTaskState();
        }
    }

    public String getLogPathByTypeNumber(String type, String num) {
        String path = FilenameUtils.concat(badResultDir, type);
        String logFilePath = FilenameUtils.concat(path, num+".txt");
        return logFilePath;
    }

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
        errorRateReader.close();
    }

    private String errorRateFilePath;
    private String rocFilePath;
    private String genuineFilePath;
    private String imposterFilePath;
    private String fmrFilePath;
    private String fnmrFilePath;
    private String badResultDir;
    private String genuineResultPath;
    private String imposterResultPath;
    private String revImposterPath;

    private double EER;
    private double EER_l;
    private double EER_h;
    private double FMR100;
    private double FMR1000;
    private double zeroFMR;
    private double zeroFNMR;

    public String getRevImposterPath() {
        return revImposterPath;
    }

    public String getGenuineResultPath() {
        return this.genuineResultPath;
    }

    public String getImposterResultPath() {
        return this.imposterResultPath;
    }

    public String getErrorRateFilePath() {
        return errorRateFilePath;
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
