package rate.util;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmVersionEntity;

import java.io.File;
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
    public static String getLastLine(String filePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        StringBuilder sb = new StringBuilder();

        int lengthOfChar = 1;
        for (long i=raf.length()-lengthOfChar; i!=-1; i-=lengthOfChar) {
            raf.seek(i);
            char c = (char)raf.readByte();

            //logger.trace(c);

            // if the last char is '\n', it should be ignored
            if (c=='\n' && i==raf.length()-lengthOfChar) continue;

            if (c=='\n') break;
            else sb.append(c);
        }

        String result = StringUtils.strip(sb.reverse().toString());
//        logger.trace(String.format("%s\n\t%s", filePath, result));

        raf.close();

        return result;
    }

//    // TODO: sholud not be here
//    public static void putFile(String filePath, File toBePut) throws IOException {
//        FileUtils.copyFile(toBePut, new File(filePath));
//    }

}
