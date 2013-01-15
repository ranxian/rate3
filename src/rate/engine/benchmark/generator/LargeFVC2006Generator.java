package rate.engine.benchmark.generator;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class LargeFVC2006Generator extends GeneralFVC2006Generator {

    private static final Logger logger = Logger.getLogger(LargeFVC2006Generator.class);

    public LargeFVC2006Generator() {
        this.setGeneratorName(LargeFVC2006Generator.class.getSimpleName());
        this.setClassCount(300);
        this.setSampleCount(10);
//        logger.info("ClassCount:100 SampleCount:10");
    }
}
