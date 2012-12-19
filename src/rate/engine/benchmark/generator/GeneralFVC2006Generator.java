package rate.engine.benchmark.generator;

import org.apache.log4j.Logger;
import rate.model.BenchmarkEntity;
import rate.util.RateConfig;

import java.io.File;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
public class GeneralFVC2006Generator extends AbstractGenerator {
    private static final Logger logger = Logger.getLogger(AbstractGenerator.class);

    public GeneralFVC2006Generator() {
        this.setProtocol("FVC2006");
    }

    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    private int classCount = 0;

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    private int sampleCount = 0;

    public BenchmarkEntity generate() throws GeneratorException {
        if (classCount==0 || sampleCount==0) {
            throw new GeneratorException("No classCount or sampleCount specified");
        }

        try {
            BenchmarkEntity benchmarkEntity = new BenchmarkEntity();
            // create the directory
            logger.debug(benchmarkEntity.getUuid());
            File dir = new File(RateConfig.getBenchmarkDir(benchmarkEntity.getUuid().toString()));
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // TODO
            // Create the enroll and match instructions and save it to file
            // Commit to database
        }
        catch (Exception ex) {
            logger.debug(ex);
            throw new GeneratorException("Failed to generate");
        }

        return null;
    }
}
