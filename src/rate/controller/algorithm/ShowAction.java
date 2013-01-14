package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;

import java.util.Collection;
import java.util.List;

import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class ShowAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(ShowAction.class);
    private final Session session = HibernateUtil.getSession();

    private AlgorithmEntity algorithm;

    public Collection<AlgorithmVersionEntity> getAlgorithmVersions() {
        return algorithmVersions;
    }

    private Collection<AlgorithmVersionEntity> algorithmVersions;

    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithm) {
        this.algorithm = algorithm;
    }

    public String execute() throws Exception {
        Query q = session.createQuery("from AlgorithmEntity where uuid=:uuid");
        q.setParameter("uuid", uuid);
        List<AlgorithmEntity> list = q.list();
        algorithm = list.get(0);
        algorithmVersions = session.createQuery("from AlgorithmVersionEntity where algorithm=:algorithm order by created desc")
                .setParameter("algorithm", algorithm)
                .list();
        return SUCCESS;
    }
}
