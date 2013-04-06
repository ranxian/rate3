package rate.engine.benchmark.analyzer;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import rate.engine.benchmark.SLSBBenchmark;
import rate.engine.task.SLSBTask;
import rate.model.TaskEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午4:53
 */
public class SLSBAnalyzer extends Analyzer {
    private int B4Far;
    private int B4Frr;
    private int K;
    private static final Logger logger = Logger.getLogger(SLSBAnalyzer.class);
    private SLSBTask slsbTask;
    private SLSBBenchmark benchmark;
    private HashMap hashedResult = new HashMap();


    public void setTask(TaskEntity task) throws Exception {
        this.slsbTask = new SLSBTask(task);
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

        analyzeTotalFMR();

        analyzeTotalFNMR();

        analyzeTotalERR();
    }

    private void calcFRRbenchmark() throws Exception {
        String outputDir = slsbTask.getFrrResultDir();
        for (int i = 1; i <= B4Frr; i++) {
            String benchmarkFilePath = benchmark.getFrrBenchmarkFilePath(i);
            String outputFilePath = FilenameUtils.concat(outputDir, i + ".txt");

            fillResult(benchmarkFilePath, outputFilePath);
        }
    }

    private void calcFarBenchmark() throws Exception {
        String outputDir = slsbTask.getFarResultDir();
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= B4Far; j++) {
                String benchmarkFilePath = benchmark.getFarBenchmarkFilePath(i, j);
                String outputFilePath = FilenameUtils.concat(outputDir, i + "" + j+".txt");

                fillResult(benchmarkFilePath, outputFilePath);
            }
        }
    }

    private void fillResult(String inFilePath, String outFilePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(inFilePath));
        PrintWriter writer = new PrintWriter(new FileWriter(outFilePath));

        while (reader.ready()) {
            String[] sp = reader.readLine().split(" ");
            reader.readLine();
            reader.readLine();

            double score = (Double)hashedResult.get(sp[0]+sp[1]);
            writer.println(sp[0] + " " + sp[1] + " " + score);
        }

        reader.close();
        writer.close();
    }

    private void analyzeTotalFMR() {

    }

    private void analyzeTotalFNMR() {

    }

    private void analyzeTotalERR() {

    }

    private void hashResult() throws Exception {
        String resultFile = slsbTask.getResultFilePath();
        BufferedReader reader = new BufferedReader(new FileReader(resultFile));

        while (reader.ready()) {
            String line = reader.readLine();
            String[] results = line.split(" ");
            if (results.length == 3) continue; // This is a enroll result
            hashedResult.put(results[0]+results[1], Double.parseDouble(results[3]));
        }

        reader.close();
    }
}
