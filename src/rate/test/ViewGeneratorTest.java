package rate.test;

import rate.engine.view.GenerateStrategy.GenerateByImportTagStrategy;
import rate.engine.view.Generator;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午8:47
 */
public class ViewGeneratorTest {
    public static void main(String[] args) throws Exception {
        GenerateByImportTagStrategy strategy = new GenerateByImportTagStrategy();
        strategy.setImportTag("20121209test");
        Generator generator = new Generator();
        generator.setGenerateStrategy(strategy);
        generator.generate();
    }
}
