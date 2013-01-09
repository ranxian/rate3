package rate.controller.benchmark;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 下午12:37
 * To change this template use File | Settings | File Templates.
 */
public class BenchmarkActionBase extends ActionSupport {
    private static final Logger logger = Logger.getLogger(BenchmarkActionBase.class);
    protected final Session session = HibernateUtil.getSession();

    private String uuid;
    public void setUuid(String uuid) {
        this.uuid = uuid;
        benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
    }

    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    public Collection<TaskEntity> getTasks() {
        return session.createQuery("from TaskEntity where benchmarkByBenchmarkUuid=:benchmark order by created desc")
                .setParameter("benchmark", benchmark)
                .list();
    }

    protected BenchmarkEntity benchmark;
}
