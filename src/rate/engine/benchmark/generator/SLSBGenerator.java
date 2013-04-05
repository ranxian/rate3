package rate.engine.benchmark.generator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.*;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private String descFilePath;
    Random random = new Random();
    public SLSBGenerator() {
        // 运算量比较大，用 SMALL 测试
        setScale("SMALL");
    }

    public void setBenchmark(BenchmarkEntity benchmark) {
        super.setBenchmark(benchmark);
        frrBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FRR");
        farBenchmarkDir = FilenameUtils.concat(benchmark.dirPath(), "FAR");
        descFilePath = FilenameUtils.concat(benchmark.dirPath(), "desc.txt");
    }

    public BenchmarkEntity generate() throws Exception {
//        if (B4Frr == 0 || B4Far == 0) throw new GeneratorException("B for Far or Frr not set!");

        // Experimental way
        if (B4Frr == 0 || B4Far == 0) {
            B4Frr = 10;
            B4Far = 10;
        }

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
        benchmark.setType("SLSB");
        new File(frrBenchmarkDir).mkdir();
        new File(farBenchmarkDir).mkdir();
        BufferedWriter wr = new BufferedWriter(new FileWriter(descFilePath));
        wr.append(B4Frr + " " + B4Far + "\r\n");
        wr.close();
    }

    public String getFarBenchmarkFilePath(int i, int j) {
        return FilenameUtils.concat(farBenchmarkDir, i + "" + j + ".txt");
    }

    public String getFrrBenchmarkFilePath(int b) {
        return FilenameUtils.concat(frrBenchmarkDir, b + ".txt");
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

    public void generateFarBenchmark(PrintWriter generalPw) throws Exception {
        int classCount = selectedMap.size();
        DebugUtil.debug("classcount = " + classCount);
        Set<Integer> generalSelectedSet = new HashSet<Integer>();

        for (int i = 1; i <= B4Far; i++) {  // Generate S[1], S[2], ..., S[B4Far]
            Set<Integer> set = new HashSet<Integer>();

            // 见论文中对划分子集的描述
            if (classCount % 2 == 0) {
                int modN = i % (classCount-1);
                for (int j = 1; j <= modN/2 + 1; j++) {
                    int k = modN - j;

                    if (k >= 1) {
                        set.add(j);
                        set.add(k);
                    }
                }
                for (int j = 1; i <= modN/2; i++) {
                    if (2*j == modN) set.add(j);
                }
            } else {
                int modN = (i-1) % classCount;

                for (int j = 1; j <= modN/2 + 1; j++) {
                    // j + k = (i-1) mod classCount;
                    int k = modN - j;
                    if (k >= 1) {
                        set.add(k);
                        set.add(j);
                    }
                }
            }
            List<Pair<ClazzEntity, List<SampleEntity>>> selectedThisTurn = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();
            Set<Integer> innerSet = new HashSet<Integer>();
            ArrayList<Integer> arrSet = new ArrayList<Integer>(set);
            for (int j = 1; j <= B4Far; j++) {
                selectedThisTurn.clear();
                innerSet.clear();
                File benchmarkFile = new File(getFarBenchmarkFilePath(i, j));
                PrintWriter pw = new PrintWriter(benchmarkFile);

                for (int k = 0; k < arrSet.size(); k++) {
                    innerSet.add(random.nextInt(arrSet.size()));
                }

                for (int k : innerSet) {
                    selectedThisTurn.add(selectedMap.get(arrSet.get(k)));
                }
                generateInterClazz(pw, selectedThisTurn);
                pw.close();
            }

            // S[i] generated
            for (int j : set) {
                selectedThisTurn.add(selectedMap.get(j-1));  // (j-1) is right
                generalSelectedSet.add(j);
            }
        }

        List<Pair<ClazzEntity, List<SampleEntity>>> generalSelected = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();

        for (int j : generalSelectedSet) {
            generalSelected.add(selectedMap.get(j-1));
        }
        generateInterClazz(generalPw, generalSelected);
    }
}

