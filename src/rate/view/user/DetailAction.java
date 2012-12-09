package rate.view.user;
import org.hibernate.Query;
import org.hibernate.type.UUIDBinaryType;
import rate.util.HibernateUtil;
import rate.model.*;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class DetailAction {
    private UUID uuid;
    private UserEntity user;

    public String execute() throws Exception {
        System.err.println("execute called");

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
