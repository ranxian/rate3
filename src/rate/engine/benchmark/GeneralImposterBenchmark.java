package rate.engine.benchmark;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.util.DebugUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-6
 * Time: 下午4:16
 */
public class GeneralImposterBenchmark extends AbstractBenchmark {
    private static final Logger logger = Logger.getLogger(GeneralImposterBenchmark.class);

    public void setImposterClassAndSamples(List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples) {
        this.imposterClassAndSamples = imposterClassAndSamples;
    }

    protected List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples=null;

    public void setImpostedClassAndSamples(List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples) {
        this.impostedClassAndSamples = impostedClassAndSamples;
    }

    protected List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples=null;

    public void prepare() throws Exception {
        benchmark.setType("GeneralImposter");
        prepareBenchmarkDir();
        File benchmarkFile = new File(benchmark.filePath());
        benchmarkFile.createNewFile();
    }

    public BenchmarkEntity generate() throws Exception {
        if ( imposterClassAndSamples==null || impostedClassAndSamples==null
                || view==null) {
            throw new GeneratorException("Parameters not specified");
        }
        DebugUtil.debug("here");
        prepare();
        PrintWriter pw = new PrintWriter(new FileOutputStream(benchmark.filePath()));

        DebugUtil.debug(String.format("Begin generation for benchmark [%s]", benchmark.getUuid()));

        // imposter and imposted
        Iterator imposterClassAndSamplesIterator = imposterClassAndSamples.iterator();
        int countOfMatches=0;
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
                        countOfMatches++;
                    }
                }
            }
        }

        // TODO: I think we should add inner-class for imposters.

        pw.close();

        benchmark.setDescription(String.format("Num of imposter classes: %d, num of imposted classes: %d, total matches: %d",
                imposterClassAndSamples.size(), impostedClassAndSamples.size(), countOfMatches));

        return benchmark;
    }
}
