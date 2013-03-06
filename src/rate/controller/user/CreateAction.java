package rate.controller.user;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.Map;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class CreateAction extends ActionSupport {
    private UserEntity user;
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String execute() throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            ActionContext.getContext().getSession().put("user-uuid", user.getUuid());
        }
        catch (HibernateException ex) {
            throw ex;
        }
        return SUCCESS;
    }
}
