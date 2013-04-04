package rate.engine.benchmark.generator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.*;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-26
 * Time: 下午4:35
 */
public class SLSBGenerator extends GeneralGenrator {
    public int getB4Frr() {
        return B4Frr;
    }

    public void setB4Frr(int b4Frr) {
        B4Frr = b4Frr;
    }

    public int getB4Far() {
        return B4Far;
    }

    public void setB4Far(int b4Far) {
        B4Far = b4Far;
    }

    private int B4Frr;
    private int B4Far;

    private String frrBenchmarkDir;
    private String farBenchmarkDir;
    Random random = new Random();
    public SLSBGenerator() {
        // 运算量比较大，用 SMALL 测试
        setScale("SMALL");
    }

    public void setBenchmark(BenchmarkEntity benchmark) {
        super.setBenchmark(benchmark);
        frrBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FRR");
        farBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FAR");
    }

    public BenchmarkEntity generate() throws Exception {
        if (B4Frr == 0 || B4Far == 0) throw new GeneratorException("B for Far or Frr not set!");

        prepare();

        PrintWriter generalPw = new PrintWriter(benchmark.filePath());

        generateFrrBenchmark(generalPw);

        generateFarBenchmark(generalPw);

        benchmark.setDescription("This is a benchmark generate by the Second Level Subset Bootstrap method");

        generalPw.close();
        return benchmark;
    }

    public void prepare() throws Exception{
        super.prepare();
        new File(frrBenchmarkDir).mkdir();
        new File(farBenchmarkDir).mkdir();

    }

    public String getFarBenchmarkFilePath(int b) {
        return FilenameUtils.concat(frrBenchmarkDir, b + ".txt");
    }

    public String getFrrBenchmarkFilePath(int b) {
        return FilenameUtils.concat(farBenchmarkDir, b + ".txt");
    }

    public void generateFrrBenchmark(PrintWriter generalPw) throws Exception {
        int classCount = selectedMap.size();
        Set<Integer> generalSelectedSet = new HashSet<Integer>();
        for (int i = 1; i <= B4Frr; i++) {
            File benchmarkFile = new File(getFrrBenchmarkFilePath(i));
            benchmarkFile.createNewFile();
            PrintWriter pw = new PrintWriter(benchmarkFile);
            List<Pair<ClazzEntity, List<SampleEntity>>> selectedThisTurn = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();
            Set<Integer> set = new HashSet<Integer>();

            for (int j = 0; j < classCount; j++) {
                set.add(random.nextInt(classCount));
                generalSelectedSet.add(j);
            }

            for (int j : set) {
                selectedThisTurn.add(selectedMap.get(j));
            }

            generateInnerClazz(pw, selectedThisTurn);
            pw.close();
        }

        List<Pair<ClazzEntity, List<SampleEntity>>> generalSelected = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();
        for (int i : generalSelectedSet) {
            generalSelected.add(selectedMap.get(i));
        }
        generateInnerClazz(generalPw, generalSelected);
    }

    public void generateFarBenchmark(PrintWriter generalPw) {

    }
}

