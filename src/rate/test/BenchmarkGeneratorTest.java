package rate.test;

import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.generator.AbstractGenerator;
import rate.engine.benchmark.generator.GeneralFVC2006Generator;
import rate.model.ClazzEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.List;

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

//        query = session.createQuery("select distinct V.sampleBySampleUuid.clazzByClassUuid from ViewSampleEntity as V where V.viewByViewUuid=:view order by RAND()");
//        query.setParameter("view", view);
//        query.setMaxResults(10);
//        List<ClazzEntity> ll = query.list();
//        for (ClazzEntity c : ll) {
//            System.out.println(c.getUuid());
//        }


        GeneralFVC2006Generator generator = new GeneralFVC2006Generator();
        generator.setClassCount(10);
        generator.setSampleCount(5);
        generator.setView(view);
        generator.generate();
    }
}
