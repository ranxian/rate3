package rate.controller.runner;

import rate.controller.RateActionBase;
import rate.engine.benchmark.runner.RunnerInvoker;
import rate.model.TaskEntity;

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
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
        RunnerInvoker.reRun(this.uuid);
        return SUCCESS;
    }
}
