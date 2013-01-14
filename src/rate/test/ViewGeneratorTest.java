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
        AbstractGenerateStrategy strategy = new GenerateAllStrategy();
        Generator generator = new Generator();
        generator.setGenerateStrategy(strategy);
        //strategy.getViewName();
        generator.generate();
    }
}
