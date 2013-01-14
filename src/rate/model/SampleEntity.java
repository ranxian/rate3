package rate.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

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

        if (getClazz() != null ? !getClazz().equals(that.getClazz()) : that.getClazz() != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (!(getDevice() == that.getDevice())) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (importTag != null ? !importTag.equals(that.importTag) : that.importTag != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (getClazz() != null ? getClazz().hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (getDevice() != null ? getDevice().hashCode() : 0);
        result = 31 * result + (importTag != null ? importTag.hashCode() : 0);
        return result;
    }

    private DeviceTypeEntity device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type", referencedColumnName = "uuid")
    public DeviceTypeEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceTypeEntity deviceTypeByDeviceType) {
        this.device = deviceTypeByDeviceType;
    }

    private ClazzEntity clazz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_uuid", referencedColumnName = "uuid")
    public ClazzEntity getClazz() {
        return clazz;
    }

    public void setClazz(ClazzEntity clazzByClassUuid) {
        this.clazz = clazzByClassUuid;
    }

    private Collection<ViewSampleEntity> view;

    @OneToMany(mappedBy = "sample", fetch = FetchType.LAZY)
    public Collection<ViewSampleEntity> getView() {
        return view;
    }

    public void setView(Collection<ViewSampleEntity> view) {
        this.view = view;
    }
}
