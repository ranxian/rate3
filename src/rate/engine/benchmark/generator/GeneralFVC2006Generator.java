package rate.engine.benchmark.generator;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
        if (classCount==0 || sampleCount==0 || getView()==null) {
            throw new GeneratorException("No classCount or sampleCount specified");
        }

        BenchmarkEntity benchmarkEntity = null;

        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();

            benchmarkEntity = new BenchmarkEntity();
            benchmarkEntity.setViewByViewUuid(this.getView());
            session.save(benchmarkEntity);

            // create the directory
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

            Query query = session.createQuery("select distinct V.sampleBySampleUuid.clazzByClassUuid from ViewSampleEntity as V where V.viewByViewUuid=:view order by RAND()");
            logger.debug(this.getView().getUuid());
            query.setParameter("view", this.getView());
            query.setMaxResults(this.classCount);

            List<ClazzEntity> selectedClasses = query.list();

            if (selectedClasses.size() < this.classCount) {
                throw new GeneratorException("Not enough classes");
            }

            HashMap<ClazzEntity, List<SampleEntity>> selectedMap = new HashMap<ClazzEntity, List<SampleEntity>>();
            for (int i=0; i<selectedClasses.size(); i++) {
                ClazzEntity classEntity = selectedClasses.get(i);
                query = session.createQuery("from ViewSampleEntity as V where V.sampleBySampleUuid.clazzByClassUuid=:clazz order by rand()");
                query.setMaxResults(this.sampleCount);
                query.setParameter("clazz", classEntity);
                List<SampleEntity> selectedSamples = (List<SampleEntity>)query.list();
                if (selectedSamples.size() < this.sampleCount) {
                    throw new GeneratorException("Not enough samples for class with uuid " + classEntity.getUuid());
                }
                selectedMap.put(classEntity, selectedSamples);
            }

            Iterator iterator = selectedMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                System.out.println(entry.getKey());
                List<SampleEntity> samples = (List<SampleEntity>)entry.getValue();
                for (SampleEntity sample : samples) {
                      System.out.println("\t" + sample.getUuid());
                }
            }





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
