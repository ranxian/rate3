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
    private int countOfMatches = 0;

    public void setImposterClassAndSamples(List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples) {
        this.imposterClassAndSamples = imposterClassAndSamples;
    }

    protected List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples=null;

    public void setImpostedClassAndSamples(List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples) {
        logger.debug("Imposted class and samples set");
        this.impostedClassAndSamples = impostedClassAndSamples;
    }

    protected List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples=null;

    public void prepare() throws Exception {
        if ( imposterClassAndSamples==null || impostedClassAndSamples==null
                || view==null) {
            throw new GeneratorException("Parameters not specified");
        }

        benchmark.setType("GeneralImposter");
        prepareBenchmarkDir();
    }


    public BenchmarkEntity generate() throws Exception {
        prepare();

        PrintWriter pw = new PrintWriter(new FileOutputStream(benchmark.getHexFilePath()));

        generateInterClassBenchmark(pw); // TODO: I think we should add inner-class for imposters.
        pw.close();

        printUuidTable();

        benchmark.setDescription(String.format("Num of imposter classes: %d, num of imposted classes: %d, total matches: %d",
                imposterClassAndSamples.size(), impostedClassAndSamples.size(), countOfMatches));

        return benchmark;
    }

    public void generateInterClassBenchmark(PrintWriter pw) {
        Iterator imposterClassAndSamplesIterator = imposterClassAndSamples.iterator();
        while (imposterClassAndSamplesIterator.hasNext()) {
            Map.Entry<ClazzEntity, List<SampleEntity>> imposter = (Map.Entry<ClazzEntity, List<SampleEntity>>)imposterClassAndSamplesIterator.next();
            List<SampleEntity> imposterSamples = imposter.getValue();

            for (int i=0; i<imposterSamples.size(); i++) {
                // Enroll the imposter sample
                SampleEntity imposterSample = imposterSamples.get(i);
                // Match with imposted samples
                Iterator impostedClassAndSamplesIterator = impostedClassAndSamples.iterator();
                while (impostedClassAndSamplesIterator.hasNext()) {
                    Map.Entry<ClazzEntity, List<SampleEntity>> imposted = (Map.Entry<ClazzEntity, List<SampleEntity>>)impostedClassAndSamplesIterator.next();
                    List<SampleEntity> impostedSamples = imposted.getValue();
                    for (int j=0; j<impostedSamples.size(); j++) {
                        SampleEntity impostedSample = impostedSamples.get(j);
                        logger.trace(String.format("Match [%s]", impostedSample.getUuid()));
                        pw.println(String.format("%s %s I", uuidTable.get(imposterSample.getUuid()), uuidTable.get(impostedSample.getUuid())));
                        countOfMatches++;
                    }
                }
            }
        }
    }
}
