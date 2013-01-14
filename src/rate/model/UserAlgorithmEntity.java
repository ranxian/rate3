package rate.model;

import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;

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

    private UserEntity user;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    @Id
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userByUserUuid) {
        this.user = userByUserUuid;
    }

    private AlgorithmEntity algorithm;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "algorithm_uuid", referencedColumnName = "uuid")
    @Id
    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithmByAlgorithmUuid) {
        this.algorithm = algorithmByAlgorithmUuid;
    }
}
