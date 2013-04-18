package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import rate.engine.task.TaskResult;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractRunner {
    protected BenchmarkEntity benchmark;
    protected TaskEntity task;
    protected TaskResult taskResult;
    protected AlgorithmVersionEntity algorithmVersion;
    protected final Session session = HibernateUtil.getSession();
    // Default 50m mem limit
    protected String memLimit = "52428800";
    // Default 3secs time limit
    protected String timeLimit = "3000";

    public void setTask(TaskEntity task) throws Exception {
        this.task = task;
        this.taskResult = new TaskResult(task);
        this.benchmark = task.getBenchmark();
        this.algorithmVersion = task.getAlgorithmVersion();
    }

    public static String getRateRunnerPath() {
        return FilenameUtils.concat(RateConfig.getBinDir(), "rate_run.exe");
    }

    public static String getDistEnginePath() {
        return "E:\\RATE_ROOT\\bin\\dist\\engine_run.py";
    }

    protected void prepare() throws Exception {
        if (!(new File(task.getTempDirPath()).exists())) {
            FileUtils.forceMkdir(new File(task.getTempDirPath()));
        }
        if (!(new File(task.getDirPath()).exists())) {
            FileUtils.forceMkdir(new File(task.getDirPath()));
        }
    }

    protected void cleanUp() throws Exception {

        FileUtils.deleteDirectory(new File(task.getTempDirPath()));
    }

    public void run() throws Exception {

    }

    public String getMemLimit() {
        return memLimit;
    }

    public String getTimeLimit() {
        return timeLimit;
    }
}
