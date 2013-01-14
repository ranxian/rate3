package rate.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@Table(name = "device_type", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class DeviceTypeEntity {
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

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;

    @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 11, precision = 0)
    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String provider;

    @Column(name = "provider", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    private String version;

    @Column(name = "version", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

        DeviceTypeEntity that = (DeviceTypeEntity) o;

        if (extra != null ? !Arrays.equals(extra, that.extra) : that.extra != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (provider != null ? !provider.equals(that.provider) : that.provider != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }

    private Collection<SampleEntity> samples;

    @OneToMany(mappedBy = "device")
    public Collection<SampleEntity> getSamples() {
        return samples;
    }

    public void setSamples(Collection<SampleEntity> samplesByUuid) {
        this.samples = samplesByUuid;
    }
}
