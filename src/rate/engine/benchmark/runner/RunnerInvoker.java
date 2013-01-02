package rate.engine.benchmark.runner;

import org.apache.log4j.Logger;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.util.JavaProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public class RunnerInvoker {

    private static final Logger logger = Logger.getLogger(RunnerInvoker.class);

    public static void run(BenchmarkEntity benchmark, AlgorithmVersionEntity algorithmVersion) throws Exception {
        // TODO
        if (! benchmark.getProtocol().equals(algorithmVersion.getAlgorithmByAlgorithmUuid().getProtocol())) {
            throw new Exception(String.format("Protocol does not match, benchmark: %s algorithmVersion: %s", benchmark.getProtocol(), algorithmVersion.getAlgorithmByAlgorithmUuid().getProtocol()));
        }

        logger.info(String.format("Invoke with benchmark: %s algorithmVersion: %s", benchmark.getUuid(), algorithmVersion.getUuid()));

        List<String> parameters = new ArrayList<String>();
        parameters.add(benchmark.getUuid());
        parameters.add(algorithmVersion.getUuid());

        JavaProcess.exec(RunnerMain.class, parameters);


        // check protocol
        // new a process with RunnerMain, benchmark uuid and algorithmVersion uuid, use rate.util.JavaProcess
        // run the process and return
    }
}
