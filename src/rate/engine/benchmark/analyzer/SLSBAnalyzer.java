package rate.engine.benchmark.analyzer;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import rate.engine.task.SLSBTask;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午4:53
 */
public class SLSBAnalyzer extends Analyzer {
    private int B4Far;
    private int B4Frr;
    private static final Logger logger = Logger.getLogger(SLSBAnalyzer.class);
    private SLSBTask slsbTask;


    public void setTask(TaskEntity task) throws Exception {
        this.slsbTask = new SLSBTask(task);
        this.B4Frr = slsbTask.getB4Frr();
        this.B4Far = slsbTask.getB4Far();
    }

    public void analyze() {
        calcChildBenchmark();

        analyzeTotalFMR();

        analyzeTotalFNMR();

        analyzeTotalERR();
    }

    private void calcChildBenchmark() {

    }

    private void analyzeTotalFMR() {

    }

    private void analyzeTotalFNMR() {

    }

    private void analyzeTotalERR() {

    }
}
