package rate.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class JavaProcess {

    private static final Logger logger = Logger.getLogger(JavaProcess.class);

    private JavaProcess() {
    }

    public static int exec(Class klass, List<String> paramaters) throws IOException,
            InterruptedException {
        logger.trace("exec");
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";

        String classpath = "";
        String rate_class_path = RateConfig.getConfig().getString("RATE_CLASS_PATH");
        classpath += rate_class_path;
        String rate_lib_path = RateConfig.getConfig().getString("RATE_LIB_PATH");
        File libDir = new File(rate_lib_path);
        File[] jars = libDir.listFiles();
        DebugUtil.debug(rate_lib_path);
        for (int i = 0; i< jars.length; i++) {
            classpath += System.getProperty("path.separator") + jars[i].getAbsolutePath();
        }
        //String classpath = "/home/rate/tomcat/webapps/ROOT/WEB-INF/classes:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/resources.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/charsets.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/jce.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/jsse.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/javazic.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/rt.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/rhino.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/compilefontconfig.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/management-agent.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/ext/pulse-java.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-1.6.0-openjdk-i386/jre/lib/ext/sunjce_provider.jar:/production/rate3:/home/rate/rate3/lib/hibernate-core-4.1.1.Final.jar:/home/rate/rate3/lib/hibernate-jpa-2.0-api-1.0.1.Final.jar:/home/rate/rate3/lib/hibernate-commons-annotations-4.0.1.Final.jar:/home/rate/rate3/lib/antlr-2.7.7.jar:/home/rate/rate3/lib/dom4j-1.6.1.jar:/home/rate/rate3/lib/javassist-3.15.0-GA.jar:/home/rate/rate3/lib/jboss-logging-3.1.0.GA.jar:/home/rate/rate3/lib/jboss-transaction-api_1.1_spec-1.0.0.Final.jar:/home/rate/rate3/lib/commons-configuration-1.9.jar:/home/rate/rate3/lib/commons-lang-2.6.jar:/home/rate/rate3/lib/commons-logging-1.1.1.jar:/home/rate/rate3/lib/servlet-api-2.4.jar:/home/rate/rate3/lib/xml-apis-1.0.b2.jar:/home/rate/rate3/lib/commons-codec-1.6.jar:/home/rate/rate3/lib/log4j-1.2.15.jar:/home/rate/rate3/lib/mysql-connector-java-5.1.22.jar:/home/rate/rate3/lib/freemarker-2.3.19.jar:/home/rate/rate3/lib/jfreechart-1.0.9.jar:/home/rate/rate3/lib/jcommon-1.0.12.jar:/home/rate/rate3/lib/struts2-jfreechart-plugin-2.3.8.jar:/home/rate/rate3/lib/struts2-core-2.3.8.jar:/home/rate/rate3/lib/xwork-core-2.3.8.jar:/home/rate/rate3/lib/commons-lang3-3.1.jar:/home/rate/rate3/lib/ognl-3.0.6.jar:/home/rate/rate3/lib/javassist-3.11.0.GA.jar:/home/rate/rate3/lib/asm-commons-3.3.jar:/home/rate/rate3/lib/asm-tree-3.3.jar:/home/rate/rate3/lib/commons-fileupload-1.2.2.jar:/home/rate/rate3/lib/commons-io-2.0.1.jar:/home/rate/rate3/lib/jsp-api-2.0.jar:/home/rate/rate3/lib/zip4j-1.3.1.jar";
        //String classpath = System.getProperty("java.class.path");
        logger.trace("classpath " + classpath);

        // WARNING!!!! 命令里面不能加引号，也不需要加引号
        //classpath = "\"" + classpath + "\"";
        String className = klass.getCanonicalName();

        String parameter = StringUtils.join(paramaters, " ");


        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-classpath", classpath, className, parameter);

        String cmd = StringUtils.join(builder.command(), " ");
        logger.debug(String.format("Run with command: %s", cmd));

        /*logger.trace("getRuntime().exec");
        Process process = Runtime.getRuntime().exec(cmd);
        logger.trace("wait for the process");
        process.waitFor();
        logger.trace("process done");
        logger.trace("process return " + process.exitValue());*/

        Process process = builder.start();

        logger.trace("process started, waiting");
        process.waitFor();
        logger.trace("process finished");
        logger.debug("Process return: " + process.exitValue());

        return 0;
    }

}
