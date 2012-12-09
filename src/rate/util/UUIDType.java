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

import org.hibernate.HibernateException ;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType ;


public class UUIDType implements UserType
{

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
        return UUID.nameUUIDFromBytes(byteArray);
    }

    public Object assemble (Serializable cached, Object owner) throws HibernateException
    {
        if (!byte[].class.isAssignableFrom (cached.getClass())) {
            return null ;
        }
        return byteArray2UUID((byte[]) cached) ;
    }

    public Object deepCopy (Object value) throws HibernateException {
        if (!UUID.class.isAssignableFrom (value.getClass ())) {
            throw new HibernateException (value.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        return value;
    }

    public Serializable disassemble (Object value) throws HibernateException {
        return UUID2ByteArray((UUID) value);
    }

    public boolean equals (Object x, Object y) throws HibernateException {
        if (x == y)
            return true ;
        if (!UUID.class.isAssignableFrom (x.getClass ())) {
            throw new HibernateException (x.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        else if (!UUID.class.isAssignableFrom (y.getClass ())) {
            throw new HibernateException (y.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }

        UUID a = (UUID) x ;
        UUID b = (UUID) y ;

        return a.equals (b) ;
    }

    public int hashCode (Object x) throws HibernateException {
        if (!UUID.class.isAssignableFrom (x.getClass ())) {
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
        return (value == null) ? null : byteArray2UUID(value);
    }

    public void nullSafeSet (PreparedStatement st, Object value, int index, SessionImplementor sessionImplementor)
            throws HibernateException, SQLException
    {
        if (value == null) {
            st.setNull (index, Types.VARBINARY);
            return ;
        }
        if (!UUID.class.isAssignableFrom (value.getClass ())) {
            throw new HibernateException (value.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }

        st.setBytes(index, UUID2ByteArray((UUID) value)); ;
    }

    public Object replace (Object original, Object target, Object owner) throws HibernateException {
        if (!UUID.class.isAssignableFrom (original.getClass ())) {
            throw new HibernateException (original.getClass ().toString () + CAST_EXCEPTION_TEXT) ;
        }
        return original;
    }

    @SuppressWarnings("unchecked")
    public Class returnedClass () {
        return UUID.class ;
    }

    public int[] sqlTypes () {
        return new int[] { Types.VARBINARY } ;
    }
}
