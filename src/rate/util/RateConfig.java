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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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

    public static String getTempRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "temp"));
    }

    public static String getTaskRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "tasks"));
    }

    // TODO: should not be here, for convenient now
    public String getLastLine(String filePath) {
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            StringBuilder sb = new StringBuilder();

            for (long i=raf.length()-1; i!=-1; i--) {
                raf.seek(i);
                char c = raf.readChar();

                // if the last char is '\n', it should be ignored
                if (c=='\n' && i==raf.length()-1) continue;

                if (c=='\n') break;
                else sb.append(c);
            }

            return sb.reverse().toString();
        }
        catch (FileNotFoundException ex) {
            logger.debug(ex);
            return "";
        }
        catch (IOException ex) {
            logger.debug(ex);
            return "";
        }
    }

}
