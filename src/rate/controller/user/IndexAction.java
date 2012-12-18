package rate.controller.user;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:38
 */
public class    IndexAction extends ActionSupport   {
    private List<UserEntity> users;

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from UserEntity");
        users  = q.list();
        return SUCCESS;
    }

    public List<UserEntity> getUsers() {
        return users;
    }
}
