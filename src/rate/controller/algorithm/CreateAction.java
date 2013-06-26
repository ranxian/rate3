package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Time: 下午12:37
 */
public class CreateAction extends AlgorithmActionBase {
    private final Logger logger = Logger.getLogger(CreateAction.class);

    // overide the father's session
    private final Session session = HibernateUtil.getSession();

    public String execute() throws Exception {
        logger.trace("execute");
        if (!getIsUserSignedIn()) {
            logger.trace("user has not signed in");
            return "eLogin";
        }

        try {
            logger.trace("beginTransaction");
            session.beginTransaction();
            logger.trace("save algorithm" + algorithm.getUuid());

            algorithm.setUser(getCurrentUser());
            session.save(algorithm);

            logger.trace("commit");
            session.getTransaction().commit();
        }
        catch (HibernateException ex) {
            logger.trace(ex.getMessage());
            throw ex;
        }
        return SUCCESS;
    }
}
