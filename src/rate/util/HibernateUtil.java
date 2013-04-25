package rate.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.service.ServiceRegistryBuilder;

import java.sql.Timestamp;

/**
 * User: Yu Yuankai
 * Date: 12-12-8
 * Time: 下午9:09
 */
public class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();
    public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration();
            conf.configure("hibernate.cfg.xml");
            ServiceRegistryBuilder srb = new ServiceRegistryBuilder().applySettings(conf.getProperties());
            return conf.buildSessionFactory(srb.buildServiceRegistry());
            // what the hell is java
        }
        catch (HibernateException ex) {
            // logger.error(ex);
            throw ex;
        }
    }

    public  static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        Session s = session.get();
        if(s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = session.get();
        if(s != null) {
            s.close();
        }
        session.set(null);
    }

    public static Timestamp getCurrentTimestamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}
