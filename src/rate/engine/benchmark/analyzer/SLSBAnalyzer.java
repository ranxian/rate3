package rate.engine.benchmark.analyzer;

//import java.util.Pair;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import rate.engine.benchmark.SLSBBenchmark;
import rate.engine.task.SLSBTask;
import rate.model.TaskEntity;
import rate.util.DebugUtil;

import java.io.*;
import java.util.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午4:53
 */
public class SLSBAnalyzer extends Analyzer implements Comparator<String> {
    private int B4Far;
    private int B4Frr;
    private int K;
    private double alpha;
    private static final Logger logger = Logger.getLogger(SLSBAnalyzer.class);
    private SLSBTask slsbTask;
    private SLSBBenchmark benchmark;
    private HashMap hashedResult = new HashMap();

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setTask(TaskEntity task) throws Exception {
        this.slsbTask = new SLSBTask(task);
        DebugUtil.debug(task.getDirPath());
        this.B4Frr = slsbTask.getB4Frr();
        this.B4Far = slsbTask.getB4Far();
        this.benchmark = new SLSBBenchmark();
        this.alpha = 0.1;
        benchmark.setBenchmark(slsbTask.getBenchmark());
        benchmark.getBenchmarkDesc();
        B4Far = benchmark.getB4Far();
        B4Frr = benchmark.getB4Frr();
        K = benchmark.getK();
    }

    public void analyze() throws Exception {
        hashResult();

        calcFRRbenchmark();

        calcFarBenchmark();

        analyzeTotalFMR();

        analyzeTotalFNMR();

        analyzeTotalERR();
    }

    private void calcFRRbenchmark() throws Exception {
        String outputDir = slsbTask.getFrrResultDir();
        new File(outputDir).mkdir();
        for (int i = 1; i <= B4Frr; i++) {
            String benchmarkFilePath = benchmark.getFrrBenchmarkFilePath(i);
            String outputFilePath = FilenameUtils.concat(outputDir, i + ".txt");
            new File(outputFilePath).createNewFile();

            fillResult(benchmarkFilePath, outputFilePath);
        }
    }

    public void setK(int k) {
        K = k;
    }

