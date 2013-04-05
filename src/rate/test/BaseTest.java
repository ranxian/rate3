package rate.test;

import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午1:31
 */
public class BaseTest {
    protected static final Session session = HibernateUtil.getSession();
    protected static ViewEntity view;
    protected static AlgorithmVersionEntity algorithmVersion = (AlgorithmVersionEntity)session.createQuery("from AlgorithmVersionEntity ").list().get(0);
    protected static AlgorithmEntity algorithm;
    protected static BenchmarkEntity benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
            .setParameter("uuid", "0b60b978-731f-4cfa-b201-fe91c813c5a8")
            .list().get(0);
    protected static BenchmarkEntity slsbBenchmark =  (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
            .setParameter("uuid", "72e33a12-a737-4bb7-ae45-661b620f71d2")
            .list().get(0);
}
