package rate.test;

import org.hibernate.Session;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午1:31
 */
public class BaseTest {
    protected static final Session session = HibernateUtil.getSession();
}
