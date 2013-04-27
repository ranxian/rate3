package rate.controller.algorithm;

import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.UserAlgorithmEntity;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-18
 * Time: 下午2:35
 */
public class AlgorithmActionBase extends RateActionBase {
    /*public void setAlgorithmAuthor(AlgorithmEntity algorithm, UserEntity user) {
        session.getTransaction().begin();
        UserAlgorithmEntity userAlgorithm = new UserAlgorithmEntity();
        userAlgorithm.setAlgorithm(algorithm);
        userAlgorithm.setUser(user);
        session.save(userAlgorithm);
        session.getTransaction().commit();
    }*/

    protected String uuid;
    protected AlgorithmEntity algorithm;

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithm) {
        this.algorithm = algorithm;
    }

    public void setUuid(String uuid) {
        this.algorithm = (AlgorithmEntity)session.createQuery("from AlgorithmEntity where uuid=:uuid").setParameter("uuid", uuid)
                .list().get(0);
    }

    public String getUuid() {
        return uuid;
    }

    public UserEntity getAuthor() {
        UserAlgorithmEntity userAlgorithm = (UserAlgorithmEntity)session.createQuery("from UserAlgorithmEntity where algorithm=:algorithm").setParameter("algorithm", algorithm)
                .list().get(0);
        return userAlgorithm.getUser();
    }

    public boolean isAuthor() {
        UserEntity user = getAuthor();

        return user.equals(getCurrentUser());
    }

    public boolean getIsAuthor() {
        return isAuthor();
    }
}
