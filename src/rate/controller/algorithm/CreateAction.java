package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.UserAlgorithmEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Time: 下午12:37
 */
public class CreateAction extends AlgorithmActionBase {
    private UserAlgorithmEntity userAlgorithm;

    public String execute() throws Exception {
        if (!getIsUserSignedIn()) return "eLogin";

        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(algorithm);
            session.getTransaction().commit();
            setAlgorithmAuthor(algorithm, getCurrentUser());
        }
        catch (HibernateException ex) {
            throw ex;
        }
        return SUCCESS;
    }
}
