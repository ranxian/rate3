package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.UserAlgorithmEntity;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Time: 下午12:37
 */
public class CreateAction extends AlgorithmActionBase {
<<<<<<< HEAD
    //private UserAlgorithmEntity userAlgorithm;
    private final Logger logger = Logger.getLogger(CreateAction.class);

    // overide the father's session
    private final Session session = HibernateUtil.getSession();
=======
    private static final Logger logger = Logger.getLogger(CreateAction.class.getName());
    private UserAlgorithmEntity userAlgorithm;
>>>>>>> 3be0506a1ae6ce7cde5d3fa631570c3c83eecbf8

    public String execute() throws Exception {
        logger.trace("execute");
        if (!getIsUserSignedIn()) {
            logger.trace("user has not signed in");
            return "eLogin";
        }

        try {
<<<<<<< HEAD
            logger.trace("beginTransaction");
=======
>>>>>>> 3be0506a1ae6ce7cde5d3fa631570c3c83eecbf8
            session.beginTransaction();
            logger.trace("save algorithm" + algorithm.getUuid());
            session.save(algorithm);
            logger.trace("new userAlgorithm");
            UserAlgorithmEntity userAlgorithm = new UserAlgorithmEntity();
            logger.trace("setAlgorithm " + algorithm.getUuid());
            userAlgorithm.setAlgorithm(algorithm);
            UserEntity user = getCurrentUser();
            logger.trace("setUser " + user.getUuid());
            userAlgorithm.setUser(user);
            logger.trace("save userAlgorithm " + userAlgorithm.getAlgorithm().getUuid() + " " + userAlgorithm.getUser().getUuid());
            session.save(userAlgorithm);
            logger.trace("commit");
            session.getTransaction().commit();
<<<<<<< HEAD
        }
        catch (HibernateException ex) {
            logger.trace(ex);
=======
            setAlgorithmAuthor(algorithm, getCurrentUser());

        }
        catch (HibernateException ex) {
            logger.trace(ex.getMessage());
>>>>>>> 3be0506a1ae6ce7cde5d3fa631570c3c83eecbf8
            throw ex;
        }
        return SUCCESS;
    }
}
