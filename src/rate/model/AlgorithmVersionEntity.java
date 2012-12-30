package rate.model;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.RateConfig;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

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

//    private String algorithmUuid;
//
//    @Column(name = "algorithm_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
//    @Basic
//    public String getAlgorithmUuid() {
//        return algorithmUuid;
//    }
//
//    public void setAlgorithmUuid(String algorithmUuid) {
//        this.algorithmUuid = algorithmUuid;
//    }

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

//        if (algorithmUuid != null ? !algorithmUuid.equals(that.algorithmUuid) : that.algorithmUuid != null)
//            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (getAlgorithmByAlgorithmUuid() != null ? getAlgorithmByAlgorithmUuid().hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    public String dir() {
        String dir = FilenameUtils.concat(RateConfig.getAlgorithmRootDir(), this.getAlgorithmByAlgorithmUuid().getUuid()).concat(this.getUuid());
        return FilenameUtils.separatorsToUnix(dir);
    }

    private AlgorithmEntity algorithmByAlgorithmUuid;

    @ManyToOne
    @JoinColumn(name = "algorithm_uuid", referencedColumnName = "uuid", nullable = false)
    public AlgorithmEntity getAlgorithmByAlgorithmUuid() {
        return algorithmByAlgorithmUuid;
    }

    public void setAlgorithmByAlgorithmUuid(AlgorithmEntity algorithmByAlgorithmUuid) {
        this.algorithmByAlgorithmUuid = algorithmByAlgorithmUuid;
    }

    private Collection<TaskEntity> tasksByUuid;

    @OneToMany(mappedBy = "algorithmVersionByAlgorithmVersionUuid")
    public Collection<TaskEntity> getTasksByUuid() {
        return tasksByUuid;
    }

    public void setTasksByUuid(Collection<TaskEntity> tasksByUuid) {
        this.tasksByUuid = tasksByUuid;
    }
}
