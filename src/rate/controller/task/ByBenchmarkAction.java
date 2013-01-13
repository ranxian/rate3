package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.util.Collection;


/**
 * Created by XianRan
 * Time: 下午12:41
 */
public class ByBenchmarkAction extends RateActionBase {

    private static final Logger logger = Logger.getLogger(ByBenchmarkAction.class);
    private final Session session = HibernateUtil.getSession();

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    private String benchmarkUuid;

    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    private BenchmarkEntity benchmark;

    public void setUuid(String benchmarkUuid) {
        this.benchmarkUuid = benchmarkUuid;
        this.benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", benchmarkUuid)
                .list().get(0);
    }

    private Collection<TaskEntity> tasks;

    public String execute() {
        tasks = session.createQuery("from TaskEntity where benchmarkByBenchmarkUuid=:benchmark order by created desc")
                .setParameter("benchmark", benchmark)
                .setFirstResult(getFirstResult()).setMaxResults(itemPerPage)
                .list();
        setNumOfItems((Long)session.createQuery("select count(*) from TaskEntity where benchmarkByBenchmarkUuid=:benchmark")
                .setParameter("benchmark", benchmark)
                .list().get(0));
        return SUCCESS;
    }
}
