package rate.test;

import rate.engine.benchmark.generator.AbstractGenerator;
import rate.engine.benchmark.generator.GeneralFVC2006Generator;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午9:00
 */
public class BenchmarkGeneratorTest {
    public static void main(String[] args) throws Exception {
        GeneralFVC2006Generator generator = new GeneralFVC2006Generator();
        generator.setClassCount(10);
        generator.setSampleCount(5);
        generator.generate();
    }
}
