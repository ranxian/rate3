package rate.engine.benchmark.generator;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class SmallFVC2006Generator extends GeneralFVC2006Generator {

    private static final Logger logger = Logger.getLogger(SmallFVC2006Generator.class);

    public SmallFVC2006Generator() {
        this.setGeneratorName("Small");
        this.setClassCount(10);
        this.setSampleCount(5);
//        logger.info("ClassCount:10 SampleCount:5");
    }
}
