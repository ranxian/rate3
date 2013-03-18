package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.engine.task.FVC2006Task;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends RateActionBase {

    public String execute() throws Exception {
        System.out.println("here");
        System.out.println(uuid);
        return SUCCESS;
    }

    public void setUuid(String uuid) throws Exception {
        this.uuid = uuid;
        this.task = (TaskEntity) session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", uuid).list().get(0);
        fvc2006Task = new FVC2006Task(task);
    }

    private String uuid;

    public TaskEntity getTask() {
        return task;
    }

    protected TaskEntity task;

    public FVC2006Task getFvc2006Task() {
        return fvc2006Task;
    }

    protected FVC2006Task fvc2006Task;
}
