package rate.util;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmVersionEntity;

import java.lang.String;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:24
 */
public class RateConfig {
    private static final Logger logger = Logger.getLogger(RateConfig.class);

    private static final Configuration config = buildConfig();

    private static PropertiesConfiguration buildConfig() {
        try {
            return new PropertiesConfiguration("rate.properties");
        }
        catch (ConfigurationException ex) {
            logger.error(ex);
            return null;
        }
    }

    public static String getRootDir() {
        return config.getString("RATE_ROOT");
    }

    public static String getSampleRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "samples"));
    }

    public static String getBenchmarkRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "benchmarks"));
    }

    public static String getAlgorithmRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "algorithms"));
    }

//    public static String getBenchmarkDir(String benchmarkUuid) {
//        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getBenchmarkRootDir(), benchmarkUuid));
//    }

//    public static String getAlgorithmVersionDir(String algorithmVersionUuid) {
//        Session session = HibernateUtil.getSession();
//        Query q = session.createQuery("from AlgorithmVersionEntity where uuid = :uuid");
//        q.setParameter("uuid", algorithmVersionUuid);
//        List<AlgorithmVersionEntity> results = q.list();
//        if (results.size()!=1) {
//            return null;
//        }
//        else {
//            AlgorithmVersionEntity e = results.get(0);
//            String temp = FilenameUtils.concat(RateConfig.getRootDir(), "algorithms").concat(e.getAlgorithmUuid()).concat(e.getUuid());
//            return FilenameUtils.separatorsToUnix(temp);
//        }
//    }

//    public static String getAlgorithmVersionDir(AlgorithmVersionEntity e) {
//        String temp = FilenameUtils.concat(RateConfig.getRootDir(), "algorithms").concat(e.getAlgorithmUuid()).concat(e.getUuid());
//        return FilenameUtils.separatorsToUnix(temp);
//    }
}
