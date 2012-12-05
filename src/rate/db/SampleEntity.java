package rate.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "sample", schema = "", catalog = "rate3")
@Entity
public class SampleEntity {
    private byte[] uuid;

    @javax.persistence.Column(name = "uuid")
    @Id
    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

    private byte[] classUuid;

    @javax.persistence.Column(name = "class_uuid")
    @Basic
    public byte[] getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(byte[] classUuid) {
        this.classUuid = classUuid;
    }

    private Timestamp created;

    @javax.persistence.Column(name = "created")
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private String file;

    @javax.persistence.Column(name = "file")
    @Basic
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private byte[] deviceType;

    @javax.persistence.Column(name = "device_type")
    @Basic
    public byte[] getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(byte[] deviceType) {
        this.deviceType = deviceType;
    }

    private String importTag;

    @javax.persistence.Column(name = "import_tag")
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

        if (!Arrays.equals(classUuid, that.classUuid)) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (!Arrays.equals(deviceType, that.deviceType)) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (importTag != null ? !importTag.equals(that.importTag) : that.importTag != null) return false;
        if (!Arrays.equals(uuid, that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? Arrays.hashCode(uuid) : 0;
        result = 31 * result + (classUuid != null ? Arrays.hashCode(classUuid) : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (deviceType != null ? Arrays.hashCode(deviceType) : 0);
        result = 31 * result + (importTag != null ? importTag.hashCode() : 0);
        return result;
    }
}
