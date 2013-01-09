package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.task.FVC2006Task;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class DeleteAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(DeleteAction.class);
    private final Session session = HibernateUtil.getSession();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) throws Exception {
        logger.trace(String.format("get uuid [%s]", uuid));
        this.uuid = uuid;
        this.task = (TaskEntity)session.createQuery("from TaskEntity where uuid=:uuid").setParameter("uuid", this.getUuid()).list().get(0);
        fvc2006Task = new FVC2006Task(task);
        logger.trace(String.format("Task [%s]", task.getUuid()));
    }

    private String uuid;

    public TaskEntity getTask() {
        return task;
    }

    private TaskEntity task;

    public FVC2006Task getFvc2006Task() {
        return fvc2006Task;
    }

    private FVC2006Task fvc2006Task;

    public String execute() throws Exception {
        FileUtils.deleteDirectory(new File(task.getDirPath()));
        session.beginTransaction();
        session.delete(task);
        session.getTransaction().commit();

        return SUCCESS;
    }
}
