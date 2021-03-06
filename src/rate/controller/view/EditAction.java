package rate.controller.view;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;
import java.util.List;
import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午12:40
 */
public class EditAction extends RateActionBase {
    private String uuid;
    private ViewEntity view;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from ViewEntity where uuid=:uuid");
        q.setParameter("uuid", uuid);
        List<ViewEntity> list = q.list();
        view = list.get(0);
        return SUCCESS;
    }
}
