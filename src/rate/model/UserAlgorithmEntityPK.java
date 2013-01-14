package rate.model;

import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午7:59
 */
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class UserAlgorithmEntityPK implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAlgorithmEntity that = (UserAlgorithmEntity) o;

        if (!(getAlgorithm() == that.getAlgorithm())) return false;
        if (!(getUser() == that.getUser())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getAlgorithm() != null ? getAlgorithm().hashCode() : 0);
        return result;
    }

    private UserEntity userByUserUuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    @Id
    public UserEntity getUser() {
        return userByUserUuid;
    }

    public void setUser(UserEntity userByUserUuid) {
        this.userByUserUuid = userByUserUuid;
    }

    private AlgorithmEntity algorithm;

    @ManyToOne
    @JoinColumn(name = "algorithm_uuid", referencedColumnName = "uuid", nullable = false)
    @Id
    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithmByAlgorithmUuid) {
        this.algorithm = algorithmByAlgorithmUuid;
    }
}
