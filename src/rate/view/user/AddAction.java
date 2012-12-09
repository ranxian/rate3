package rate.view.user;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-8
 * Time:    下午11:37
 */
public class AddAction {
    private UserEntity user;
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String execute() throws Exception {
        if (user==null) return "fail";
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        catch (HibernateException ex) {
            throw ex;
        }
        return "redirect";
    }
}
