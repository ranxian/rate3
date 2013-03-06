package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class NewAction extends RateActionBase {

    private static final Logger logger = Logger.getLogger(NewAction.class);

    public String getAlgorithmUuid() {
        return algorithmUuid;
    }

    public void setAlgorithmUuid(String algorithmUuid) {
        this.algorithmUuid = algorithmUuid;
    }

    private AlgorithmVersionEntity algorithmVersion;

    private String algorithmUuid;

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    private AlgorithmEntity algorithm;

    public String execute() {
        algorithmVersion = new AlgorithmVersionEntity();
        Session session = HibernateUtil.getSession();
        algorithm = (AlgorithmEntity)session.createQuery("from AlgorithmEntity where uuid=:uuid")
                .setParameter("uuid", algorithmUuid)
                .list().get(0);

        algorithmVersion.setAlgorithm(algorithm);

        return SUCCESS;
    }
}
