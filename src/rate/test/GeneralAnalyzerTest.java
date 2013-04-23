package rate.test;

import rate.engine.benchmark.analyzer.Analyzer;
import rate.engine.benchmark.analyzer.GeneralAnalyzer;
import rate.model.TaskEntity;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午7:33
 */
public class GeneralAnalyzerTest extends BaseTest {
    public static void main(String[] args) throws Exception {
        TaskEntity task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid").
                setParameter("uuid", "7b53dae7-d51d-4e3e-a75c-ef1c4e994db4")
                .list().get(0);

        Analyzer analyzer = new GeneralAnalyzer();
        analyzer.setTask(task);
        analyzer.analyze();
    }
}
