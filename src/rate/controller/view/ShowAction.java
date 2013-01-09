package rate.controller.view;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
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
public class ShowAction extends ActionSupport {
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

    public String execute() throws Exception {

        tasks = session.createQuery("from TaskEntity where benchmarkByBenchmarkUuid.viewByViewUuid=:view")
                .setParameter("view", this.view).list();

        return SUCCESS;
    }
}
