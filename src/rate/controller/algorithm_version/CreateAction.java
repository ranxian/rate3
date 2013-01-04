package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;


/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class CreateAction extends ActionSupport {
    private final static Logger logger = Logger.getLogger(CreateAction.class);

    public AlgorithmVersionEntity getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(AlgorithmVersionEntity algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    private AlgorithmVersionEntity algorithmVersion;

    private AlgorithmEntity algorithm;

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithmUuid(String uuid) {
        this.algorithm = (AlgorithmEntity) HibernateUtil.getSession()
                .createQuery("from AlgorithmEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
    }

    public String execute() {
        this.algorithmVersion.setAlgorithmByAlgorithmUuid(this.algorithm);
        return SUCCESS;
    }
}
