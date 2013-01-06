package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(ShowAction.class);
    private final Session session = HibernateUtil.getSession();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String uuid;

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public TaskEntity getTask() {
        return task;
    }

    private TaskEntity task;

    public String execute() {
        logger.trace(String.format("Task uuid [%s]", uuid));
        this.task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", this.getUuid()).list().get(0);
        logger.trace(String.format("Get task with finished [%s]", task.getFinished().toString()));
        return SUCCESS;
    }
}
