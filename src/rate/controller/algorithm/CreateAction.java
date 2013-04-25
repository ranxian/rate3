package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(CreateAction.class.getName());
    private UserAlgorithmEntity userAlgorithm;

    public String execute() throws Exception {
        if (!getIsUserSignedIn()) return "eLogin";

        try {
            session.beginTransaction();
            session.save(algorithm);
            session.getTransaction().commit();
            setAlgorithmAuthor(algorithm, getCurrentUser());

        }
        catch (HibernateException ex) {
            logger.trace(ex.getMessage());
            throw ex;
        }
        return SUCCESS;
    }
}
