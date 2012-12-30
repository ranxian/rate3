package rate.util;

import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(UUIDGenerator.class);

    @Override
    public Serializable generate (SessionImplementor session, Object parent)
            throws HibernateException
    {
        String  r = UUID.randomUUID ().toString() ;
        logger.debug("UUIDGenerator: " + r);
        return r;
    }
}
