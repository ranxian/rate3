package rate.controller.view;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.Collection;
import java.util.List;

import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class ShowAction extends RateActionBase {
    private ViewEntity view;
    private String uuid;

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    private Collection<TaskEntity> tasks;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        this.view = (ViewEntity)session.createQuery("from ViewEntity where uuid=:uuid")
                .setParameter("uuid", uuid).list().get(0);
    }

    public ViewEntity getView() {
        return view;
    }

    private final Session session = HibernateUtil.getSession();

    public Collection<BenchmarkEntity> getBenchmarks() {
        return this.view.getBenchmarksByUuid();
    }

    public String execute() throws Exception {

        tasks = session.createQuery("from TaskEntity where benchmark.view=:view order by created desc")
                .setParameter("view", this.view)
                .setMaxResults(itemPerPage)
                .list();

        return SUCCESS;
    }
}
