package rate.model;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.HibernateUtil;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@Table(name = "algorithm_version", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class AlgorithmVersionEntity {
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

    @Column(name = "created", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
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

        if (algorithm != null ? !algorithm.equals(that.algorithm) : that.algorithm != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (getAlgorithm() != null ? getAlgorithm().hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    private AlgorithmEntity algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_uuid", referencedColumnName = "uuid", nullable = false)
    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithmByAlgorithmUuid) {
        this.algorithm = algorithmByAlgorithmUuid;
    }

    private Collection<TaskEntity> tasks;

    @OneToMany(mappedBy = "algorithmVersion")
    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<TaskEntity> tasksByUuid) {
        this.tasks = tasksByUuid;
    }

    public String dirPath() {
        String dir = FilenameUtils.concat(getAlgorithm().dirPath(), this.getUuid());
        return FilenameUtils.separatorsToUnix(dir);
    }

    @Transient
    public int getNumOfResults() {
        return this.getTasks().size();
    }

    private void setNumOfResults(int nonsense) {
        // make JPA happy
    }

    private String description;

    @Column(name = "description", nullable = false, insertable = true, updatable = true, length = 65535, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public String getUuidShort() {
        return uuid.split("-")[0];
    }
    private void setUuidShort(String nonsense) {}

    @Transient
    public String getAuthorName() {
        return this.algorithm.getAuthorName();
    }

    private void setAuthorName(String nonsense) {}

    @Transient
    public String getBareDir() {
        return "algorithms" + "/" + getAlgorithm().getUuid() + "/" + this.getUuid();
    }

    private void setBareDir(String nonsense) {}

    @Transient
    public TaskEntity getTaskOn(BenchmarkEntity benchmark) {
        Session session = HibernateUtil.getSession();
        List<TaskEntity>  tasks = session.createQuery("from TaskEntity where algorithmVersion=:version and benchmark=:benchmark")
                .setParameter("version", this).setParameter("benchmark", benchmark)
                .list();
        if (tasks.isEmpty()) return null;
        return tasks.get(0);
    }
    private void setTaskEntity(TaskEntity entity) { }
}
