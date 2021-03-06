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

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
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
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    private String generator;

    @Column(name = "generator", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
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

    private Collection<BenchmarkEntity> benchmarks;

    @OneToMany(mappedBy = "view")
    public Collection<BenchmarkEntity> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(Collection<BenchmarkEntity> benchmarks) {
        this.benchmarks = benchmarks;
    }

    private Collection<ViewSampleEntity> viewSamples;

    @OneToMany(mappedBy = "view", fetch = FetchType.LAZY)
    public Collection<ViewSampleEntity> getViewSamples() {
        return viewSamples;
    }

    public void setViewSamples(Collection<ViewSampleEntity> viewSamples) {
        this.viewSamples = viewSamples;
    }

    @Transient
    public int getNumOfBenchmarks() {
        return getBenchmarks().size();
    }

    private void setNumOfBenchmarks(int noneSense) {
        // make JPA happy
    }

    private int numOfClasses;

    @Column(name = "numOfClasses", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public int getNumOfClasses() {
        return this.numOfClasses;
    }

    public void setNumOfClasses(int numOfClasses) {
        this.numOfClasses = numOfClasses;
    }

    private int numOfSamples;

    @Column(name = "numOfSamples", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public int getNumOfSamples() {
        return numOfSamples;
    }

    public void setNumOfSamples(int numOfSamples) {
        this.numOfSamples = numOfSamples;
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

}
