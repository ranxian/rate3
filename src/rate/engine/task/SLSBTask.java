package rate.engine.task;

import org.apache.commons.io.FilenameUtils;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午2:44
 */
public class SLSBTask extends TaskResult {
    private String farResultDir;
    private String frrResultDir;
    private String taskStateFilePath;
    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String resultFilePath;
    private String stdoutPath;
    private String stderrPath;
    private String purfPath;
    private String bxxResultFilePath;

    public String getFrrResultPath() {
        return frrResultPath;
    }

    private String frrResultPath;
    private BenchmarkEntity benchmark;

    public String getFarResultPath(int i) {
        return FilenameUtils.concat(task.getDirPath(), "FAR" + i + ".txt");
    }

    public String getFarResultPath() {
        return FilenameUtils.concat(task.getDirPath(), "FAR.txt");
    }

    public String getBxxResultFilePath() {
        return bxxResultFilePath;
    }

    public int getB4Far() throws Exception {
        BufferedReader rd = new BufferedReader(new FileReader(getBenchmark().dirPath()+"/desc.txt"));
        int res = Integer.parseInt(rd.readLine().split(" ")[1]);
        rd.close();
        return res;
    }

    public double getAlpha() throws Exception {
        BufferedReader rd = new BufferedReader(new FileReader(getBenchmark().dirPath()+"/desc.txt"));
        int res = Integer.parseInt(rd.readLine().split(" ")[2]);
        rd.close();
        return res;
    }

    public int getB4Frr() throws Exception {
        BufferedReader rd = new BufferedReader(new FileReader(getBenchmark().dirPath()+"/desc.txt"));
        int res = Integer.parseInt(rd.readLine().split(" ")[0]);
        rd.close();
        return res;
    }

    public String getFarResultPathByNum(int i, int j) {
        return FilenameUtils.concat(farResultDir,i+""+j+".txt" );
    }

    public String getFrrResultPathByNum(int i) {
        return FilenameUtils.concat(frrResultDir,i+".txt" );
    }

    private TaskEntity task;

    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    public SLSBTask(TaskEntity task) throws Exception {
        super(task);
        this.task = task;
        this.benchmark = task.getBenchmark();
        farResultDir = FilenameUtils.concat(task.getDirPath(), "FAR");
        frrResultDir = FilenameUtils.concat(task.getDirPath(), "FRR");
        taskStateFilePath = FilenameUtils.concat(task.getDirPath(), "state.txt");
        resultFilePath = FilenameUtils.concat(task.getDirPath(), "result.txt");
        bxxResultFilePath = FilenameUtils.concat(task.getDirPath(), "match_result_bxx.unsorted.txt");
        stderrPath = FilenameUtils.concat(task.getDirPath(), "stderr.txt");
        stdoutPath = FilenameUtils.concat(task.getDirPath(), "stdout.txt");
        purfPath = FilenameUtils.concat(task.getDirPath(), "purf.txt") ;
        enrollExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(task.getAlgorithmVersion().dirPath(), "match.exe");
        frrResultPath = FilenameUtils.concat(task.getDirPath(), "FRR.txt");
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
