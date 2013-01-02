package rate.model;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.RateConfig;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@Table(name = "task", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class TaskEntity {
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

    private String algorithmVersionUuid;

    @Type(type="UUIDType")
    @Column(name = "algorithm_version_uuid", nullable = true, insertable = false, updatable = false, length = 16, precision = 0)
    @Basic
    public String getAlgorithmVersionUuid() {
        return algorithmVersionUuid;
    }

    public void setAlgorithmVersionUuid(String algorithmVersionUuid) {
        this.algorithmVersionUuid = algorithmVersionUuid;
    }

    private String benchmarkUuid;

    @Column(name = "benchmark_uuid", nullable = true, insertable = false, updatable = false, length = 16, precision = 0)
    @Basic
    public String getBenchmarkUuid() {
        return benchmarkUuid;
    }

    public void setBenchmarkUuid(String benchmarkUuid) {
        this.benchmarkUuid = benchmarkUuid;
    }

    private Timestamp created;

    @Column(name = "created", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private Timestamp finished;

    @Column(name = "finished", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
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

    private BenchmarkEntity benchmarkByBenchmarkUuid;

    @ManyToOne
    @JoinColumn(name = "benchmark_uuid", referencedColumnName = "uuid")
    public BenchmarkEntity getBenchmarkByBenchmarkUuid() {
        return benchmarkByBenchmarkUuid;
    }

    public void setBenchmarkByBenchmarkUuid(BenchmarkEntity benchmarkByBenchmarkUuid) {
        this.benchmarkByBenchmarkUuid = benchmarkByBenchmarkUuid;
    }

    private AlgorithmVersionEntity algorithmVersionByAlgorithmVersionUuid;

    @ManyToOne
    @JoinColumn(name = "algorithm_version_uuid", referencedColumnName = "uuid")
    public AlgorithmVersionEntity getAlgorithmVersionByAlgorithmVersionUuid() {
        return algorithmVersionByAlgorithmVersionUuid;
    }

    public void setAlgorithmVersionByAlgorithmVersionUuid(AlgorithmVersionEntity algorithmVersionByAlgorithmVersionUuid) {
        this.algorithmVersionByAlgorithmVersionUuid = algorithmVersionByAlgorithmVersionUuid;
    }

    public String tempDirPath() {
        String p = FilenameUtils.concat(FilenameUtils.concat(RateConfig.getTempRootDir(), "tasks"), this.getUuid());
        return FilenameUtils.separatorsToUnix(p);
    }

    public String dirPath() {
        String p = FilenameUtils.concat(RateConfig.getTaskRootDir(), this.getUuid());
        return FilenameUtils.separatorsToUnix(p);
    }


}
