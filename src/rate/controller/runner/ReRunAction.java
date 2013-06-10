package rate.controller.runner;

import org.apache.commons.io.FileUtils;
import rate.controller.RateActionBase;
import rate.engine.benchmark.runner.RunnerInvoker;
import rate.model.TaskEntity;

import java.io.File;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-6-6
 * Time: 下午9:27
 */
public class ReRunAction extends RateActionBase {
    public String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String execute() throws Exception {
        TaskEntity task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", uuid)
                .list().get(0);
        task.setFinished(null);
        FileUtils.deleteDirectory(new File(task.getDirPath()));
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
        RunnerInvoker.reRun(this.uuid);
        return SUCCESS;
    }
}
