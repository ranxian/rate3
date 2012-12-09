package rate.view.user;

import org.hibernate.Query;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:12
 */
public class ListAction {
    private List<UserEntity> users;

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from UserEntity");
        users  = q.list();
        return "success";
    }

    public List<UserEntity> getUsers() {
        return users;
    }
}
