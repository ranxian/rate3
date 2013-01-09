package rate.controller.view;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午9:25
 */
public class UpdateAction extends ActionSupport {
    private final Session session = HibernateUtil.getSession();

    private ViewEntity view;

    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    public String execute() throws Exception {

        ViewEntity updated = (ViewEntity)session.createQuery("from ViewEntity where uuid=:uuid")
                .setParameter("uuid", view.getUuid())
                .list().get(0);

        updated.setName(view.getName());

        session.beginTransaction();
        session.update(updated);
        session.getTransaction().commit();

        return SUCCESS;
    }
}
