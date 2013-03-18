package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.omg.PortableInterceptor.SUCCESSFUL;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.Date;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 12-12-18
 * Time: 下午9:43
 */
public class UpdateAction extends AlgorithmActionBase {
    public String execute() {
        AlgorithmEntity updated = (AlgorithmEntity)session.createQuery("from AlgorithmEntity where uuid=:uuid")
                .setParameter("uuid", algorithm.getUuid())
                .list().get(0);

        updated.setName(algorithm.getName());
        updated.setDescription(algorithm.getDescription());
        updated.setUpdated(HibernateUtil.getCurrentTimestamp());

        session.beginTransaction();
        session.update(updated);
        session.getTransaction().commit();

        return SUCCESS;
    }
}
