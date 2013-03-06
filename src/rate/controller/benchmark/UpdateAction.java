package rate.controller.benchmark;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 下午6:18
 * To change this template use File | Settings | File Templates.
 */
public class UpdateAction extends RateActionBase {

    private static final Logger logger = Logger.getLogger(UpdateAction.class);
    private final Session session = HibernateUtil.getSession();

    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(BenchmarkEntity benchmark) {
        this.benchmark = benchmark;
    }

    private BenchmarkEntity benchmark;

    public String execute() {
        BenchmarkEntity updated = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", benchmark.getUuid())
                .list().get(0);
        updated.setName(benchmark.getName());
        updated.setDescription(benchmark.getDescription());

        session.beginTransaction();
        session.update(updated);
        session.getTransaction().commit();
        return SUCCESS;
    }
}
