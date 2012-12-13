package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.model.AlgorithmEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:38
 */
public class IndexAction extends ActionSupport   {
    public List<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<AlgorithmEntity> algorithms) {
        this.algorithms = algorithms;
    }

    private List<AlgorithmEntity> algorithms;

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from AlgorithmEntity");
        algorithms = q.list();
        return SUCCESS;
    }
}
