package rate.engine.benchmark;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.util.DebugUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-6
 * Time: 下午4:16
 */
public class GeneralBenchmark extends AbstractBenchmark {
    private static final Logger logger = Logger.getLogger(GeneralBenchmark.class);
    protected HashMap uuidTable = new HashMap();
    protected Set<Pair<String, String>> enrollSet = new HashSet<Pair<String, String>>();


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
    private int totalGenuineCount = 0;
    private int totalImposterCount = 0;
    List<ClazzEntity> selectedClasses = new ArrayList<ClazzEntity>();
    List<Pair<ClazzEntity, List<SampleEntity>>> selectedMap = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();

    public void generateInnerClazz(PrintWriter writer, List<Pair<ClazzEntity, List<SampleEntity>>> selected) throws Exception {
        Iterator iterator = selected.iterator();
        // inner class
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            List<SampleEntity> samples = (List<SampleEntity>)entry.getValue();
            for (int i=0; i<samples.size()-1; i++) {
                // Enroll
                SampleEntity sample1 = samples.get(i);
                // Match with remaining
                for (int j=i+1; j<samples.size(); j++) {
                    SampleEntity sample2 = samples.get(j);
                    if (sample1.getUuid().equals(sample2.getUuid())) {
                        logger.trace("Match uuid should not be the same one");
                        continue;
                    }
                    writer.println(String.format("%s %s G", uuidTable.get(sample1.getUuid()), uuidTable.get(sample2.getUuid())));
                    totalGenuineCount++;
                }
            }
        }
    }

    public void generateInterClazz(PrintWriter writer, List<Pair<ClazzEntity, List<SampleEntity>>> selected) throws Exception {
        for (int i=0; i<selected.size()-1; i++) {
            Pair<ClazzEntity, List<SampleEntity>> pair1 = selected.get(i);
            SampleEntity sample1 = pair1.getValue().get(0);
            // Match with remaining
            for (int j=i+1; j<selected.size(); j++) {
                Pair<ClazzEntity, List<SampleEntity>> pair2 = selected.get(j);
                SampleEntity sample2 = pair2.getValue().get(0);
                writer.println(String.format("%s %s I", uuidTable.get(sample1.getUuid()), uuidTable.get(sample2.getUuid())));
                totalImposterCount++;
            }
        }
    }

    public BenchmarkEntity generate() throws Exception {
        // 检查参数，建立文件夹，按照 sampleCount 和 classCount 生成备选样本空间
        prepare();
        // 生成类内匹配
        printEnroll();

        printUuidTable();

        PrintWriter writer = new PrintWriter(benchmark.getHexFilePath());
        generateInnerClazz(writer, selectedMap);
        writer.flush();
        // 生成类间匹配
        generateInterClazz(writer, selectedMap);
        writer.close();

        benchmark.setDescription(String.format("Num of classes: %d, num of samples in each class: %d, num of genuine attempts %d, num of imposter attempts",
                this.classCount, this.sampleCount, totalGenuineCount, totalImposterCount));

        return benchmark;
    }

    public void printEnroll() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(benchmark.getEnrollFilePath()));

        for (Pair<String, String> enroll : enrollSet) {
            writer.println(enroll.getKey() + " " + enroll.getValue());
        }
        writer.close();
    }

    public void printUuidTable() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(benchmark.getEnrollFilePath()));
        ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(uuidTable.entrySet());
        for (Map.Entry<String, String> entry : list) {
            writer.println(entry.getValue() + " " + entry.getKey());
        }
        writer.close();
    }

    public void prepare() throws Exception {
        benchmark.setType("General");
        prepareBenchMark();
        prepareSelectedClazz();
        prepareUuidTable();
    }

    public void prepareUuidTable() {
        Iterator<Pair<ClazzEntity, List<SampleEntity>>> iterator = selectedMap.iterator();
        while (iterator.hasNext()) {
            Pair<ClazzEntity, List<SampleEntity>> pair = iterator.next();
            for(SampleEntity sample : pair.getValue()) {
                if (!uuidTable.containsKey(sample.getUuid()))
                    uuidTable.put(sample.getUuid(), Integer.toHexString(uuidTable.size()+1));
            }
        }
    }

    public void prepareBenchMark() throws Exception{
        if (classCount==0 || sampleCount==0 || view ==null) {
            throw new GeneratorException("No classCount or sampleCount or view or generator name specified");
        }

        logger.trace(String.format("Class count [%d], sample count [%d]", this.classCount, this.sampleCount));
        logger.trace(String.format("Benchmark [%s], view [%s], benchmark name [%s]", benchmark.getUuid(), this.view.getUuid(), benchmark.getName()));

        // create the benchmark file
        File benchmarkDir = new File(benchmark.dirPath());
        benchmarkDir.mkdir();
        File benchmarkFile = new File(benchmark.filePath());
        benchmarkFile.createNewFile();
    }

    public void prepareSelectedClazz() throws Exception{
        Query query = session.createQuery("select distinct clazz from ViewSampleEntity where view=:view order by RAND()");
        query.setParameter("view", this.view);

        Iterator<ClazzEntity> clazzIterator = query.iterate();

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
            for (SampleEntity sample : selectedSamples) {
                enrollSet.add(new MutablePair<String, String>(sample.getUuid(), sample.getFile()));
            }
            if (selectedSamples.size() == 0) {
                DebugUtil.debug(clazz.getUuid() + " has no samples");
                continue;
            }
            selectedClasses.add(clazz);
            logger.trace(String.format("Add clazz [%s] [%d] of [%d]", clazz.getUuid(), selectedClasses.size(), this.classCount));
            Pair<ClazzEntity, List<SampleEntity>> newPair = new ImmutablePair<ClazzEntity, List<SampleEntity>>(clazz, selectedSamples);
            selectedMap.add(newPair);
        }
        if (selectedClasses.size() < this.classCount) {
            throw new GeneratorException("Not enough classes");
        }
    }

    public void setScale(String scale) {
        setSampleCount(5);
        if (scale.equals("SMALL")) {
            setClassCount(10);
        } else if (scale.equals("MEDIUM")) {
            setClassCount(50);
        } else if (scale.equals("LARGE")) {
            setClassCount(100);
        } else if (scale.equals("VERY_LARGE")) {
            setClassCount(1000);
        }
    }
}
