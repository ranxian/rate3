package rate.test;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import rate.engine.benchmark.runner.FVC2006Runner;
import rate.engine.benchmark.runner.SLSBRunner;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.io.File;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午4:57
 */
public class SLSBRunnerTest extends BaseTest {
    public static void main(String[] args) throws Exception {
        BenchmarkEntity benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", "be2287b2-4433-4992-a00f-25b8d2182251")
                .list().get(0);


        AlgorithmVersionEntity algorithmVersion = (AlgorithmVersionEntity)session.createQuery("from AlgorithmVersionEntity where uuid=:uuid")
                .setParameter("uuid", "a686ed52-a008-4937-8350-dd5cad7ead6d")
                .list().get(0);

        TaskEntity task = new TaskEntity();
        task.setAlgorithmVersion(algorithmVersion);
        task.setBenchmark(benchmark);
        session.save(task);

        SLSBRunner runner = new SLSBRunner();

        runner.setTask(task);

        // Runner begin
        runner.run();
        // Runner end


        FileUtils.deleteDirectory(new File(task.getDirPath()));
        session.delete(task);
        session.getTransaction().commit();
    }
}
