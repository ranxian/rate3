package rate.util;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-20
 * Time: 下午10:56
 */
public class BaseXX {
    private static String map = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM`1234567890-=~!@#$%^&*()_+|[];',./{}:<>?";
    private static int base = map.length();

    public static String parse(int num) {
        String res = "";
        while (num!=0) {
            int mod = num % base;
            num /= base;
            res += map.charAt(mod);
        }
        return res;
    }
}
