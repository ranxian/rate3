package rate.controller.benchmark;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 下午12:44
 * To change this template use File | Settings | File Templates.
 */
public class IndexAction extends RateActionBase {
    private final Session session = HibernateUtil.getSession();

    public Collection<BenchmarkEntity> getBenchmarks() {
        return benchmarks;
    }

    private Collection<BenchmarkEntity> benchmarks;

    public String execute() {
        benchmarks = session.createQuery("from BenchmarkEntity order by created desc").list();
        return SUCCESS;
    }
}
