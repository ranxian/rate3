package rate.engine.benchmark;

import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    protected HashMap uuidTable = new HashMap();
    protected HashMap enrollMap = new HashMap();

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

    public void printUuidTable() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(benchmark.getUuidTableFilePath()));
        ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(uuidTable.entrySet());
        for (Map.Entry<String, String> entry : list) {
            writer.println(entry.getValue() + " " + entry.getKey() + " " + enrollMap.get(entry.getKey()));
        }
        writer.close();
    }
}
