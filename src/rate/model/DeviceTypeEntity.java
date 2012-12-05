package rate.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "device_type", schema = "", catalog = "rate3")
@Entity
public class DeviceTypeEntity {
    private byte[] uuid;

    @javax.persistence.Column(name = "uuid")
    @Id
    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

    private String name;

    @javax.persistence.Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;

    @javax.persistence.Column(name = "type")
    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String provider;

    @javax.persistence.Column(name = "provider")
    @Basic
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    private String version;

    @javax.persistence.Column(name = "version")
    @Basic
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private byte[] extra;

    @javax.persistence.Column(name = "extra")
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

        if (!Arrays.equals(extra, that.extra)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (provider != null ? !provider.equals(that.provider) : that.provider != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!Arrays.equals(uuid, that.uuid)) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? Arrays.hashCode(uuid) : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (extra != null ? Arrays.hashCode(extra) : 0);
        return result;
    }
}
