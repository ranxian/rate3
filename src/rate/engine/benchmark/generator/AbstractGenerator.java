package rate.engine.benchmark.generator;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
abstract public class AbstractGenerator {

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
