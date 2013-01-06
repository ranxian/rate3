package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.util.Collection;


/**
 * Created by XianRan
 * Time: 下午12:41
 */
public class IndexAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(IndexAction.class);

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    private Collection<TaskEntity> tasks;

    public String execute() {
        tasks = HibernateUtil.getSession().createQuery("from TaskEntity order by created desc").list();
        logger.debug(tasks.size());
        return SUCCESS;
    }
}
