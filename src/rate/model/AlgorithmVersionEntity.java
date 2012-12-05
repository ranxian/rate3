package rate.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "algorithm_version", schema = "", catalog = "rate3")
@Entity
public class AlgorithmVersionEntity {
    private byte[] uuid;

    @Column(name = "uuid")
    @Id
    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

    private byte[] algorithmUuid;

    @Column(name = "algorithm_uuid")
    @Basic
    public byte[] getAlgorithmUuid() {
        return algorithmUuid;
    }

    public void setAlgorithmUuid(byte[] algorithmUuid) {
        this.algorithmUuid = algorithmUuid;
    }

    private Timestamp created;

    @Column(name = "created")
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
