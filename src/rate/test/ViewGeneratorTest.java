package rate.test;

import rate.engine.view.GenerateStrategy.AbstractGenerateStrategy;
import rate.engine.view.GenerateStrategy.GenerateAllStrategy;
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
        strategy.setImportTag("newbioverify");
        strategy.setDescription("yyk generated newbioveiry; not from webpage coz it said lock wait time out");
        strategy.setViewName("newbioverify-2013-04-01");

        Generator generator = new Generator();
        generator.setGenerateStrategy(strategy);

        generator.generate();
    }
}