    private void calcFarBenchmark() throws Exception {
        String outputDir = slsbTask.getFarResultDir();
        new File(outputDir).mkdir();
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= B4Far; j++) {
                String benchmarkFilePath = benchmark.getFarBenchmarkFilePath(i, j);
                String outputFilePath = FilenameUtils.concat(outputDir, i + "" + j+".txt");
                new File(outputFilePath).createNewFile();
                fillResult(benchmarkFilePath, outputFilePath);
            }
        }
    }

    private void fillResult(String inFilePath, String outFilePath) throws Exception {
        Iterator iterator = hashedResult.keySet().iterator();

        BufferedReader reader = new BufferedReader(new FileReader(inFilePath));
        PrintWriter writer = new PrintWriter(new FileWriter(outFilePath));

        List<String> list = new ArrayList<String>();
        Set ignoreSet = new HashSet();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            String[] sp = line.split(" ");
            if (hashedResult.containsKey(sp[0]+sp[1])) {
                double score = (Double)hashedResult.get(sp[0]+sp[1]);
                list.add(sp[0] + " " + sp[1] + " " + score);
            } else {
                ignoreSet.add(sp[0]+sp[1]);
                DebugUtil.debug(sp[0] + " matches " + sp[1] + " not found");
            }
        }

        Collections.sort(list, this);

        for (String line : list) {
            writer.println(line);
        }
        reader.close();
        writer.close();
    }

    public void analyzeTotalFMR() throws Exception {
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= B4Far; j++) {
                analyzeFMR(slsbTask.getFarResultPathByNum(i, j), slsbTask.getFarResultPathByNum(i, j)+"-result.txt");
            }
            List<Pair<Double, Double>> fmrList = new ArrayList<Pair<Double, Double>>();
            List<BufferedReader> readerList = new LinkedList<BufferedReader>();
            // scoreList, [<阈值，分数>]
            List<Pair<Double, Double>> scoreList = new ArrayList<Pair<Double, Double>>();
            for (int j = 0; j < B4Far; j++) {
                readerList.add(new BufferedReader(new FileReader(slsbTask.getFarResultPathByNum(i, j+1)+"-result.txt")));
                String line = readerList.get(j).readLine();
                scoreList.add(new ImmutablePair<Double, Double>(Double.parseDouble(line.split(" ")[0]), Double.parseDouble(line.split(" ")[1])));
            }
            double t = 1.0;
            for (int j = 0; j < 1000; j++) {
                List<Double> list = new ArrayList<Double>();

                for (int k = 0; k < scoreList.size(); k++) {
                    if (scoreList.get(k).getValue() <= t || scoreList.get(k).getValue() == 0.0) {
                        list.add(scoreList.get(k).getKey());
                    } else {
                        while (true) {
                            String[] sp = readerList.get(k).readLine().split(" ");
                            scoreList.set(k, new ImmutablePair<Double, Double>(Double.parseDouble(sp[0]), Double.parseDouble(sp[1])));
                            if (scoreList.get(k).getValue() <= t || scoreList.get(k).getValue() == 0.0) {
                                list.add(scoreList.get(k).getKey());
                                break;
                            }
                        }
                    }
                }

                t -= 0.001;
                Collections.sort(list);
                int lower = (int)(alpha/2*list.size());
                int higher = (int)((1-alpha/2)*list.size());
                if (lower < 0) lower = 0;
                if (higher >= list.size()) higher = list.size()-1;
                fmrList.add(new ImmutablePair<Double, Double>(list.get(lower), list.get(higher)));
            }
            t = 1.0;

            PrintWriter writer = new PrintWriter(new FileWriter(slsbTask.getFarResultPath(i)));
            for (int j = 0; j < 1000; j++) {
                writer.println(String.format("%.3f %s %s", t, fmrList.get(j).getKey(), fmrList.get(j).getValue()));
                t -= 0.001;
            }

            writer.close();
            for (BufferedReader reader : readerList) {
                reader.close();
            }
        }

        List<BufferedReader> readerList = new ArrayList<BufferedReader>();
        List<Pair<Double, Double>> fmrList = new ArrayList<Pair<Double, Double>>();
        for (int i = 1; i <= K; i++) {
            readerList.add(new BufferedReader(new FileReader(slsbTask.getFarResultPath(i))));
        }

        double t = 1.0;
        for (int i = 0; i < 1000; i++) {
            double  lowBound = 0.0;
            double highBound = 0.0;
            for (BufferedReader reader : readerList) {
                String[] sp = reader.readLine().split(" ");
                lowBound += Double.parseDouble(sp[1]);
                highBound += Double.parseDouble(sp[2]);
            }
            lowBound /= K;
            highBound /= K;
            fmrList.add(new ImmutablePair<Double, Double>(lowBound, highBound));
        }

        for (BufferedReader reader : readerList) {
            reader.close();
        }

        PrintWriter writer = new PrintWriter(new FileWriter(slsbTask.getFarResultPath()));
        t = 1.0;
        for (int i = 0; i < 1000; i++) {
            writer.println(String.format("%.3f %s %s", t, fmrList.get(i).getKey(), fmrList.get(i).getValue()));
            t -= 0.001;
        }
        writer.close();
    }

    public void analyzeTotalFNMR() throws Exception {
        List<Pair<Double, Double>> fnmrList = new ArrayList<Pair<Double, Double>>();
        List<BufferedReader> readerList = new LinkedList<BufferedReader>();
        // scoreList, [<阈值，分数>]
        List<Pair<Double, Double>> scoreList = new ArrayList<Pair<Double, Double>>();

        for (int i = 1; i <= B4Frr; i++) {
            analyzeFNMR(slsbTask.getFrrResultPathByNum(i), slsbTask.getFrrResultPathByNum(i)+"-result.txt");
        }

        for (int i = 0; i < B4Frr; i++) {
            readerList.add(new BufferedReader(new FileReader(slsbTask.getFrrResultPathByNum(i+1)+"-result.txt")));
            String line = readerList.get(i).readLine();
            scoreList.add(new ImmutablePair<Double, Double>(Double.parseDouble(line.split(" ")[0]), Double.parseDouble(line.split(" ")[1])));
        }

        double t = 0.0;

        for (int i = 0; i < 1000; i++) {
            List<Double> list = new ArrayList<Double>();
            for (int j = 0; j < scoreList.size(); j++) {

                if (scoreList.get(j).getValue() > t || scoreList.get(j).getValue() == 0.0) {
                    list.add(scoreList.get(j).getKey());
                } else {
                    while (true) {
                        String[] sp = readerList.get(j).readLine().split(" ");
                        scoreList.set(j, new ImmutablePair<Double, Double>(Double.parseDouble(sp[0]), Double.parseDouble(sp[1])));
                        if (scoreList.get(j).getValue() > t || scoreList.get(j).getValue() == 0.0) {
                            list.add(scoreList.get(j).getKey());
                            break;
                        }
                    }
                }
            }

            t += 0.001;
            Collections.sort(list);
            int lower = (int)(alpha/2*list.size());
            int higher = (int)((1-alpha/2)*list.size());
            if (lower < 0) lower = 0;
            if (higher >= list.size()) higher = list.size()-1;
            fnmrList.add(new ImmutablePair<Double, Double>(list.get(lower), list.get(higher)));
        }

        t = 0.0;

        PrintWriter writer = new PrintWriter(new FileWriter(slsbTask.getFrrResultPath()));
        for (int i = 0; i < 1000; i++) {
            writer.println(String.format("%.3f %s %s", t, fnmrList.get(i).getKey(), fnmrList.get(i).getValue()));
            t += 0.001;
        }

        writer.close();
        for (BufferedReader reader : readerList) {
            reader.close();
        }
    }

    private void analyzeTotalERR() {

    }

    private void hashResult() throws Exception {
        String resultFile = slsbTask.getBxxResultFilePath();
        BufferedReader reader = new BufferedReader(new FileReader(resultFile));
        while (reader.ready()) {
            String line = reader.readLine();
            String[] results = line.split(" ");
            // DebugUtil.debug(results[0]+ " " + results[1] + " " + Double.parseDouble(results[4]));
            hashedResult.put(results[0] + results[1], Double.parseDouble(results[4]));
        }

        reader.close();
    }

    public int compare(String s1, String s2) {
        String ss1[] = s1.split(" ");
        String ss2[] = s2.split(" ");
        double d1 = Double.parseDouble(ss1[ss1.length-1]);
        double d2 = Double.parseDouble(ss2[ss2.length-1]);
        return (d1<d2 ? -1 : (d1==d2 ? 0 : 1));
    }
}
