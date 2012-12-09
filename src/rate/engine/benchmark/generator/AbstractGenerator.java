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
    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    private ViewEntity view;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String protocol;

    abstract public BenchmarkEntity generate() throws GeneratorException;
}
