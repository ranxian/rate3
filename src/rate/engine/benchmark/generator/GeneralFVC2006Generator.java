package rate.engine.benchmark.generator;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

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
            File dir = new File(benchmarkEntity.dirPath());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File benchmarkFile = new File(benchmarkEntity.filePath());
            if (benchmarkFile.exists()) {
                throw new GeneratorException("The benchmark file already exists.");
            }
            benchmarkFile.createNewFile();
            PrintWriter pw = new PrintWriter(new FileOutputStream(benchmarkFile));

            Session session = HibernateUtil.getSession();
            Query query = session.createQuery("from ViewSampleEntity V left join SampleEntity S where ViewSampleEntity.viewUuid=:viewUuid");
            query.setParameter("viewUuid", getView().getUuid());

            //List<ClazzEntity> clazzes =



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
