package rate.test;

import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.File;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:58
 */
public class RateConfigTest {
    static final Session session = HibernateUtil.getSession();
    public static void main(String[] args) throws Exception {
        System.out.println(RateConfig.getRootDir());
        System.out.println(RateConfig.getSampleRootDir());
        System.out.println(RateConfig.getZipRootDir());
        System.out.println(RateConfig.getClassPath());
        System.out.println(RateConfig.getBenchmarkRootDir());
        DebugUtil.debug(RateConfig.getDistEngineDir());
        if (RateConfig.isDistRun()) {
            DebugUtil.debug("In distribution mode");
        }

        BenchmarkEntity benchmark = new BenchmarkEntity();
        session.save(benchmark);

        DebugUtil.debug(benchmark.dirPath());
        File file = new File(benchmark.dirPath());
        file.mkdir();
    }
}
