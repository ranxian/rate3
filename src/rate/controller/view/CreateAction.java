package rate.controller.view;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class CreateAction extends RateActionBase {
    private ViewEntity view;

    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    public String execute() throws Exception {
        Session session = HibernateUtil.getSession();

        // Here should call generate from engine
        session.beginTransaction();
        session.save(view);
        session.getTransaction().commit();

        return SUCCESS;
    }
}
