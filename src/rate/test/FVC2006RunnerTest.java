package rate.test;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.benchmark.runner.FVC2006Runner;
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
public class FVC2006RunnerTest extends BaseTest {

    private static final Logger logger = Logger.getLogger(FVC2006RunnerTest.class);

    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        TaskEntity task = new TaskEntity();
        task.setAlgorithmVersion((AlgorithmVersionEntity)getExample("AlgorithmVersion"));
        task.setBenchmark((BenchmarkEntity)getExample("BenchmakrEntity"));
        task.setCreated(HibernateUtil.getCurrentTimestamp());
        session.save(task);

        DebugUtil.debug(task.getAlgorithmVersion().dirPath());

        FVC2006Runner runner = new FVC2006Runner();

        runner.setTask(task);

        DebugUtil.debug("start tun");
        // runner.run();
        // Runner end


        session.getTransaction().commit();
    }
}
