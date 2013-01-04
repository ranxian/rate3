package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class IndexAction extends ActionSupport {

    private final Logger logger = Logger.getLogger(this.getClass());

    public String getAlgorithmUuid() {
        return algorithmUuid;
    }

    public void setAlgorithmUuid(String algorithmUuid) {
        this.algorithmUuid = algorithmUuid;
    }

    private String algorithmUuid;

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithm) {
        this.algorithm = algorithm;
    }

    public Collection<AlgorithmVersionEntity> getAlgorithmVersions() {
        logger.trace("I'm executed");
        return this.algorithm.getAlgorithmVersionsByUuid();
    }

    private AlgorithmEntity algorithm;

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from AlgorithmEntity where uuid=:uuid").setParameter("uuid", getAlgorithmUuid());
        algorithm = (AlgorithmEntity)q.list().get(0);
        return SUCCESS;
    }

}
