package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
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
public class IndexAction extends RateActionBase {

    private final static Logger logger = Logger.getLogger(IndexAction.class);
    private final Session session = HibernateUtil.getSession();

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
        return session.createQuery("from AlgorithmVersionEntity where algorithm=:algorithm order by created desc")
                .setParameter("algorithm", algorithm)
                .list();
        //return this.algorithm.getAlgorithmVersionsByUuid();
    }

    private AlgorithmEntity algorithm;

    public String execute() throws Exception {
                Query q = session.createQuery("from AlgorithmEntity where uuid=:uuid order by created desc").setParameter("uuid", getAlgorithmUuid());
        algorithm = (AlgorithmEntity)q.list().get(0);
        return SUCCESS;
    }

}
