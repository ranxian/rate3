package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.Collection;
import java.util.List;

import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class ShowAction extends AlgorithmActionBase {
    public Collection<AlgorithmVersionEntity> getAlgorithmVersions() {
        return algorithmVersions;
    }

    private Collection<AlgorithmVersionEntity> algorithmVersions;

    public String execute() throws Exception {
        algorithmVersions = session.createQuery("from AlgorithmVersionEntity where algorithm=:algorithm order by created desc")
                .setParameter("algorithm", algorithm)
                .list();
        return SUCCESS;
    }
}
