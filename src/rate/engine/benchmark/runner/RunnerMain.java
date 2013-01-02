package rate.engine.benchmark.runner;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
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
        logger.info("Started");
        try {
            logger.debug("RunnerMain started");
            args = args[0].split(" ");
            String benchmarkUuid = args[0];
            String algorithmVersionUuid = args[1];
            Session session = HibernateUtil.getSession();
            Query query = session.createQuery("from BenchmarkEntity where uuid = :uuid").setParameter("uuid", benchmarkUuid);
            BenchmarkEntity benchmark = (BenchmarkEntity)query.list().get(0);
            query = session.createQuery("from AlgorithmVersionEntity where uuid = :uuid").setParameter("uuid", algorithmVersionUuid);
            AlgorithmVersionEntity algorithmVersion = (AlgorithmVersionEntity)query.list().get(0);

            if (!benchmark.getProtocol().equals(algorithmVersion.getAlgorithmByAlgorithmUuid().getProtocol())) {
                throw new Exception("Protocol does not match");
            }

            logger.debug(String.format("Protocol matched, benchmark %s algorithmVersion %s protocol %s", benchmark.getUuid(), algorithmVersion.getUuid(), benchmark.getProtocol()));

            FVC2006Runner runner1 = new FVC2006Runner();

            Class<?> runnerClass = Class.forName("rate.engine.benchmark.runner."+benchmark.getProtocol()+"Runner");

            AbstractRunner runner = (AbstractRunner)runnerClass.newInstance();
            runner.setBenchmarkEntity(benchmark);
            runner.setAlgorithmVersionEntity(algorithmVersion);
            runner.run();
        }
        catch (Exception ex) {
            logger.error(ex);
        }


        // check the parameter and determine the RunnerClass
        // new the class and run

        logger.info("Exit");


    }
}
