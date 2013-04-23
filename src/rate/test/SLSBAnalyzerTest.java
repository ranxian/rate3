package rate.test;

import rate.engine.benchmark.analyzer.SLSBAnalyzer;
import rate.engine.task.SLSBTask;
import rate.model.TaskEntity;
import rate.util.DebugUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-13
 * Time: 下午10:01
 */
public class SLSBAnalyzerTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        SLSBAnalyzer slsbAnalyzer = new SLSBAnalyzer();
        TaskEntity task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid")
                .setParameter("uuid", "83750d39-895e-41e4-9797-7be373b80c3b")
                .list().get(0);
        SLSBTask slsbTask = new SLSBTask(task);
        DebugUtil.debug(slsbTask.getBenchmark().dirPath());
        slsbAnalyzer.setTask(task);
        slsbAnalyzer.setAlpha(0);
//        slsbAnalyzer.analyze();
        slsbAnalyzer.analyzeTotalFMR();
        slsbAnalyzer.analyzeTotalFNMR();
    }
}
