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
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";

        // FIXME: this is just a temp for debug
        String classpath = "/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/jsse.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/javazic.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rt.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rhino.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/compilefontconfig.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/management-agent.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/pulse-java.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/sunjce_provider.jar:/home/rate/rate3:/home/rate/RATE_ROOT/lib/hibernate-core-4.1.1.Final.jar:/home/rate/RATE_ROOT/lib/hibernate-jpa-2.0-api-1.0.1.Final.jar:/home/rate/RATE_ROOT/lib/hibernate-commons-annotations-4.0.1.Final.jar:/home/rate/RATE_ROOT/lib/antlr-2.7.7.jar:/home/rate/RATE_ROOT/lib/dom4j-1.6.1.jar:/home/rate/RATE_ROOT/lib/javassist-3.15.0-GA.jar:/home/rate/RATE_ROOT/lib/jboss-logging-3.1.0.GA.jar:/home/rate/RATE_ROOT/lib/jboss-transaction-api_1.1_spec-1.0.0.Final.jar:/home/rate/RATE_ROOT/lib/commons-configuration-1.9.jar:/home/rate/RATE_ROOT/lib/commons-lang-2.6.jar:/home/rate/RATE_ROOT/lib/commons-logging-1.1.1.jar:/home/rate/RATE_ROOT/lib/servlet-api-2.4.jar:/home/rate/RATE_ROOT/lib/xml-apis-1.0.b2.jar:/home/rate/RATE_ROOT/lib/commons-codec-1.6.jar:/home/rate/RATE_ROOT/lib/log4j-1.2.15.jar:/home/rate/RATE_ROOT/lib/mysql-connector-java-5.1.22.jar:/home/rate/RATE_ROOT/lib/freemarker-2.3.19.jar:/home/rate/RATE_ROOT/lib/jfreechart-1.0.9.jar:/home/rate/RATE_ROOT/lib/jcommon-1.0.12.jar:/home/rate/RATE_ROOT/lib/struts2-jfreechart-plugin-2.3.8.jar:/home/rate/RATE_ROOT/lib/struts2-core-2.3.8.jar:/home/rate/RATE_ROOT/lib/xwork-core-2.3.8.jar:/home/rate/RATE_ROOT/lib/commons-lang3-3.1.jar:/home/rate/RATE_ROOT/lib/ognl-3.0.6.jar:/home/rate/RATE_ROOT/lib/javassist-3.11.0.GA.jar:/home/rate/RATE_ROOT/lib/asm-commons-3.3.jar:/home/rate/RATE_ROOT/lib/asm-tree-3.3.jar:/home/rate/RATE_ROOT/lib/commons-fileupload-1.2.2.jar:/home/rate/RATE_ROOT/lib/commons-io-2.0.1.jar:/home/rate/RATE_ROOT/lib/jsp-api-2.0.jar:/home/rate/RATE_ROOT/lib/zip4j-1.3.1.jar";
//      String classpath = System.getProperty("java.class.path");

        classpath = "\"" + classpath + "\"";
        String className = klass.getCanonicalName();

        String parameter = StringUtils.join(paramaters, " ");


        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-classpath", classpath, className, parameter);

        String cmd = String.format("Run with command: %s", StringUtils.join(builder.command(), " "));
        logger.debug(cmd);

//        Process process = Runtime.getRuntime().exec(cmd);

        Process process = builder.start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));


        process.waitFor();

        DebugUtil.debug(process.exitValue()+"");

        while (true) {
            String line = stdInput.readLine();
            if (line == null) break;

            DebugUtil.debug(line);
        }

        stdInput.close();

        return 0;

        //process.waitFor();

//        logger.debug("Process return: " + process.exitValue());

//        return process.exitValue();
    }

}
