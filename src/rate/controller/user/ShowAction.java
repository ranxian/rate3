package rate.controller.user;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.model.UserEntity;
import rate.util.HibernateUtil;
import java.util.List;

import java.util.UUID;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class ShowAction extends ActionSupport {
    private UserEntity user;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from UserEntity where uuid=:uuid");
        q.setParameter("uuid", UUID.fromString(uuid));
        List<UserEntity> list = q.list();
        user = list.get(0);
        return SUCCESS;
    }
}
