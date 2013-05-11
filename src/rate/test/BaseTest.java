package rate.test;

import org.hibernate.Session;
import rate.controller.algorithm_version.AlgorithmVersionActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午1:31
 */
public class BaseTest {
    protected static Session session;

    public static Object getExample(String entityName) {
        String className = entityName+"Entity";
        return session.createQuery("from "+className).list().get(0);
    }
}
