package rate.engine.benchmark.generator;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class MediumFVC2006Generator extends GeneralFVC2006Generator {

    private static final Logger logger = Logger.getLogger(MediumFVC2006Generator.class);

    public MediumFVC2006Generator() {
        this.setGeneratorName("MediumFVC2006Generator");
        this.setClassCount(20);
        this.setSampleCount(8);
        logger.info("ClassCount:20 SampleCount:8");
    }
}
