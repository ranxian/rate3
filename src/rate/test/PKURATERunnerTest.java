package rate.test;

import rate.engine.benchmark.runner.FVC2006Runner;
import rate.engine.benchmark.runner.PKURATERunner;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-15
 * Time: 下午5:46
 */
public class PKURATERunnerTest extends BaseTest {
    public static void main(String args[]) throws Exception {
        BenchmarkEntity slsbBenchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid").setParameter("uuid", "cfb2c25c-140a-4b55-80e6-5a8c507e37da")
                .list().get(0);

        TaskEntity task = new TaskEntity();
        AlgorithmVersionEntity version = (AlgorithmVersionEntity)session.createQuery("from AlgorithmVersionEntity where uuid=:uuid")
                .setParameter("uuid", "c6370812-3a9c-412d-a5da-0b20df3aac53").list().get(0);
        task.setAlgorithmVersion(version);
        task.setBenchmark(slsbBenchmark);
        task.setCreated(HibernateUtil.getCurrentTimestamp());
        session.save(task);

        DebugUtil.debug(task.getAlgorithmVersion().dirPath());

        PKURATERunner runner = new PKURATERunner();

        runner.setTask(task);

        DebugUtil.debug("start tun");
        runner.run();
    }
}
