package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.UserAlgorithmEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class AlgorithmVersionActionBase extends RateActionBase {

    protected final Session session = HibernateUtil.getSession();
    private final Logger logger = Logger.getLogger(AlgorithmVersionActionBase.class);

    public void setUuid(String uuid) {
        this.uuid = uuid;
        algorithmVersion = (AlgorithmVersionEntity)session
                .createQuery("from AlgorithmVersionEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
        algorithm = algorithmVersion.getAlgorithm();
    }

    private String uuid;

    public void setAlgorithmVersion(AlgorithmVersionEntity algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public AlgorithmVersionEntity getAlgorithmVersion() {
        return algorithmVersion;
    }

    protected AlgorithmVersionEntity algorithmVersion;

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithmUuid(String uuid) {
        this.algorithm = (AlgorithmEntity) session
                .createQuery("from AlgorithmEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
        algorithmVersion.setAlgorithm(algorithm);
    }

    protected AlgorithmEntity algorithm;

    protected boolean isAuthor() {
        UserAlgorithmEntity userAlgorithm = (UserAlgorithmEntity)session.createQuery("from UserAlgorithmEntity where algorithm=:algorithm")
                .setParameter("algorithm", algorithm)
                .list().get(0);
        logger.trace("algorithm " + algorithm.getUuid() + " user " + userAlgorithm.getUser().getName() + " currentUser " + getCurrentUser().getName());

        return userAlgorithm.getUser().equals(getCurrentUser());
    }
}
