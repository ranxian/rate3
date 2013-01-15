package rate.test;

import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.generator.GeneralFVC2006Generator;
import rate.engine.benchmark.generator.OneClassImposterGenerator;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午9:00
 */
public class OneClassImposterBenchmarkGeneratorTest {
    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from ViewEntity where uuid=:uuid")
                .setParameter("uuid", "40811913-dfef-44a7-9087-358be72125ec");
        ViewEntity view = (ViewEntity)query.list().get(0);

        OneClassImposterGenerator generator = new OneClassImposterGenerator();
        generator.setView(view);
        generator.setImposterClazzUuid("4FDC5715-C239-4FFB-9ED6-4F5FBA7B918D");

        generator.generate();
    }
}
