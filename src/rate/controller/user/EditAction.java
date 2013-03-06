package rate.controller.user;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.controller.RateActionBase;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:40
 */
public class EditAction extends RateActionBase {
    private String uuid;
    private UserEntity user;

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
        q.setParameter("uuid", uuid);
        List<UserEntity> list = q.list();
        user = list.get(0);
        return SUCCESS;
    }
}
