package rate.engine.benchmark.runner;

import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;

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

    public void setTask(TaskEntity task) {
        this.task = task;
        this.benchmark = task.getBenchmarkByBenchmarkUuid();
        this.algorithmVersion = task.getAlgorithmVersionByAlgorithmVersionUuid();
    }

    public void run() throws Exception {

    }
}
