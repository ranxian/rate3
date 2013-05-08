package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.UserAlgorithmEntity;
import rate.util.HibernateUtil;

import java.util.ArrayList;
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

    private List<AlgorithmEntity> algorithms = new ArrayList<AlgorithmEntity>() ;

    public String execute() throws Exception {
        algorithms = session.createQuery("from AlgorithmEntity order by updated desc")
                .setFirstResult(getFirstResult()).setMaxResults(itemPerPage)
                .list();
        long count = (Long)(session.createQuery("select count(*) from AlgorithmEntity ").list().get(0));
//        if (getIsUserSignedIn() && getCurrentUser().isVip()) {
//            algorithms = session.createQuery("from AlgorithmEntity order by updated desc")
//                    .setFirstResult(getFirstResult()).setMaxResults(itemPerPage)
//                    .list();
//        } else {
//            List<UserAlgorithmEntity> userAlgorithmEntities = session.createQuery("from UserAlgorithmEntity where user=:user")
//                    .setParameter("user", getCurrentUser()).list();
//            for (UserAlgorithmEntity entity : userAlgorithmEntities) {
//                if (entity.getUser().equals(getCurrentUser()))
//                    algorithms.add(entity.getAlgorithm());
//            }
//        }
        setNumOfItems(count);
        return SUCCESS;
    }
}
