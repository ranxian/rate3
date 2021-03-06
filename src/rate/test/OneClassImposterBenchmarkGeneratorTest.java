package rate.test;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.OneClassImposterBenchmark;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.File;

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
                .setParameter("uuid", "9919088d-ef1e-4238-beee-15aecdfc10f8");
        ViewEntity view = (ViewEntity)query.list().get(0);

        BenchmarkEntity benchmark = new BenchmarkEntity();
        benchmark.setName("Imposter");

        session.save(benchmark);

        OneClassImposterBenchmark generator = new OneClassImposterBenchmark();
        generator.setBenchmark(benchmark);
        generator.setImposterClazzUuid("4FDC5715-C239-4FFB-9ED6-4F5FBA7B918D");

        DebugUtil.debug("start generate");
        generator.generate();
        DebugUtil.debug("finished generate");

        session.delete(benchmark);
        FileUtils.deleteDirectory(new File(benchmark.dirPath()));
    }
}
