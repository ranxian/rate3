package rate.test;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.UserAlgorithmEntity;
import rate.model.UserEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-19
 * Time: 下午12:41
 */
public class Executor {
    private static final Session session = HibernateUtil.getSession();
    public static void main(String[] args) throws Exception {
        session.getTransaction().begin();
        session.createQuery("delete from TaskEntity");
        session.getTransaction().commit();
    }
}
