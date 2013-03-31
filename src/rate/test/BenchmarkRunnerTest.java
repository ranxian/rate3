package rate.test;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.generator.GeneralGenrator;
import rate.engine.benchmark.runner.FVC2006Runner;
import rate.engine.benchmark.runner.RunnerInvoker;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-1
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class BenchmarkRunnerTest {

    private static final Logger logger = Logger.getLogger(BenchmarkRunnerTest.class);

    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        // small
        BenchmarkEntity benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", "f707b21c-9d05-436c-b8d1-e4c79f1fa4d7")
                .list().get(0);

        // medium
//        BenchmarkEntity benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
//                .setParameter("uuid", "54accea9-8f52-42b9-ac54-02ad580e1c9d")
//                .list().get(0);

        AlgorithmVersionEntity algorithmVersion = (AlgorithmVersionEntity)session.createQuery("from AlgorithmVersionEntity where uuid=:uuid")
                .setParameter("uuid", "986f0a57-7b96-40e7-93b3-b3bc383fc9aa")
                .list().get(0);

        TaskEntity task = new TaskEntity();
        task.setAlgorithmVersion(algorithmVersion);
        task.setBenchmark(benchmark);
        task.setCreated(HibernateUtil.getCurrentTimestamp());
        session.save(task);

        FVC2006Runner runner = new FVC2006Runner();

        runner.setTask(task);

        // Runner begin
        runner.run();
        // Runner end


        FileUtils.deleteDirectory(new File(task.getDirPath()));
        session.delete(task);
        session.getTransaction().commit();
    }
}
