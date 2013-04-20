package rate.model;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.RateConfig;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午9:18
 */
@Table(name = "benchmark", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class BenchmarkEntity {

    private static final Logger logger = Logger.getLogger(BenchmarkEntity.class);

    private String uuid;

    @Type(type="UUIDType")
    @GenericGenerator(name="UUIDGenerator", strategy="rate.util.UUIDGenerator")
    @GeneratedValue(generator = "UUIDGenerator")
    @javax.persistence.Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
//        logger.trace(String.format("setUuid [%s] -> [%s]", this.getUuid(), uuid));
        this.uuid = uuid;
    }

    private String name;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
//        logger.trace(String.format("setName [%s] [%s]", this.getUuid(), name));
        this.name = name;
    }

    private String type;

    @Column(name ="type", nullable = false, insertable = true, updatable = false, length = 45, precision = 0)
    @Basic
    public String getType() {
        return this.type;
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

    private String description;

    @Column(name = "description", nullable = false, insertable = true, updatable = true, length = 65535, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
//        logger.trace(String.format("setDescription uuid [%s] [%s]", this.uuid, description));
        this.description = description;
    }

    private String generator;

    @Column(name = "generator", nullable = false, insertable = true, updatable = false, length = 255, precision = 0)
    @Basic
    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkEntity that = (BenchmarkEntity) o;

        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (generator != null ? !generator.equals(that.generator) : that.generator != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (!(uuid == that.uuid)) return false;
        if (!(view.equals(that.view))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (view != null ? view.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (generator != null ? generator.hashCode() : 0);
        return result;
    }

    public String dirPath() {
        String dir = FilenameUtils.concat(RateConfig.getBenchmarkRootDir(), this.getUuid());
        return FilenameUtils.separatorsToUnix(dir);
    }

    public String filePath() {
         return FilenameUtils.separatorsToUnix(FilenameUtils.concat(this.dirPath(), "benchmark.txt"));
    }

    private ViewEntity view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "view_uuid", referencedColumnName = "uuid", nullable = false)
    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity viewByViewUuid) {
        this.view = viewByViewUuid;
    }

    private Collection<TaskEntity> tasks;

    @OneToMany(mappedBy = "benchmark")
    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    @Transient
    public int getNumOfTasks() {
        return this.getTasks().size();
    }
    private void setNumOfTasks(int nonsense) {}

    @Transient
    public String getUuidTableFilePath() {
        return FilenameUtils.concat(this.dirPath(), "uuid_table.txt");
    }
    private void setUuidFilePath(String nonsense) {}

    @Transient
    public String getHexFilePath() {
        return FilenameUtils.concat(this.dirPath(), "benchmark_hex.txt");
    }
    private void setHexFilePath(String no) {}

}
