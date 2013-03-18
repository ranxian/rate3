package rate.util;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-18
 * Time: 下午3:09
 */
public class StringUtil {
    static int TRUNCATE_LENGTH = 8;

    public static String truncate(String str) {
        int length = str.length();
        if (length <= 8) return str;
        else {
            str = str.substring(0, TRUNCATE_LENGTH);
            str.concat("...");
            return str;
        }
    }
}
