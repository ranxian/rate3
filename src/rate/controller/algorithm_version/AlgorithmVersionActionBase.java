package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class AlgorithmVersionActionBase extends ActionSupport {

    protected final Session session = HibernateUtil.getSession();

    public void setUuid(String uuid) {
        this.uuid = uuid;
        algorithmVersion = (AlgorithmVersionEntity)session
                .createQuery("from AlgorithmVersionEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
        algorithm = algorithmVersion.getAlgorithmByAlgorithmUuid();
    }

    private String uuid;

    public AlgorithmVersionEntity getAlgorithmVersion() {
        return algorithmVersion;
    }

    protected AlgorithmVersionEntity algorithmVersion;

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    protected AlgorithmEntity algorithm;
}
