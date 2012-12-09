package rate.view.user;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import rate.util.HibernateUtil;
import rate.model.*;

import java.util.List;
import java.util.UUID;

/**
 * User: Yu Yuankai
 * Date: 12-12-5
 * Time: 下午9:37
 */
public class DetailAction {
    private static final Logger logger = Logger.getLogger(DetailAction.class);

    private UUID uuid;
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
        return uuid.toString();
    }
    public void setUuid(String s) {
        uuid = UUID.fromString(s);
    }

}
