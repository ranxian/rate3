package rate.engine.CLI;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-10
 * Time: AM11:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseUtils {
    public static String rateReadline() {
        System.out.print("rate> ");
        return System.console().readLine();
    }

    public static void ratePrintln(String msg) {
        System.out.println("--> " + msg);
    }
}
