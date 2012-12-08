package rate.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-8
 * Time:    下午10:46
 */
@javax.persistence.Table(name = "class", schema = "", catalog = "rate3")
@Entity
public class ClazzEntity {
    private byte[] uuid;

    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getUuid() {
        return UUID.nameUUIDFromBytes(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = HexBin.decode(uuid.toString().replace("-", ""));
    }

    private byte[] personUuid;

    @javax.persistence.Column(name = "person_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getPersonUuid() {
        return UUID.nameUUIDFromBytes(personUuid);
    }

    public void setPersonUuid(UUID personUuid) {
        this.personUuid = HexBin.decode(personUuid.toString());
    }

    private String type;

    @javax.persistence.Column(name = "type", nullable = false, insertable = true, updatable = true, length = 11, precision = 0)
    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Timestamp created;

    @javax.persistence.Column(name = "created", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
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

        ClazzEntity that = (ClazzEntity) o;

        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (importTag != null ? !importTag.equals(that.importTag) : that.importTag != null) return false;
        if (!Arrays.equals(personUuid, that.personUuid)) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!Arrays.equals(uuid, that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? Arrays.hashCode(uuid) : 0;
        result = 31 * result + (personUuid != null ? Arrays.hashCode(personUuid) : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (importTag != null ? importTag.hashCode() : 0);
        return result;
    }
}
