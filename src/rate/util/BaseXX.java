package rate.util;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-20
 * Time: 下午10:56
 */
public class BaseXX {
    private static int base = 93;

    public static String parse(int num) {
        String res = "";
        while (num!=0) {
            int mod = num % base;
            num /= base;
            res += (char)('!'+mod);
        }
        return res;
    }
}
