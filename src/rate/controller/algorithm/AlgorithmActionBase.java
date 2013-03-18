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
    public void setAlgorithmAuthor(AlgorithmEntity algorithm, UserEntity user) {
        session.getTransaction().begin();
        UserAlgorithmEntity userAlgorithm = new UserAlgorithmEntity();
        userAlgorithm.setAlgorithm(algorithm);
        userAlgorithm.setUser(user);
        session.save(userAlgorithm);
        session.getTransaction().commit();
    }
}
