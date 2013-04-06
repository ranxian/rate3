package rate.test;

import org.hibernate.Query;
import rate.engine.benchmark.SLSBBenchmark;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.DebugUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午1:31
 */
public class SLSBGeneratorTest extends BaseTest {
    public static void main(String[] args) throws Exception {
        Query query = session.createQuery("from ViewEntity where uuid=:uuid")
//          .setParameter("uuid", "9e050473-9306-4d32-ba72-deed98d432cb"); // 2010-autumn
//        .setParameter("uuid", "968d97c2-785f-4775-ad3f-f29ceb8c799c"); // 2011-spring
//        .setParameter("uuid", "11f9ec0c-5380-4e8c-95c8-a586cae0ad3d"); // 2011-autumn
//                .setParameter("uuid", "9919088d-ef1e-4238-beee-15aecdfc10f8"); // 2012-spring
        .setParameter("uuid", "797578ff-f40d-46d4-a6bb-930069f70705");
        ViewEntity view = (ViewEntity)query.list().get(0);
        BenchmarkEntity benchmark = new BenchmarkEntity();
        benchmark.setView(view);
        benchmark.setName("SLSB");
        // general
        SLSBBenchmark generator = new SLSBBenchmark();
        generator.setB4Far(10);
        generator.setB4Frr(10);
        generator.setK(10);
        benchmark.setGenerator("SLSB");
        benchmark.setType("SLSB");

        session.beginTransaction();
        session.save(benchmark);
        DebugUtil.debug(benchmark.dirPath());

        generator.setBenchmark(benchmark);
        DebugUtil.debug(benchmark.filePath());
        DebugUtil.debug(benchmark.getUuid());
        DebugUtil.debug("start generate");
        benchmark = generator.generate();
        DebugUtil.debug("finished generate");
        session.delete(benchmark);
        session.getTransaction().commit();
    }
}
