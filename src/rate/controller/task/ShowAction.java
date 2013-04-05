package rate.controller.task;

import rate.controller.RateActionBase;
import rate.engine.task.GeneralTask;
import rate.model.TaskEntity;
import rate.util.StringUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends RateActionBase {

    public String execute() throws Exception {
        return SUCCESS;
    }

    public void setUuid(String uuid) throws Exception {
        this.uuid = uuid;
        this.task = (TaskEntity) session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", uuid).list().get(0);
        generalTask = new GeneralTask(task);
    }

    private String uuid;

    public TaskEntity getTask() {
        return task;
    }

    public String getUuid() {
        return StringUtil.shorterUuid(this.uuid);
    }

    protected TaskEntity task;

    public GeneralTask getGeneralTask() {
        return generalTask;
    }

    protected GeneralTask generalTask;
}
