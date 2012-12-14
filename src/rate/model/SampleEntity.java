package rate.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@javax.persistence.Table(name = "sample", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class SampleEntity {
    private UUID uuid;

    @Type(type="UUIDType")
    @GenericGenerator(name="UUIDGenerator", strategy="rate.util.UUIDGenerator")
    @GeneratedValue(generator = "UUIDGenerator")
    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    private UUID classUuid;

    @javax.persistence.Column(name = "class_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(UUID classUuid) {
        this.classUuid = classUuid;
    }

    private Timestamp created;

    @javax.persistence.Column(name = "created", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private String file;

    @javax.persistence.Column(name = "file", nullable = false, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private UUID deviceType;

    @Type(type="rate.util.UUIDType")
    @javax.persistence.Column(name = "device_type", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(UUID deviceType) {
        this.deviceType = deviceType;
    }

    private String importTag;

    @javax.persistence.Column(name = "import_tag", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getImportTag() {
        return importTag;
    }

    public void setImportTag(String importTag) {
        this.importTag = importTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleEntity that = (SampleEntity) o;

        if (classUuid != null ? !classUuid.equals(that.classUuid) : that.classUuid != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (!(deviceType == that.deviceType)) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (importTag != null ? !importTag.equals(that.importTag) : that.importTag != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (classUuid != null ? classUuid.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (importTag != null ? importTag.hashCode() : 0);
        return result;
    }
}
