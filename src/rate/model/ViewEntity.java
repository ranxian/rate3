package rate.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@Table(name = "view", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class ViewEntity {
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


    private String name;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;

    @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 11, precision = 0)
    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewEntity that = (ViewEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (generator != null ? generator.hashCode() : 0);
        result = 31 * result + (generated != null ? generated.hashCode() : 0);
        return result;
    }

    private String generator;

    @Column(name = "generator", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    private Timestamp generated;

    @Column(name = "generated", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getGenerated() {
        return generated;
    }

    public void setGenerated(Timestamp generated) {
        this.generated = generated;
    }

    private Collection<BenchmarkEntity> benchmarksByUuid;

    @OneToMany(mappedBy = "view")
    public Collection<BenchmarkEntity> getBenchmarksByUuid() {
        return benchmarksByUuid;
    }

    public void setBenchmarksByUuid(Collection<BenchmarkEntity> benchmarksByUuid) {
        this.benchmarksByUuid = benchmarksByUuid;
    }

    private Collection<ViewSampleEntity> viewSamplesByUuid;

    @OneToMany(mappedBy = "view")
    public Collection<ViewSampleEntity> getViewSamplesByUuid() {
        return viewSamplesByUuid;
    }

    public void setViewSamplesByUuid(Collection<ViewSampleEntity> viewSamplesByUuid) {
        this.viewSamplesByUuid = viewSamplesByUuid;
    }

    @Transient
    public int getNumOfBenchmarks() {
        return getBenchmarksByUuid().size();
    }

    private void setNumOfBenchmarks(int noneSense) {
        // make JPA happy
    }

    @Transient
    public int getNumOfClasses() {
        // TODO: The performance should be improved in the future. Maybe put a field in the table.
        Set<String> clazzUuids = new HashSet<String>();
        for (ViewSampleEntity viewSample : getViewSamplesByUuid()) {
              clazzUuids.add(viewSample.getSample().getClazz().getUuid());
        }
        return clazzUuids.size();
    }

    private void setNumOfClasses(int noneSense) {
        // make JPA happy
    }

    @Transient
    public int getNumOfSamples() {
        return getViewSamplesByUuid().size();
    }

    private void setNumOfSamples(int noneSense) {
        // make JPA happy
    }

}
