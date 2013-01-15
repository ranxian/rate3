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
@Table(name = "class", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class ClazzEntity {
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

    private String type;

    @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 11, precision = 0)
    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Timestamp created;

    @Column(name = "created", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
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

        ClazzEntity that = (ClazzEntity) o;

        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (importTag != null ? !importTag.equals(that.importTag) : that.importTag != null) return false;
        if (getPerson() != null ? !getPerson().equals(that.getPerson()) : that.getPerson() != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (importTag != null ? importTag.hashCode() : 0);
        return result;
    }

    private PersonEntity person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_uuid", referencedColumnName = "uuid")
    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    private Collection<SampleEntity> samples;

    @OneToMany(mappedBy = "clazz")
    public Collection<SampleEntity> getSamples() {
        return samples;
    }

    public void setSamples(Collection<SampleEntity> samples) {
        this.samples = samples;
    }

    @Transient
    public String getUuidShort() {
        return this.uuid.split("-")[0];
    }
    private void setUuidShort(String nonsense) {}
}
