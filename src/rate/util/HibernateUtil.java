package rate.util;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * User: Yu Yuankai
 * Date: 12-12-8
 * Time: 下午9:09
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration();
            conf.configure("hibernate.cfg.xml");
            ServiceRegistryBuilder srb = new ServiceRegistryBuilder().applySettings(conf.getProperties());
            return conf.buildSessionFactory(srb.buildServiceRegistry());
            // what the hell is java
        }
        catch (HibernateException ex) {
            System.err.println("Hibernate SessionFactory creation failed." + ex);
            throw ex;
        }
    }

    public  static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
