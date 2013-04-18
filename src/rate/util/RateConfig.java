package rate.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
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
    public static String getBinDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "bin"));
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

    public static String getZipRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "zips"));
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

    public static int getCountOfLines(String filePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int count = 0;
        String line;
        while ((line=reader.readLine())!=null) {
            count++;
        }
        return count;
    }

    public static String getJdkRootDir() {
        return config.getString("JDK_ROOT");
    }

    public static String getLibRootDir() {
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(getRootDir(), "lib"));
    }

    public static String getClassPath() {
        String jdkPath = FilenameUtils.separatorsToWindows(getJdkRootDir());
        String rateLibPath =FilenameUtils.separatorsToWindows(getLibRootDir());
        String classpath = jdkPath + "\\jre\\lib\\charsets.jar;" + jdkPath + "\\jre\\lib\\deploy.jar;" + jdkPath + "\\jre\\lib\\javaws.jar;" + jdkPath + "\\jre\\lib\\jce.jar;" + jdkPath + "\\jre\\lib\\jfr.jar;" + jdkPath + "\\jre\\lib\\jfxrt.jar;" + jdkPath + "\\jre\\lib\\jsse.jar;" + jdkPath + "\\jre\\lib\\management-agent.jar;" + jdkPath + "\\jre\\lib\\plugin.jar;" + jdkPath + "\\jre\\lib\\resources.jar;" + jdkPath + "\\jre\\lib\\rt.jar;" + jdkPath + "\\jre\\lib\\ext\\access-bridge.jar;" + jdkPath + "\\jre\\lib\\ext\\dnsns.jar;" + jdkPath + "\\jre\\lib\\ext\\jaccess.jar;" + jdkPath + "\\jre\\lib\\ext\\localedata.jar;" + jdkPath + "\\jre\\lib\\ext\\sunec.jar;" + jdkPath + "\\jre\\lib\\ext\\sunjce_provider.jar;" + jdkPath + "\\jre\\lib\\ext\\sunmscapi.jar;" + jdkPath + "\\jre\\lib\\ext\\sunpkcs11.jar;" + jdkPath + "\\jre\\lib\\ext\\zipfs.jar;" + rateLibPath + "\\hibernate-core-4.1.1.Final.jar;" + rateLibPath + "\\hibernate-jpa-2.0-api-1.0.1.Final.jar;" + rateLibPath + "\\hibernate-commons-annotations-4.0.1.Final.jar;" + rateLibPath + "\\antlr-2.7.7.jar;" + rateLibPath + "\\dom4j-1.6.1.jar;" + rateLibPath + "\\javassist-3.15.0-GA.jar;" + rateLibPath + "\\jboss-logging-3.1.0.GA.jar;" + rateLibPath + "\\jboss-transaction-api_1.1_spec-1.0.0.Final.jar;" + rateLibPath + "\\commons-configuration-1.9.jar;" + rateLibPath + "\\commons-lang-2.6.jar;" + rateLibPath + "\\commons-logging-1.1.1.jar;" + rateLibPath + "\\servlet-api-2.4.jar;" + rateLibPath + "\\xml-apis-1.0.b2.jar;" + rateLibPath + "\\commons-codec-1.6.jar;" + rateLibPath + "\\log4j-1.2.15.jar;" + rateLibPath + "\\mysql-connector-java-5.1.22.jar;" + rateLibPath + "\\freemarker-2.3.19.jar;" + rateLibPath + "\\jfreechart-1.0.9.jar;" + rateLibPath + "\\jcommon-1.0.12.jar;" + rateLibPath + "\\struts2-jfreechart-plugin-2.3.8.jar;" + rateLibPath + "\\struts2-core-2.3.8.jar;" + rateLibPath + "\\xwork-core-2.3.8.jar;" + rateLibPath + "\\commons-lang3-3.1.jar;" + rateLibPath + "\\ognl-3.0.6.jar;" + rateLibPath + "\\javassist-3.11.0.GA.jar;" + rateLibPath + "\\asm-commons-3.3.jar;" + rateLibPath + "\\asm-tree-3.3.jar;" + rateLibPath + "\\commons-fileupload-1.2.2.jar;" + rateLibPath + "\\commons-io-2.0.1.jar;" + rateLibPath + "\\jsp-api-2.0.jar;E:\\Program Files\\JetBrains\\IntelliJ IDEA 11.1.4\\lib\\idea_rt.jar";
        return classpath;
    }

    public static Boolean isDistRun() {
        DebugUtil.debug(config.getString("DIST_RUN"));
        return config.getString("DIST_RUN").equals("1") ? true : false;
    }

    public static String getDistEngineDir() {
        return FilenameUtils.concat(getBinDir(), "dist");
    }

    public static String getPythonRoot() {
        return config.getString("PYTHON_ROOT");
    }

    public static String getPythonExe() {
        return FilenameUtils.concat(getPythonRoot(), "python.exe");
    }
}
