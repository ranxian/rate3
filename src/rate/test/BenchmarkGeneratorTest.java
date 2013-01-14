package rate.test;

import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.generator.AbstractGenerator;
import rate.engine.benchmark.generator.GeneralFVC2006Generator;
import rate.engine.benchmark.generator.MediumFVC2006Generator;
import rate.engine.benchmark.generator.SmallFVC2006Generator;
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
        Query query = session.createQuery("from ViewEntity");
        ViewEntity view = (ViewEntity)query.list().get(0);

//        GeneralFVC2006Generator generator = new SmallFVC2006Generator();
//        generator.setView(view);
//        generator.setBenchmarkName("Test for SmallFVC2006Generator");
//        generator.generate();

        GeneralFVC2006Generator generator = new MediumFVC2006Generator();
        generator.setView(view);
        generator.setBenchmarkName("Test for MediumFVC2006Generator");

        generator.generate();
    }
}
