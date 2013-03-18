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
        String classpath = "C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\charsets.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\deploy.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\javaws.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\jce.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\jfr.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\jfxrt.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\jsse.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\management-agent.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\plugin.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\resources.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\rt.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\access-bridge.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\dnsns.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\jaccess.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\localedata.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\sunec.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\sunjce_provider.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\sunmscapi.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\sunpkcs11.jar;C:\\Program Files\\Java\\jdk1.7.0_09\\jre\\lib\\ext\\zipfs.jar;E:\\Dev\\rate3\\rate3\\out\\production\\rate3;E:\\Dev\\rate3\\rate3\\lib\\hibernate-core-4.1.1.Final.jar;E:\\Dev\\rate3\\rate3\\lib\\hibernate-jpa-2.0-api-1.0.1.Final.jar;E:\\Dev\\rate3\\rate3\\lib\\hibernate-commons-annotations-4.0.1.Final.jar;E:\\Dev\\rate3\\rate3\\lib\\antlr-2.7.7.jar;E:\\Dev\\rate3\\rate3\\lib\\dom4j-1.6.1.jar;E:\\Dev\\rate3\\rate3\\lib\\javassist-3.15.0-GA.jar;E:\\Dev\\rate3\\rate3\\lib\\jboss-logging-3.1.0.GA.jar;E:\\Dev\\rate3\\rate3\\lib\\jboss-transaction-api_1.1_spec-1.0.0.Final.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-configuration-1.9.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-lang-2.6.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-logging-1.1.1.jar;E:\\Dev\\rate3\\rate3\\lib\\servlet-api-2.4.jar;E:\\Dev\\rate3\\rate3\\lib\\xml-apis-1.0.b2.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-codec-1.6.jar;E:\\Dev\\rate3\\rate3\\lib\\log4j-1.2.15.jar;E:\\Dev\\rate3\\rate3\\lib\\mysql-connector-java-5.1.22.jar;E:\\Dev\\rate3\\rate3\\lib\\freemarker-2.3.19.jar;E:\\Dev\\rate3\\rate3\\lib\\jfreechart-1.0.9.jar;E:\\Dev\\rate3\\rate3\\lib\\jcommon-1.0.12.jar;E:\\Dev\\rate3\\rate3\\lib\\struts2-jfreechart-plugin-2.3.8.jar;E:\\Dev\\rate3\\rate3\\lib\\struts2-core-2.3.8.jar;E:\\Dev\\rate3\\rate3\\lib\\xwork-core-2.3.8.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-lang3-3.1.jar;E:\\Dev\\rate3\\rate3\\lib\\ognl-3.0.6.jar;E:\\Dev\\rate3\\rate3\\lib\\javassist-3.11.0.GA.jar;E:\\Dev\\rate3\\rate3\\lib\\asm-commons-3.3.jar;E:\\Dev\\rate3\\rate3\\lib\\asm-tree-3.3.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-fileupload-1.2.2.jar;E:\\Dev\\rate3\\rate3\\lib\\commons-io-2.0.1.jar;E:\\Dev\\rate3\\rate3\\lib\\jsp-api-2.0.jar;E:\\Program Files\\JetBrains\\IntelliJ IDEA 11.1.4\\lib\\idea_rt.jar";

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
