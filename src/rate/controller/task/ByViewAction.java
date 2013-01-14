package rate.controller.task;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.Collection;


/**
 * Created by XianRan
 * Time: 下午12:41
 */
public class ByViewAction extends RateActionBase {

    private static final Logger logger = Logger.getLogger(ByViewAction.class);
    private final Session session = HibernateUtil.getSession();

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    private String viewUuid;


    public ViewEntity getView() {
        return view;
    }

    private ViewEntity view;

    public void setUuid(String uuid) {
        this.viewUuid = uuid;
        this.view = (ViewEntity)session.createQuery("from ViewEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
    }

    private Collection<TaskEntity> tasks;

    public String execute() {
        tasks = session.createQuery("from TaskEntity where benchmark.view=:view order by created desc")
                .setParameter("view", view)
                .setFirstResult(getFirstResult()).setMaxResults(itemPerPage)
                .list();
        setNumOfItems((Long)session.createQuery("select count(*) from TaskEntity where benchmark.view=:view")
                .setParameter("view", view)
                .list().get(0));
        return SUCCESS;
    }
}
