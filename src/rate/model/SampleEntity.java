package rate.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@Table(name = "sample", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class SampleEntity {
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

//    private String classUuid;
//
//    @Type(type="UUIDType")
//    @Column(name = "class_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
//    @Basic
//    public String getClassUuid() {
//        return classUuid;
//    }
//
//    public void setClassUuid(String classUuid) {
//        this.classUuid = classUuid;
//    }

    private Timestamp created;

    @Column(name = "created", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private String file;

    @Column(name = "file", nullable = false, insertable = true, updatable = true, length = 256, precision = 0)
    @Basic
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

//    private UUID deviceType;
//
//    @Type(type="rate.util.UUIDType")
//    @Column(name = "device_type", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
//    @Basic
//    public UUID getDeviceType() {
//        return deviceType;
//    }
//
//    public void setDeviceType(UUID deviceType) {
//        this.deviceType = deviceType;
//    }

    private String importTag;

    @Column(name = "import_tag", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
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

        if (getClazzByClassUuid() != null ? !getClazzByClassUuid().equals(that.getClazzByClassUuid()) : that.getClazzByClassUuid() != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (!(getDeviceTypeByDeviceType() == that.getDeviceTypeByDeviceType())) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (importTag != null ? !importTag.equals(that.importTag) : that.importTag != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (getClazzByClassUuid() != null ? getClazzByClassUuid().hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (getDeviceTypeByDeviceType() != null ? getDeviceTypeByDeviceType().hashCode() : 0);
        result = 31 * result + (importTag != null ? importTag.hashCode() : 0);
        return result;
    }

    private DeviceTypeEntity deviceTypeByDeviceType;

    @ManyToOne
    @JoinColumn(name = "device_type", referencedColumnName = "uuid")
    public DeviceTypeEntity getDeviceTypeByDeviceType() {
        return deviceTypeByDeviceType;
    }

    public void setDeviceTypeByDeviceType(DeviceTypeEntity deviceTypeByDeviceType) {
        this.deviceTypeByDeviceType = deviceTypeByDeviceType;
    }

    private ClazzEntity clazzByClassUuid;

    @ManyToOne
    @JoinColumn(name = "class_uuid", referencedColumnName = "uuid")
    public ClazzEntity getClazzByClassUuid() {
        return clazzByClassUuid;
    }

    public void setClazzByClassUuid(ClazzEntity clazzByClassUuid) {
        this.clazzByClassUuid = clazzByClassUuid;
    }

    private Collection<ViewSampleEntity> viewSamplesByUuid;

    @OneToMany(mappedBy = "sampleBySampleUuid")
    public Collection<ViewSampleEntity> getViewSamplesByUuid() {
        return viewSamplesByUuid;
    }

    public void setViewSamplesByUuid(Collection<ViewSampleEntity> viewSamplesByUuid) {
        this.viewSamplesByUuid = viewSamplesByUuid;
    }
}
