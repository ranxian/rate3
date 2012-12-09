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
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@javax.persistence.Table(name = "task", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class TaskEntity {
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

    private UUID algorithmVersionUuid;

    @javax.persistence.Column(name = "algorithm_version_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getAlgorithmVersionUuid() {
        return algorithmVersionUuid;
    }

    public void setAlgorithmVersionUuid(UUID algorithmVersionUuid) {
        this.algorithmVersionUuid = algorithmVersionUuid;
    }

    private UUID benchmarkUuid;

    @javax.persistence.Column(name = "benchmark_uuid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    public UUID getBenchmarkUuid() {
        return benchmarkUuid;
    }

    public void setBenchmarkUuid(UUID benchmarkUuid) {
        this.benchmarkUuid = benchmarkUuid;
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

        if (algorithmVersionUuid != null ? !algorithmVersionUuid.equals(that.algorithmVersionUuid) : that.algorithmVersionUuid != null)
            return false;
        if (benchmarkUuid != null ? !benchmarkUuid.equals(that.benchmarkUuid) : that.benchmarkUuid != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (algorithmVersionUuid != null ? algorithmVersionUuid.hashCode() : 0);
        result = 31 * result + (benchmarkUuid != null ? benchmarkUuid.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        return result;
    }
}
