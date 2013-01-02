package rate.engine.benchmark.runner;

import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractRunner {

    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    public void setBenchmarkEntity(BenchmarkEntity benchmark) {
        this.benchmark = benchmark;
    }

    private BenchmarkEntity benchmark = null;

    public AlgorithmVersionEntity getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersionEntity(AlgorithmVersionEntity algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    private AlgorithmVersionEntity algorithmVersion = null;

    public void run() throws Exception {

    }
}
