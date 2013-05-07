package rate.controller.task;

import rate.controller.RateActionBase;
import rate.engine.task.GeneralTask;
import rate.model.TaskEntity;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
public class TaskActionBase extends RateActionBase  {
    public void setUuid(String uuid) throws Exception {
        this.uuid = uuid;
        this.task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", uuid).list().get(0);
        generalTask = new GeneralTask(task);
        System.out.println("uuid and task prepared");
    }

    private String uuid;

    public TaskEntity getTask() {
        return task;
    }

    protected TaskEntity task;

    public GeneralTask getGeneralTask() {
        return generalTask;
    }

    protected GeneralTask generalTask;
}
