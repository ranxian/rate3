package rate.controller.user;

import org.apache.log4j.Logger;
import rate.controller.RateActionBase;
import rate.model.UserAlgorithmEntity;
import rate.model.UserEntity;

import java.util.Collection;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class AlgorithmsAction extends RateActionBase {
    private static final Logger logger = Logger.getLogger(AlgorithmsAction.class);

    private UserEntity user;

    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String execute() throws Exception {
        user = (UserEntity)session.createQuery("from UserEntity where uuid=:uuid").setParameter("uuid", this.uuid)
                .list().get(0);

        if (user == null) return "eNoUser";
        if (!user.getUuid().equals(getCurrentUser().getUuid())) {
            if (!getCurrentUser().getPrivilege().equals("vip")) {
                return "eNotVip";
            }
        }

        algorithms = session.createQuery("from UserAlgorithmEntity where user=:user").setParameter("user", user).list();
        setNumOfItems(Long.parseLong(String.valueOf(algorithms.size())));

        return SUCCESS;
    }

    public UserEntity getUser() {
        return user;
    }

    public Collection<UserAlgorithmEntity> getAlgorithms() {
        return algorithms;
    }

    private Collection<UserAlgorithmEntity> algorithms;
}
