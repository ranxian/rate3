package rate.controller.user;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Time: 下午9:25
 */
public class UpdateAction extends RateActionBase {
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String execute() throws Exception {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();

        session.update(user);

        session.getTransaction().commit();

        return SUCCESS;
    }
}
