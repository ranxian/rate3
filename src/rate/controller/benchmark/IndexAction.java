package rate.controller.benchmark;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
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
public class IndexAction extends ActionSupport {
    private final Session session = HibernateUtil.getSession();

    private String viewUuid;

    public ViewEntity getView() {
        return view;
    }

    private ViewEntity view;

    public Collection<BenchmarkEntity> getBenchmarks() {
        return benchmarks;
    }

    private Collection<BenchmarkEntity> benchmarks;

    public void setViewUuid(String viewUuid) {
        this.viewUuid = viewUuid;
        this.view = (ViewEntity)session.createQuery("from ViewEntity where uuid=:uuid")
                .setParameter("uuid", viewUuid)
                .list().get(0);
        this.benchmarks = this.view.getBenchmarksByUuid();
    }

    public String execute() {
        return SUCCESS;
    }
}
