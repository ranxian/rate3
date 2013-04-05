package rate.test;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import rate.engine.benchmark.runner.GeneralRunner;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
public class BenchmarkRunnerAnalyzeMethodTest {

    private static final Logger logger = Logger.getLogger(BenchmarkRunnerAnalyzeMethodTest.class);

    public static void main(String args[]) {
        try {
            String taskUuid = "7ea9d42f-3498-4e13-b178-e5edc7a5e6de";
            TaskEntity task = (TaskEntity)HibernateUtil.getSession()
                    .createQuery("from TaskEntity where uuid=:uuid")
                    .setParameter("uuid", taskUuid)
                    .list().get(0);

            GeneralRunner runner = new GeneralRunner();


            logger.trace(String.format("Task [%s]", task.getUuid()));

            File genuineFile = new File(FilenameUtils.concat(task.getDirPath(), "genuine.txt"));
            if (genuineFile.exists()) genuineFile.delete();
            File imposterFile = new File(FilenameUtils.concat(task.getDirPath(), "imposter.txt"));
            if (imposterFile.exists()) imposterFile.delete();

            runner.setTask(task);
            runner.prepare();
            runner.analyzeAll();

        }
        catch (Exception ex) {
            logger.error("", ex);
        }
    }
}
