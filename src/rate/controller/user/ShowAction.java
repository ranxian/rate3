package rate.controller.user;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(ShowAction.class);

    private String uuid;
    private UserEntity user;

    public String execute() throws Exception {
        logger.debug(uuid.toString());
        Query q = HibernateUtil.getSession().createQuery("from UserEntity where uuid =:uuid");
        q.setParameter("uuid", uuid);
        List<UserEntity> list = q.list();
        user = list.get(0);
        return "success";
    }

    public UserEntity getUser() {
        return user;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
