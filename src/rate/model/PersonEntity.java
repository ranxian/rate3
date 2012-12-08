package rate.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Arrays;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-8
 * Time:    下午10:46
 */
@javax.persistence.Table(name = "person", schema = "", catalog = "rate3")
@Entity
public class PersonEntity {
    private byte[] uuid;

    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

    private String name;

    @javax.persistence.Column(name = "name", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String gender;

    @javax.persistence.Column(name = "gender", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private Date birth;

    @javax.persistence.Column(name = "birth", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    private byte[] extra;

    @javax.persistence.Column(name = "extra", nullable = true, insertable = true, updatable = true, length = 65535, precision = 0)
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
        if (!Arrays.equals(extra, that.extra)) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (!Arrays.equals(uuid, that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? Arrays.hashCode(uuid) : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (extra != null ? Arrays.hashCode(extra) : 0);
        return result;
    }
}
