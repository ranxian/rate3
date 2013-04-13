package rate.engine.benchmark.analyzer;

import javafx.util.Pair;
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

//        analyzeTotalFMR();
//
//        analyzeTotalFNMR();
//
//        analyzeTotalERR();
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
        BufferedReader reader = new BufferedReader(new FileReader(inFilePath));
        PrintWriter writer = new PrintWriter(new FileWriter(outFilePath));

        List<String> list = new ArrayList<String>();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            String[] sp = line.split(" ");
            reader.readLine();
            reader.readLine();

            double score = (Double)hashedResult.get(sp[0]+sp[1]);
            list.add(sp[0] + " " + sp[1] + " " + score);
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
                analyzeFMR(slsbTask.getFarResultPathByNum(1, 1), slsbTask.getFarResultPathByNum(i, j)+"-result.txt");
            }
        }
    }

    public void analyzeTotalFNMR() throws Exception {
        List<Pair<Double, Double>> fnmrList = new ArrayList<Pair<Double, Double>>();
        List<BufferedReader> readerList = new LinkedList<BufferedReader>();
        List<Pair<Double, Double>> scoreList = new ArrayList<Pair<Double, Double>>();

        for (int i = 1; i <= B4Frr; i++) {
            analyzeFNMR(slsbTask.getFrrResultPathByNum(i), slsbTask.getFrrResultPathByNum(i)+"-result.txt");
        }

        for (int i = 0; i < B4Frr; i++) {
            readerList.add(new BufferedReader(new FileReader(slsbTask.getFrrResultPathByNum(i+1)+"-result.txt")));
            String line = readerList.get(i).readLine();
            scoreList.add(new Pair<Double, Double>(Double.parseDouble(line.split(" ")[0]), Double.parseDouble(line.split(" ")[1])));
        }

        double t = 0.0;

        for (int i = 0; i < 1000; i++) {
            List<Double> list = new ArrayList<Double>();
            for (int j = 0; j < scoreList.size(); j++) {
//                DebugUtil.debug(scoreList.get(j).toString());

                if (scoreList.get(j).getValue() > t || scoreList.get(j).getValue() == 0.0) {
                    list.add(scoreList.get(j).getKey());
                } else {
                    while (true) {
                        String[] sp = readerList.get(j).readLine().split(" ");
                        scoreList.set(j, new Pair<Double, Double>(Double.parseDouble(sp[0]), Double.parseDouble(sp[1])));
                        if (scoreList.get(j).getKey() > t || scoreList.get(j).getValue() == 0.0) {
                            list.add(scoreList.get(j).getKey());
                            break;
                        }
                    }
                }
            }

            t += 0.001;
            Collections.sort(list);
            int lower = (int)(alpha/2*list.size()+0.5);
            int higher = (int)((1-alpha/2)*list.size());
            fnmrList.add(new Pair<Double, Double>(list.get(lower), list.get(higher)));
        }

        t = 0.0;

        PrintWriter writer = new PrintWriter(new FileWriter(slsbTask.getFarResultPath()));
        for (int i = 0; i < 1000; i++) {
            writer.println(String.format("%.3f %s %s", t, fnmrList.get(i).getKey(), fnmrList.get(i).getValue()));
            t += 0.001;
        }

        writer.close();
    }

    private void analyzeTotalERR() {

    }

    private void hashResult() throws Exception {
        String resultFile = slsbTask.getResultFilePath();
        BufferedReader reader = new BufferedReader(new FileReader(resultFile));

        while (reader.ready()) {
            String line = reader.readLine();
            String[] results = line.split(" ");
            if (results.length == 4) continue; // This is a enroll result
            hashedResult.put(results[0]+results[1], Double.parseDouble(results[4]));
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
