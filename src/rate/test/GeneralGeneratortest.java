package rate.test;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jfree.io.FileUtilities;
import rate.engine.benchmark.GeneralBenchmark;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.ViewEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.File;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午9:00
 */
public class GeneralGeneratortest {
    public static void main(String[] args) throws Exception {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from ViewEntity where uuid=:uuid")
//          .setParameter("uuid", "9e050473-9306-4d32-ba72-deed98d432cb"); // 2010-autumn
//        .setParameter("uuid", "968d97c2-785f-4775-ad3f-f29ceb8c799c"); // 2011-spring
//        .setParameter("uuid", "11f9ec0c-5380-4e8c-95c8-a586cae0ad3d"); // 2011-autumn
        .setParameter("uuid", "797578ff-f40d-46d4-a6bb-930069f70705"); // 2012-spring
        ViewEntity view = (ViewEntity)query.list().get(0);
        BenchmarkEntity benchmark = new BenchmarkEntity();
        benchmark.setView(view);
        benchmark.setName("LARGE");
        benchmark.setType("General");
        // general
        GeneralBenchmark generator = new GeneralBenchmark();
        generator.setClassCount(10);
        generator.setSampleCount(5);
        benchmark.setGenerator("SMALL");

        session.beginTransaction();
        session.save(benchmark);
        session.getTransaction().commit();

        generator.setBenchmark(benchmark);
        DebugUtil.debug(benchmark.filePath());
        DebugUtil.debug(benchmark.getUuid());
        DebugUtil.debug("start generate");
        generator.generate();
        DebugUtil.debug("finished generate");

        FileUtils.deleteDirectory(new File(benchmark.dirPath()));
        session.beginTransaction();
        session.delete(benchmark);
        session.getTransaction().commit();


//        GeneralFVC2006Generator generator = new SmallFVC2006Generator();
//        generator.setView(view);
//        generator.setBenchmarkName("Test for SmallFVC2006Generator");
//        generator.generate();


//        GeneralFVC2006Generator generator = new GeneralFVC2006Generator();
//        generator.setView(view);
//        generator.setClassCount(1000);
//        generator.setSampleCount(10);
//        generator.setBenchmarkName("Test for GeneralFVC2006Generator");

    }
}
