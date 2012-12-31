package rate.engine.benchmark.generator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
        if (classCount==0 || sampleCount==0 || getView()==null || getGeneratorName()==null || getBenchmarkName()==null) {
            throw new GeneratorException("No classCount or sampleCount or view or generator name specified");
        }

        BenchmarkEntity benchmarkEntity = null;

        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();

            benchmarkEntity = new BenchmarkEntity();
            benchmarkEntity.setViewByViewUuid(this.getView());
            benchmarkEntity.setGenerator(this.getGeneratorName());
            benchmarkEntity.setName(getBenchmarkName());
            benchmarkEntity.setProtocol("FVC2006");

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


            List<Pair<ClazzEntity, List<SampleEntity>>> selectedMap = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();
            for (int i=0; i<selectedClasses.size(); i++) {
                ClazzEntity classEntity = selectedClasses.get(i);
                query = session.createQuery("select V.sampleBySampleUuid from ViewSampleEntity as V where V.sampleBySampleUuid.clazzByClassUuid=:clazz order by rand()");
                query.setMaxResults(this.sampleCount);
                query.setParameter("clazz", classEntity);
                List<SampleEntity> selectedSamples = (List<SampleEntity>)query.list();
                if (selectedSamples.size() < this.sampleCount) {
                    throw new GeneratorException("Not enough samples for class with uuid " + classEntity.getUuid());
                }
                Pair<ClazzEntity, List<SampleEntity>> newPair = new ImmutablePair<ClazzEntity, List<SampleEntity>>(classEntity, selectedSamples);
                selectedMap.add(newPair);
            }

            Iterator iterator = selectedMap.iterator();
            // inner class
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                ClazzEntity clazzEntity = (ClazzEntity)entry.getKey();
                List<SampleEntity> samples = (List<SampleEntity>)entry.getValue();
                for (int i=0; i<samples.size()-1; i++) {
                    // Enroll
                    SampleEntity sample1 = samples.get(i);
                    pw.println(String.format("E %s %s", clazzEntity.getUuid(), sample1.getUuid()));
                    pw.println(sample1.getFile());
                    // Match with remaining
                    for (int j=i+1; j<samples.size(); j++) {
                        SampleEntity sample2 = samples.get(j);
                        pw.println(String.format("M %s %s %s %s", clazzEntity.getUuid(), sample1.getUuid(), clazzEntity.getUuid(), sample2.getUuid()));
                        pw.println(sample2.getFile());
                    }
                }
            }

            // inter class
            for (int i=0; i<selectedMap.size()-1; i++) {
                Pair<ClazzEntity, List<SampleEntity>> pair1 = selectedMap.get(i);
                ClazzEntity class1 = pair1.getKey();
                SampleEntity sample1 = pair1.getValue().get(0);
                // Enroll
                pw.println(String.format("E %s %s", class1.getUuid(), sample1.getUuid()));
                pw.println(sample1.getFile());
                // Match with remaining
                for (int j=i+1; j<selectedMap.size(); j++) {
                    Pair<ClazzEntity, List<SampleEntity>> pair2 = selectedMap.get(i);
                    ClazzEntity class2 = pair1.getKey();
                    SampleEntity sample2 = pair1.getValue().get(0);
                    pw.println(String.format("M %s %s %s %s", class1.getUuid(), sample1.getUuid(), class2.getUuid(), sample2.getUuid()));
                    pw.println(sample2.getFile());
                }
            }
            pw.close();

            session.getTransaction().commit();
        }
        catch (Exception ex) {
            logger.debug(ex);
            throw new GeneratorException("Failed to generate");
        }

        return null;
    }
}
