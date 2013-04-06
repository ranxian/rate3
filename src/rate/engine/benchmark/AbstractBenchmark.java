package rate.engine.benchmark;

import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.io.File;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-6
 * Time: 下午4:16
 */
public abstract class AbstractBenchmark {
    protected final Session session = HibernateUtil.getSession();

    protected ViewEntity view = null;

    protected BenchmarkEntity benchmark;

    public BenchmarkEntity getBenchmark() {
        return this.benchmark;
    }

    public void setBenchmark(BenchmarkEntity benchmark) {
        this.benchmark = benchmark;
        this.view = benchmark.getView();
    }

    abstract public BenchmarkEntity generate() throws Exception;

    abstract public void prepare() throws Exception;

    protected void prepareBenchmarkDir() {
        File dir = new File(benchmark.dirPath());
        dir.mkdirs();
    }
}
