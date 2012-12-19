package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.model.AlgorithmEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:40
 */
public class EditAction extends ActionSupport {
    private String uuid;
    private AlgorithmEntity algorithm;

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
        Query q = HibernateUtil.getSession().createQuery("from AlgorithmEntity where uuid=:uuid");
        q.setParameter("uuid", uuid);
        List<AlgorithmEntity> list = q.list();
        algorithm = list.get(0);
        return SUCCESS;
    }
}
