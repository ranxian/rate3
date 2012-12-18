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
    private ViewEntity view;

    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    public String execute() throws Exception {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();

        session.update(view);

        session.getTransaction().commit();

        return SUCCESS;
    }
}
