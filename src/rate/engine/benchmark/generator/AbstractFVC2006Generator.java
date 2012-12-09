package rate.engine.benchmark.generator;

import org.apache.log4j.Logger;
import rate.model.BenchmarkEntity;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
abstract public class AbstractFVC2006Generator extends AbstractGenerator {
    private static final Logger logger = Logger.getLogger(AbstractGenerator.class);

    protected AbstractFVC2006Generator() {
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
            String uuidString = benchmarkEntity.getUuid().toString();
            // TODO
            // Create the directory
            // Create the enroll and match instructions and save it to file
            // Commit to database
        }
        catch (Exception ex) {
            logger.debug(ex);
            throw new GeneratorException("Generation failed");
        }

        return null;
    }
}
