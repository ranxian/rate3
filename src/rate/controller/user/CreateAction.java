package rate.controller.user;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.Map;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class CreateAction extends RateActionBase {
    private UserEntity user;
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        user.setPrivilege("plain");
        this.user = user;
    }

    public String execute() throws Exception {
        try {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            ActionContext.getContext().getSession().put("user-uuid", user.getUuid());
        }
        catch (HibernateException ex) {
            return "input";
        }
        return SUCCESS;
    }
}
