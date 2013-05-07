package rate.test;

import org.apache.commons.io.FileUtils;
import rate.engine.benchmark.CustomBenchmark;
import rate.model.BenchmarkEntity;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-7
 * Time: PM4:29
 * To change this template use File | Settings | File Templates.
 */
public class CustomBenchmarkTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        String content = "1a74ffe6-d9f3-4ca4-802f-e7dde46e972a 7d3fd27f-6939-41b7-92e8-f650763afe85 G";
        CustomBenchmark benchmark = new CustomBenchmark();

        benchmark.setContent(content);
        benchmark.prepare();
        BenchmarkEntity bm = benchmark.generate();

        FileUtils.deleteDirectory(new File(bm.dirPath()));
        session.beginTransaction();
        session.delete(bm);
        session.getTransaction().commit();
    }


}
