package rate.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午7:59
 */
@IdClass(UserAlgorithmEntityPK.class)
@Table(name = "user_algorithm", schema = "", catalog = "rate3")
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
@Entity
public class UserAlgorithmEntity {
    private String userUuid;

    @Type(type="UUIDType")
    @Column(name = "user_uuid", nullable = false, insertable = false, updatable = false, length = 16, precision = 0)
    @Id
    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    private String algorithmUuid;

    @Type(type="UUIDType")
    @Column(name = "algorithm_uuid", nullable = false, insertable = false, updatable = false, length = 16, precision = 0)
    @Id
    public String getAlgorithmUuid() {
        return algorithmUuid;
    }

    public void setAlgorithmUuid(String algorithmUuid) {
        this.algorithmUuid = algorithmUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAlgorithmEntity that = (UserAlgorithmEntity) o;

        if (!(getAlgorithmByAlgorithmUuid() == that.getAlgorithmByAlgorithmUuid())) return false;
        if (!(getUserByUserUuid() == that.getUserByUserUuid())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getUserByUserUuid() != null ? getUserByUserUuid().hashCode() : 0;
        result = 31 * result + (getAlgorithmByAlgorithmUuid() != null ? getAlgorithmByAlgorithmUuid().hashCode() : 0);
        return result;
    }

    private UserEntity userByUserUuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    public UserEntity getUserByUserUuid() {
        return userByUserUuid;
    }

    public void setUserByUserUuid(UserEntity userByUserUuid) {
        this.userByUserUuid = userByUserUuid;
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
}
