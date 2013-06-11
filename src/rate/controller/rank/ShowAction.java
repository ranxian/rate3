package rate.controller.rank;

import com.opensymphony.xwork2.ActionSupport;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;

import java.util.Collection;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-6-11
 * Time: 下午4:18
 */
public class ShowAction extends RateActionBase {
    private BenchmarkEntity benchmark;

    public void setUuid(String uuid) {
        benchmark = (BenchmarkEntity) session.createQuery("from BenchmarkEntity  where uuid=:uuid").setParameter("uuid", uuid)
                .list().get(0);
    }

    private Collection<TaskEntity> tasks;

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String execute() throws Exception {
        // actually order by score, but no this field in database yet.
        tasks = session.createQuery("from TaskEntity where benchmark=:benchmark order by created").setParameter("benchmark", benchmark)
                .setMaxResults(100).list();
        return SUCCESS;
    }
}
