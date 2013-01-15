package rate.util;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午12:51
 */

import java.io.Serializable ;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.sql.Types;
import java.util.UUID;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException ;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType ;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;


public class UUIDType implements UserType
{
    private static final Logger logger = Logger.getLogger(UUIDType.class);

    private static final String CAST_EXCEPTION_TEXT = " cannot be cast to a java.util.UUID." ;

    private static byte[] UUID2ByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] byteArray = new byte[16];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        LongBuffer longBuffer = byteBuffer.asLongBuffer();
        longBuffer.put(0, msb);
        longBuffer.put(1, lsb);
        return byteArray;
    }

    private static UUID byteArray2UUID(byte[] byteArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        LongBuffer longBuffer = byteBuffer.asLongBuffer();
        return new UUID(longBuffer.get(0), longBuffer.get(1));
    }

    public Object assemble (Serializable cached, Object owner) throws HibernateException
    {
        if (!byte[].class.isAssignableFrom (cached.getClass())) {
            return null ;
        }
        return byteArray2UUID((byte[]) cached).toString() ;
    }

    public Object deepCopy (Object value) throws HibernateException {
        if (!String.class.isAssignableFrom (value.getClass ())) {
            throw new HibernateException (value.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        return value;
    }

    public Serializable disassemble (Object value) throws HibernateException {
        return UUID2ByteArray(UUID.fromString(StringUtils.lowerCase((String)value)));
    }

    public boolean equals (Object x, Object y) throws HibernateException {
        if (x == y)
            return true ;
        if (!String.class.isAssignableFrom (x.getClass ())) {
            throw new HibernateException (x.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        else if (!String.class.isAssignableFrom (y.getClass ())) {
            throw new HibernateException (y.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }

        String a = (String) x ;
        String b = (String) y ;

        return a.equals (b) ;
    }

    public int hashCode (Object x) throws HibernateException {
        if (!String.class.isAssignableFrom (x.getClass ())) {
            throw new HibernateException (x.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        return x.hashCode () ;
    }

    public boolean isMutable () {
        return false ;
    }

    public Object nullSafeGet (ResultSet rs, String[] names, SessionImplementor sessionImplementor, Object owner) throws HibernateException,
            SQLException
    {
        byte[] value = rs.getBytes(names[0]);
        return (value == null) ? null : byteArray2UUID(value).toString();
    }

    public void nullSafeSet (PreparedStatement st, Object value, int index, SessionImplementor sessionImplementor)
            throws HibernateException, SQLException
    {
        if (value == null) {
            st.setNull (index, Types.VARBINARY);
            return ;
        }
        if (!String.class.isAssignableFrom (value.getClass ())) {
            throw new HibernateException (value.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }

        st.setBytes(index, UUID2ByteArray(UUID.fromString(StringUtils.lowerCase((String) value))));
    }

    public Object replace (Object original, Object target, Object owner) throws HibernateException {
        if (!String.class.isAssignableFrom (original.getClass ())) {
            throw new HibernateException (original.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        return original;
    }

    @SuppressWarnings("unchecked")
    public Class returnedClass () {
        return String.class ;
    }

    public int[] sqlTypes () {
        return new int[] { Types.VARBINARY } ;
    }
}
