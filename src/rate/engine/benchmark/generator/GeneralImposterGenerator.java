package rate.engine.benchmark.generator;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
public class GeneralImposterGenerator extends AbstractGenerator {
    private static final Logger logger = Logger.getLogger(GeneralImposterGenerator.class);

    public GeneralImposterGenerator() {
        this.setProtocol("FVC2006");
        this.setGeneratorName("GeneralImposterGenerator");
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

        session.beginTransaction();

        BenchmarkEntity benchmark = this.prepareBenchmark();

        File benchmarkFile = new File(benchmark.filePath());
        benchmarkFile.createNewFile();
        PrintWriter pw = new PrintWriter(new FileOutputStream(benchmarkFile));

        logger.trace(String.format("Begin generation for benchmark [%s]", benchmark.getUuid()));

        // imposter and imposted
        Iterator imposterClassAndSamplesIterator = imposterClassAndSamples.iterator();
        while (imposterClassAndSamplesIterator.hasNext()) {
            Map.Entry<ClazzEntity, List<SampleEntity>> imposter = (Map.Entry<ClazzEntity, List<SampleEntity>>)imposterClassAndSamplesIterator.next();
            ClazzEntity imposterClazz = imposter.getKey();
            List<SampleEntity> imposterSamples = imposter.getValue();

            for (int i=0; i<imposterSamples.size(); i++) {
                // Enroll the imposter sample
                SampleEntity imposterSample = imposterSamples.get(i);
                logger.trace(String.format("Enroll [%s]", imposterSample.getUuid()));
                pw.println(String.format("E %s %s", imposterClazz.getUuid(), imposterSample.getUuid()));
                pw.println(imposterSample.getFile());
                // Match with imposted samples
                Iterator impostedClassAndSamplesIterator = impostedClassAndSamples.iterator();
                while (impostedClassAndSamplesIterator.hasNext()) {
                    Map.Entry<ClazzEntity, List<SampleEntity>> imposted = (Map.Entry<ClazzEntity, List<SampleEntity>>)impostedClassAndSamplesIterator.next();
                    ClazzEntity impostedClazz = imposted.getKey();
                    List<SampleEntity> impostedSamples = imposted.getValue();
                    for (int j=0; j<impostedSamples.size(); j++) {
                        SampleEntity impostedSample = impostedSamples.get(j);
                        logger.trace(String.format("Match [%s]", impostedSample.getUuid()));
                        pw.println(String.format("M %s %s %s %s", imposterClazz.getUuid(), imposterSample.getUuid(), impostedClazz.getUuid(), impostedSample.getUuid()));
                        pw.println(impostedSample.getFile());
                    }
                }
            }
        }

        pw.close();

        session.getTransaction().commit();

        logger.trace(String.format("Finished generation for benchmark [%s]", benchmark.getUuid()));

        return benchmark;
    }
}
