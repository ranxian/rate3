package rate.controller.view;

import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.Collection;

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

    public Collection<BenchmarkEntity> getBenchmarks() {
        return this.view.getBenchmarks();
    }

    public String execute() throws Exception {

        tasks = session.createQuery("from TaskEntity where benchmark.view=:view order by created desc")
                .setParameter("view", this.view)
                .setMaxResults(itemPerPage)
                .list();

        return SUCCESS;
    }
}
