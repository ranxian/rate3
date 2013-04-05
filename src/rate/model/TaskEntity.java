package rate.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.engine.task.GeneralTask;
import rate.util.RateConfig;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;

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

    private final Logger logger = Logger.getLogger(TaskEntity.class);

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

        if (algorithmVersion != null ? !algorithmVersion.equals(that.algorithmVersion) : that.algorithmVersion != null)
            return false;
        if (benchmark != null ? !benchmark.equals(that.benchmark) : that.benchmark != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (algorithmVersion != null ? algorithmVersion.hashCode() : 0);
        result = 31 * result + (benchmark != null ? benchmark.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        return result;
    }

    private BenchmarkEntity benchmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benchmark_uuid", referencedColumnName = "uuid")
    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(BenchmarkEntity benchmarkByBenchmarkUuid) {
        this.benchmark = benchmarkByBenchmarkUuid;
    }

    private AlgorithmVersionEntity algorithmVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_version_uuid", referencedColumnName = "uuid")
    public AlgorithmVersionEntity getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(AlgorithmVersionEntity algorithmVersionByAlgorithmVersionUuid) {
        this.algorithmVersion = algorithmVersionByAlgorithmVersionUuid;
    }

    @Transient
    public String getTempDirPath() {
        String p = FilenameUtils.concat(FilenameUtils.concat(RateConfig.getTempRootDir(), "tasks"), this.getUuid());
        String r = FilenameUtils.separatorsToUnix(p);
//        logger.trace(r);
        return r;
    }
    private void setTempDirPath(String nonsense) {}

    @Transient
    public String getDirPath() {
        String p = FilenameUtils.concat(RateConfig.getTaskRootDir(), this.getUuid());
        return FilenameUtils.separatorsToUnix(p);
    }
    private void setDirPath(String nonsense) {}

    @Transient
    public String getResultFilePath() {
        String p = FilenameUtils.concat(getDirPath(), "result.txt");
        return FilenameUtils.separatorsToUnix(p);
    }
    private void setResultFilePath(String nonsense) {}

    @Transient
    public String getUuidShort() {
        return uuid.split("-")[0];
    }
    private void setUuidShort(String nonsense) {}

    @Transient
    public String getRunnerName() {
        return this.algorithmVersion.getAlgorithm().getAuthorName();
    }
    public void setRunnerName(String nonsense) {}

    @Transient
    public Double getGeneralPercentage() throws Exception{
//        if (this.benchmark.getProtocol().equals("FVC2006")) {
            GeneralTask GeneralTask = new GeneralTask(this);
            return GeneralTask.getPercentage();
//        } else {
//            DebugUtil.debug(this.benchmark.getProtocol());
//            return 0.0;
//        }
    }
    private void setGeneralPercentage(Double nonsense) {}
}
