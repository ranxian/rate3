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
@javax.persistence.Table(name = "algorithm_version", schema = "", catalog = "rate3")
@Entity
public class AlgorithmVersionEntity {
    private byte[] uuid;

    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getUuid() {
        return UUID.nameUUIDFromBytes(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = HexBin.decode(uuid.toString().replace("-", ""));
    }


    private byte[] algorithmUuid;

    @javax.persistence.Column(name = "algorithm_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getAlgorithmUuid() {
        return UUID.nameUUIDFromBytes(algorithmUuid);
    }

    public void setAlgorithmUuid(UUID algorithmUuid) {
        this.algorithmUuid = HexBin.decode(algorithmUuid.toString());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgorithmVersionEntity that = (AlgorithmVersionEntity) o;

        if (!Arrays.equals(algorithmUuid, that.algorithmUuid)) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (!Arrays.equals(uuid, that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? Arrays.hashCode(uuid) : 0;
        result = 31 * result + (algorithmUuid != null ? Arrays.hashCode(algorithmUuid) : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
