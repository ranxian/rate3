package rate.engine.benchmark.generator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.*;
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
    private int B4Frr;
    private int B4Far;

    private String frrBenchmarkDir;
    private String farBenchmarkDir;
    Random random = new Random();
    SLSBGenerator() {
        // 运算量比较大，用 SMALL 测试
        setScale("SMALL");
        frrBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FRR");
        farBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FAR");
    }

    public BenchmarkEntity generate() throws Exception {
        if (B4Frr == 0 || B4Far == 0) throw new GeneratorException("B for Far or Frr not set!");

        prepare();

        generateFrrBenchmark();

        generateFarBenchmark();

        benchmark.setDescription("This is a benchmark generate by the Second Level Subset Bootstrap method");

        return benchmark;
    }

    public String getFarBenchmarkFilePath(int b) {
        return FilenameUtils.concat(frrBenchmarkDir, b + ".txt");
    }

    public String getFrrBenchmarkFilePath(int b) {
        return FilenameUtils.concat(farBenchmarkDir, b + ".txt");
    }

    public void generateFrrBenchmark() throws Exception{
        Set<ClazzEntity> set = new HashSet<ClazzEntity>();
        int classCount = selectedClasses.size();
        for (int i = 1; i <= B4Frr; i++) {
            set.clear();

            File benchmarkFile = new File(getFrrBenchmarkFilePath(i));
            benchmarkFile.createNewFile();
            PrintWriter pw = new PrintWriter(benchmarkFile);

            for (int j = 0; j < classCount; j++) {
                set.add(selectedClasses.get(random.nextInt(classCount)));
            }

            generateInnerClazz();
        }

    }

    public void generateFarBenchmark() {

    }
}

