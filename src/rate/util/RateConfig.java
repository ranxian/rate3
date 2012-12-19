package rate.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.lang.String;

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

    public static String getBenchmarkDir(String benchmarkUuid) {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getBenchmarkRootDir(), benchmarkUuid));
    }
}
