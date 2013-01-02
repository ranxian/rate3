package rate.test;

import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.runner.RunnerInvoker;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-1
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class BenchmarkRunnerTest {
    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query = session.createQuery("from BenchmarkEntity ");
        BenchmarkEntity benchmark = (BenchmarkEntity)query.list().get(0);
        query = session.createQuery("from AlgorithmVersionEntity ");
        AlgorithmVersionEntity algorithmVersion = (AlgorithmVersionEntity)query.list().get(0);
        RunnerInvoker.run(benchmark, algorithmVersion);
    }
}
