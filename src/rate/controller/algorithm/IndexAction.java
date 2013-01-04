package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:38
 */
public class IndexAction extends ActionSupport   {

    private final Session session = HibernateUtil.getSession();

    public List<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<AlgorithmEntity> algorithms) {
        this.algorithms = algorithms;
    }

    private List<AlgorithmEntity> algorithms;

    public String execute() throws Exception {
        Query q = session.createQuery("from AlgorithmEntity order by updated desc");
        algorithms = q.list();
        return SUCCESS;
    }
}
