package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;

import java.io.File;

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
    protected AlgorithmVersionEntity algorithmVersion;

    public void setTask(TaskEntity task) throws Exception {
        this.task = task;
        this.benchmark = task.getBenchmarkByBenchmarkUuid();
        this.algorithmVersion = task.getAlgorithmVersionByAlgorithmVersionUuid();
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
}
