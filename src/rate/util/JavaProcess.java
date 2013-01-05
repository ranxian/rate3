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
        String classpath = "\"" + System.getProperty("java.class.path") + "\"";
        String className = klass.getCanonicalName();

        String parameter = StringUtils.join(paramaters, " ");

        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className, parameter);

        String cmd = String.format("Run with command: %s", StringUtils.join(builder.command(), " "));
        logger.debug(cmd);

        //Process process = Runtime.getRuntime().exec(cmd);

        Process process = builder.start();

        process.waitFor();

        logger.debug("Process return: " + process.exitValue());
        return process.exitValue();
    }
}
