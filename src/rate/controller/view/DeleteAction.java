package rate.controller.view;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午12:40
 */
public class DeleteAction extends ActionSupport {
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String execute() throws Exception {
        Session session = HibernateUtil.getSession();
        Query q = session.createQuery("from ViewEntity where uuid=:uuid");
        q.setParameter("uuid", uuid);
        ViewEntity view = (ViewEntity)q.list().get(0);

        session.beginTransaction();
        session.delete(view);
        session.getTransaction().commit();

        return SUCCESS;
    }
}
