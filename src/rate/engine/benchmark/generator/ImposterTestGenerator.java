package rate.engine.benchmark.generator;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
public class ImposterTestGenerator extends AbstractGenerator {
    private static final Logger logger = Logger.getLogger(ImposterTestGenerator.class);

    public ImposterTestGenerator() {
        this.setProtocol("FVC2006");
        this.setGeneratorName("ImposterTestGenerator");
    }

    public void setImposterClassAndSamples(List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples) {
        this.imposterClassAndSamples = imposterClassAndSamples;
    }

    protected List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples=null;

    public void setImpostedClassAndSamples(List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples) {
        this.impostedClassAndSamples = impostedClassAndSamples;
    }

    protected List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples=null;

    public BenchmarkEntity generate() throws Exception {
        if ( imposterClassAndSamples==null || impostedClassAndSamples==null
                || getView()==null || getGeneratorName()==null || getBenchmarkName()==null) {
            throw new GeneratorException("Parameters not specified");
        }

        BenchmarkEntity benchmarkEntity = this.prepareBenchmark();

        File benchmarkFile = new File(benchmarkEntity.filePath());
        benchmarkFile.createNewFile();
        PrintWriter pw = new PrintWriter(new FileOutputStream(benchmarkFile));

        // imposter and imposted

        for (int i=0; i<imposterClassAndSamples.size(); i++) {
              // TODO: not done yet
        }

        /*
        Query query = session.createQuery("select distinct clazz from ViewSampleEntity where view=:view order by RAND()");
        query.setParameter("view", this.getView());

        Iterator<ClazzEntity> clazzIterator = query.iterate();
        List<ClazzEntity> imposterClasses = new ArrayList<ClazzEntity>();

        // select imposter classes
        //

        List<Pair<ClazzEntity, List<SampleEntity>>> selectedMap = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();

        int countIgnored = 0;

        while (clazzIterator.hasNext() && selectedClasses.size()<this.classCount) {
            ClazzEntity clazz = clazzIterator.next();
            int sampleCountOfClazz = ((Long)session.createQuery("select count(*) from ViewSampleEntity as V where V.clazz=:clazz")
                    .setParameter("clazz", clazz)
                    .list().get(0))
                    .intValue();
            if (sampleCountOfClazz < this.sampleCount) {
                logger.trace(String.format("Ignore clazz [%s], total [%d] ignored", clazz.getUuid(), ++countIgnored));
                continue;
            }
            query = session.createQuery("select V.sample from ViewSampleEntity as V where V.clazz=:clazz order by rand()");
            query.setMaxResults(this.sampleCount);
            query.setParameter("clazz", clazz);
            List<SampleEntity> selectedSamples = (List<SampleEntity>)query.list();
            selectedClasses.add(clazz);
            logger.trace(String.format("Add clazz [%s] [%d] of [%d]", clazz.getUuid(), selectedClasses.size(), this.classCount));
            Pair<ClazzEntity, List<SampleEntity>> newPair = new ImmutablePair<ClazzEntity, List<SampleEntity>>(clazz, selectedSamples);
            selectedMap.add(newPair);
        }

        if (selectedClasses.size() < this.classCount) {
            throw new GeneratorException("Not enough classes");
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
                Pair<ClazzEntity, List<SampleEntity>> pair2 = selectedMap.get(j);
                ClazzEntity class2 = pair2.getKey();
                SampleEntity sample2 = pair2.getValue().get(0);
                pw.println(String.format("M %s %s %s %s", class1.getUuid(), sample1.getUuid(), class2.getUuid(), sample2.getUuid()));
                pw.println(sample2.getFile());
            }
        }
        pw.close();
        */

        session.getTransaction().commit();

        return null;
    }
}
