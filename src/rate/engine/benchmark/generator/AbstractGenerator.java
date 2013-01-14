package rate.engine.benchmark.generator;

import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
abstract public class AbstractGenerator {

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    private String benchmarkName = null;

    public String getGeneratorName() {
        return generatorName;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    String generatorName = null;

    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    private ViewEntity view = null;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String protocol = "";

    abstract public BenchmarkEntity generate() throws Exception;
}
