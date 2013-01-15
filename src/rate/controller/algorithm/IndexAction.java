package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:38
 */
public class IndexAction extends RateActionBase {

    public List<AlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<AlgorithmEntity> algorithms) {
        this.algorithms = algorithms;
    }

    private List<AlgorithmEntity> algorithms;

    public String execute() throws Exception {
        algorithms = session.createQuery("from AlgorithmEntity order by updated desc")
                .setFirstResult(getFirstResult()).setMaxResults(itemPerPage)
                .list();
         setNumOfItems((Long)session.createQuery("select count(*) from AlgorithmEntity").list().get(0));
        return SUCCESS;
    }
}
