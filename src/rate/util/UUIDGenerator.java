package rate.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午10:55
 */
public class UUIDGenerator implements IdentifierGenerator {
        @Override
        public Serializable generate (SessionImplementor session, Object parent)
                throws HibernateException
        {
            UUID r = UUID.randomUUID () ;
            return r;
        }
}
