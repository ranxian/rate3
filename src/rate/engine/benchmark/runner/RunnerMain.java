package rate.engine.benchmark.runner;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
public class RunnerMain {

    private static final Logger logger = Logger.getLogger(RunnerMain.class);

    public static void main(String args[]) {
        try {
            logger.debug("Started");

            args = args[0].split(" ");
            String taskUuid = args[0];
            Session session = HibernateUtil.getSession();
            Query query = session.createQuery("from TaskEntity where uuid = :uuid").setParameter("uuid", taskUuid);

            TaskEntity task = (TaskEntity)query.list().get(0);
            BenchmarkEntity benchmark = task.getBenchmarkByBenchmarkUuid();
            AlgorithmVersionEntity algorithmVersion = task.getAlgorithmVersion();

            if (!benchmark.getProtocol().equals(algorithmVersion.getAlgorithm().getProtocol())) {
                throw new Exception("Protocol does not match");
            }

            Class<?> runnerClass = Class.forName("rate.engine.benchmark.runner."+benchmark.getProtocol()+"Runner");

            AbstractRunner runner = (AbstractRunner)runnerClass.newInstance();
            runner.setTask(task);

            logger.info(String.format("Attempt to run task [%s] with runner [%s]", task.getUuid(), runnerClass.getName()));
            runner.run();
            logger.info(String.format("Run task [%s] with runner [%s] finished", task.getUuid(), runnerClass.getName()));

            task.setFinished(HibernateUtil.getCurrentTimestamp());
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();

            logger.info("Exit");
        }
        catch (Exception ex) {
            logger.error("", ex);
        }
    }

}
