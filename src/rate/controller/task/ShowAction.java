package rate.controller.task;

import rate.controller.RateActionBase;
import rate.engine.task.GeneralTask;
import rate.engine.task.SLSBTask;
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
        if (!getIsUserSignedIn()) {
            return "eLogin";
        }
        if (task.getAlgorithmVersion().getAlgorithm().getAuthor().getUuid().equals(getCurrentUser().getUuid()) || getCurrentUser().isVip()) {
            return "eNotVip";
        }
        if (task.getBenchmark().getType().equals("SLSB")) {
            slsbTask = new SLSBTask(task);
            return "SLSB";
        }  else {
            generalTask = new GeneralTask(task);
            return SUCCESS;
        }
    }

    public void setUuid(String uuid) throws Exception {
        this.uuid = uuid;
        this.task = (TaskEntity) session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", uuid).list().get(0);
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
    protected SLSBTask slsbTask;

    public SLSBTask getSlsbTask() {
        return slsbTask;
    }
}
