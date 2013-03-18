package rate.test;

import rate.util.RateConfig;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:58
 */
public class RateConfigTest {
    public static void main(String[] args) throws Exception {
        System.out.println(RateConfig.getRootDir());
        System.out.println(RateConfig.getSampleRootDir());
        System.out.println(RateConfig.getZipRootDir());
        System.out.println(RateConfig.getClassPath());
    }
}
