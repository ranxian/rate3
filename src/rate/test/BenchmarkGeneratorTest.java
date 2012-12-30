package rate.test;

import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.generator.AbstractGenerator;
import rate.engine.benchmark.generator.GeneralFVC2006Generator;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午9:00
 */
public class BenchmarkGeneratorTest {
    public static void main(String[] args) throws Exception {

        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from ViewEntity ");
        ViewEntity view = (ViewEntity)query.list().get(0);
        query = session.createQuery("from ViewSampleEntity where viewByViewUuid=:view");
        query.setParameter("view", view);
        System.out.println(query.list().get(0));

        return;
//        GeneralFVC2006Generator generator = new GeneralFVC2006Generator();
//        generator.setClassCount(10);
//        generator.setSampleCount(5);
//        generator.generate();
    }
}
