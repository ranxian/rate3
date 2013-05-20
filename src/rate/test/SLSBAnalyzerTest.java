package rate.test;

import rate.engine.benchmark.analyzer.SLSBAnalyzer;
import rate.engine.task.SLSBTask;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-13
 * Time: 下午10:01
 */
public class SLSBAnalyzerTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        session = HibernateUtil.getSession();
        SLSBAnalyzer slsbAnalyzer = new SLSBAnalyzer();
        TaskEntity task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid")
                .setParameter("uuid", "82dc7368-f698-40a2-8255-e09383a795bf")
                .list().get(0);
        SLSBTask slsbTask = new SLSBTask(task);
        DebugUtil.debug(slsbTask.getBenchmark().dirPath());
        slsbAnalyzer.setTask(task);
        slsbAnalyzer.setAlpha(0.05);
        slsbAnalyzer.analyze();
        task.setFinished(HibernateUtil.getCurrentTimestamp());
        session.beginTransaction();
        session.update(task);

//        slsbAnalyzer.analyzeTotalFMR();
//        slsbAnalyzer.analyzeTotalFNMR();
        session.close();
    }
}
