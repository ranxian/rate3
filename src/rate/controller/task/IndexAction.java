package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by XianRan
 * Time: 下午12:41
 */
public class IndexAction extends RateActionBase {

    private static final Logger logger = Logger.getLogger(IndexAction.class);

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    private Collection<TaskEntity> tasks = new ArrayList<TaskEntity>();

    public String execute() {
        if (getIsUserSignedIn() && getCurrentUser().isVip()) {
            tasks = session.createQuery("from TaskEntity order by created desc")
                    .setFirstResult(getFirstResult()).setMaxResults(itemPerPage)
                    .list();
        } else {
            List<TaskEntity> alltasks = session.createQuery("from TaskEntity order by created desc")
                    .setFirstResult(getFirstResult()).setMaxResults(itemPerPage*10)
                    .list();
            if (getIsUserSignedIn()) {
                for (TaskEntity task : alltasks) {
                    if (task.getRunnerName().equals(getCurrentUser().getName())) {
                        tasks.add(task);
                        if (tasks.size() >= 10) break;
                    }
                }
            }

        }
        setNumOfItems((long)tasks.size());
        return SUCCESS;
    }
}
