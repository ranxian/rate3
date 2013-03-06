package rate.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.HibernateUtil;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-8
 * Time:    下午10:46
 */
@Table(name = "user", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class UserEntity {

    private String uuid;

    @Id
    @Type(type="UUIDType")
    @GenericGenerator(name="UUIDGenerator", strategy="rate.util.UUIDGenerator")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String name;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String password;

    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 32, precision = 0)
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        this.password = DigestUtils.md5Hex(password);
    }

    private Timestamp registered;

    @Column(name = "registered", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getRegistered() {
        return registered;
    }

    public void setRegistered(Timestamp registered) {
        this.registered = registered;
    }

    private String email;

    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String organization;

    @Column(name = "organization", nullable = true, insertable = true, updatable = true, length = 128, precision = 0)
    @Basic
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (organization != null ? !organization.equals(that.organization) : that.organization != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (registered != null ? !registered.equals(that.registered) : that.registered != null) return false;
        if (uuid !=that.uuid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registered != null ? registered.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        return result;
    }

    private Collection<UserAlgorithmEntity> userAlgorithms;

    @OneToMany(mappedBy = "user")
    public Collection<UserAlgorithmEntity> getUserAlgorithms() {
        return userAlgorithms;
    }

    public void setUserAlgorithms(Collection<UserAlgorithmEntity> userAlgorithms) {
        this.userAlgorithms = userAlgorithms;
    }

    public static UserEntity authenticate(String name, String password) {
        org.hibernate.Query q = HibernateUtil.getSession().createQuery("from UserEntity where name=:name");
        q.setParameter("name", name);
        List<UserEntity> list = q.list();
        UserEntity user = list.get(0);

        if (user == null) return null;
        if (user.getPassword() == DigestUtils.md5Hex(password)) return user;
        else return null;
    }
}
