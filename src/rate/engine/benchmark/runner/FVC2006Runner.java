package rate.engine.benchmark.runner;

import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class FVC2006Runner extends AbstractRunner {
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

    public void run() throws RunnerException {
        if (getBenchmark()==null || getAlgorithmVersion()==null) {
            throw new RunnerException("No benchmark or algorithmVersion specified");
        }

        if (getBenchmark().getProtocol() != getAlgorithmVersion().getAlgorithmByAlgorithmUuid().getProtocol()) {
            throw new RunnerException("Protocol does not match.");
        }


    }
}
