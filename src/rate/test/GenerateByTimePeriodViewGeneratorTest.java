package rate.test;

import rate.engine.view.GenerateStrategy.AbstractGenerateStrategy;
import rate.engine.view.GenerateStrategy.GenerateAllStrategy;
import rate.engine.view.GenerateStrategy.GenerateByTimePeriodStrategy;
import rate.engine.view.Generator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午8:47
 */
public class GenerateByTimePeriodViewGeneratorTest {
    public static void main(String[] args) throws Exception {
        GenerateByTimePeriodStrategy strategy = new GenerateByTimePeriodStrategy();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Timestamp start = new Timestamp(format.parse("01-09-2010").getTime());
        strategy.setStartTimeStamp(start);
        Timestamp end = new Timestamp(format.parse("01-02-2011").getTime());
        strategy.setEndTimeStamp(end);

        Generator generator = new Generator();
        generator.setGenerateStrategy(strategy);

        generator.generate();
    }
}
