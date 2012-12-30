package rate.model;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.RateConfig;
import rate.util.UUIDType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午7:59
 */
@Table(name = "algorithm", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class AlgorithmEntity {
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

    private String protocol;

    @Column(name = "protocol", nullable = false, insertable = true, updatable = true, length = 8, precision = 0)
    @Basic
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgorithmEntity that = (AlgorithmEntity) o;

        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!(uuid == that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public String dir() {
        String dir = FilenameUtils.concat(RateConfig.getAlgorithmRootDir(), this.getUuid());
        return FilenameUtils.separatorsToUnix(dir);
    }

    private Collection<AlgorithmVersionEntity> algorithmVersionsByUuid;

    @OneToMany(mappedBy = "algorithmByAlgorithmUuid")
    public Collection<AlgorithmVersionEntity> getAlgorithmVersionsByUuid() {
        return algorithmVersionsByUuid;
    }

    public void setAlgorithmVersionsByUuid(Collection<AlgorithmVersionEntity> algorithmVersionsByUuid) {
        this.algorithmVersionsByUuid = algorithmVersionsByUuid;
    }

    private Collection<UserAlgorithmEntity> userAlgorithmsByUuid;

    @OneToMany(mappedBy = "algorithmByAlgorithmUuid")
    public Collection<UserAlgorithmEntity> getUserAlgorithmsByUuid() {
        return userAlgorithmsByUuid;
    }

    public void setUserAlgorithmsByUuid(Collection<UserAlgorithmEntity> userAlgorithmsByUuid) {
        this.userAlgorithmsByUuid = userAlgorithmsByUuid;
    }
}
