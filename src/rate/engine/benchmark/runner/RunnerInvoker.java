package rate.engine.benchmark.runner;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;
import rate.util.JavaProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public class RunnerInvoker {

    private static final Logger logger = Logger.getLogger(RunnerInvoker.class);

    public static void run(BenchmarkEntity benchmark, AlgorithmVersionEntity algorithmVersion) throws Exception {
        // TODO
        if (! benchmark.getProtocol().equals(algorithmVersion.getAlgorithm().getProtocol())) {
            throw new Exception(String.format("Protocol does not match, benchmark: %s algorithmVersion: %s", benchmark.getProtocol(), algorithmVersion.getAlgorithm().getProtocol()));
        }

        logger.info(String.format("Invoke with benchmark: %s algorithmVersion: %s", benchmark.getUuid(), algorithmVersion.getUuid()));

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        TaskEntity task = new TaskEntity();
        task.setAlgorithmVersion(algorithmVersion);
        task.setBenchmark(benchmark);
        task.setCreated(HibernateUtil.getCurrentTimestamp());
        session.save(task);
        session.getTransaction().commit();

        List<String> parameters = new ArrayList<String>();
        parameters.add(task.getUuid());

        JavaProcess.exec(RunnerMain.class, parameters);
    }
}
