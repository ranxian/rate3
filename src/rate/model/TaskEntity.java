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
@javax.persistence.Table(name = "task", schema = "", catalog = "rate3")
@Entity
public class TaskEntity {
    private byte[] uuid;

    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getUuid() {
        return UUID.nameUUIDFromBytes(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = HexBin.decode(uuid.toString().replace("-", ""));
    }

    private byte[] algorithmVersionUuid;

    @javax.persistence.Column(name = "algorithm_version_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getAlgorithmVersionUuid() {
        return UUID.nameUUIDFromBytes(algorithmVersionUuid);
    }

    public void setAlgorithmVersionUuid(UUID algorithmVersionUuid) {
        this.algorithmVersionUuid = HexBin.decode(algorithmVersionUuid.toString());
    }

    private byte[] benchmarkUuid;

    @javax.persistence.Column(name = "benchmark_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getBenchmarkUuid() {
        return UUID.nameUUIDFromBytes(benchmarkUuid);
    }

    public void setBenchmarkUuid(UUID benchmarkUuid) {
        this.benchmarkUuid = HexBin.decode(benchmarkUuid.toString());
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

    private Timestamp finished;

    @javax.persistence.Column(name = "finished", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getFinished() {
        return finished;
    }

    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (!Arrays.equals(algorithmVersionUuid, that.algorithmVersionUuid)) return false;
        if (!Arrays.equals(benchmarkUuid, that.benchmarkUuid)) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
        if (!Arrays.equals(uuid, that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? Arrays.hashCode(uuid) : 0;
        result = 31 * result + (algorithmVersionUuid != null ? Arrays.hashCode(algorithmVersionUuid) : 0);
        result = 31 * result + (benchmarkUuid != null ? Arrays.hashCode(benchmarkUuid) : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        return result;
    }
}
