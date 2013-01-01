package rate.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@Table(name = "person", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class PersonEntity {
    private String uuid;

    @Type(type="UUIDType")
    @GenericGenerator(name="UUIDGenerator", strategy="rate.util.UUIDGenerator")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String name;

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String gender;

    @Column(name = "gender", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private Date birth;

    @Column(name = "birth", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    private byte[] extra;

    @Column(name = "extra", nullable = true, insertable = true, updatable = true, length = 65535, precision = 0)
    @Basic
    public byte[] getExtra() {
        return extra;
    }

    public void setExtra(byte[] extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEntity that = (PersonEntity) o;

        if (birth != null ? !birth.equals(that.birth) : that.birth != null) return false;
        if (extra != null ? !Arrays.equals(extra, that.extra) : that.extra != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }

    private Collection<ClazzEntity> clazzsByUuid;

    @OneToMany(mappedBy = "personByPersonUuid")
    public Collection<ClazzEntity> getClazzsByUuid() {
        return clazzsByUuid;
    }

    public void setClazzsByUuid(Collection<ClazzEntity> clazzsByUuid) {
        this.clazzsByUuid = clazzsByUuid;
    }
}
