package rate.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
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
        String classpath = "C:\\Java\\jdk1.7.0_10\\jre\\lib\\charsets.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\deploy.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\javaws.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\jce.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\jfr.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\jfxrt.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\jsse.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\management-agent.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\plugin.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\resources.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\rt.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\access-bridge.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\dnsns.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\jaccess.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\localedata.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\sunec.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\sunjce_provider.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\sunmscapi.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\sunpkcs11.jar;C:\\Java\\jdk1.7.0_10\\jre\\lib\\ext\\zipfs.jar;D:\\Workspaces\\intellij\\rate3_22\\out\\production\\rate3;D:\\Workspaces\\intellij\\rate3_22\\lib\\hibernate-core-4.1.1.Final.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\hibernate-jpa-2.0-api-1.0.1.Final.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\hibernate-commons-annotations-4.0.1.Final.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\antlr-2.7.7.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\dom4j-1.6.1.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\javassist-3.15.0-GA.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\jboss-logging-3.1.0.GA.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\jboss-transaction-api_1.1_spec-1.0.0.Final.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-configuration-1.9.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-lang-2.6.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-logging-1.1.1.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\servlet-api-2.4.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\xml-apis-1.0.b2.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-codec-1.6.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\log4j-1.2.15.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\mysql-connector-java-5.1.22.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\freemarker-2.3.19.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\jfreechart-1.0.9.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\jcommon-1.0.12.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\struts2-jfreechart-plugin-2.3.8.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\struts2-core-2.3.8.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\xwork-core-2.3.8.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-lang3-3.1.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\ognl-3.0.6.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\javassist-3.11.0.GA.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\asm-commons-3.3.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\asm-tree-3.3.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-fileupload-1.2.2.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\commons-io-2.0.1.jar;D:\\Workspaces\\intellij\\rate3_22\\lib\\jsp-api-2.0.jar;C:\\Program Files\\JetBrains\\IntelliJ IDEA 12.0\\lib\\idea_rt.jar";

//      String classpath = System.getProperty("java.class.path");

        classpath = "\"" + classpath + "\"";
        String className = klass.getCanonicalName();

        String parameter = StringUtils.join(paramaters, " ");


        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className, parameter);

        String cmd = String.format("Run with command: %s", StringUtils.join(builder.command(), " "));
        logger.debug(cmd);

//        Process process = Runtime.getRuntime().exec(cmd);

        Process process = builder.start();

        return 0;

        //process.waitFor();

//        logger.debug("Process return: " + process.exitValue());

//        return process.exitValue();
    }
}
