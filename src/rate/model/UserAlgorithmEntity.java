package rate.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-14
 * Time:    下午7:59
 */
@javax.persistence.IdClass(rate.model.UserAlgorithmEntityPK.class)
@javax.persistence.Table(name = "user_algorithm", schema = "", catalog = "rate3")
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
@Entity
public class UserAlgorithmEntity {
    private UUID userUuid;

    @Type(type="UUIDType")
    @javax.persistence.Column(name = "user_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    private UUID algorithmUuid;

    @Type(type="UUIDType")
    @javax.persistence.Column(name = "algorithm_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getAlgorithmUuid() {
        return algorithmUuid;
    }

    public void setAlgorithmUuid(UUID algorithmUuid) {
        this.algorithmUuid = algorithmUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAlgorithmEntity that = (UserAlgorithmEntity) o;

        if (!(algorithmUuid == algorithmUuid)) return false;
        if (!(userUuid == that.userUuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userUuid != null ? userUuid.hashCode() : 0;
        result = 31 * result + (algorithmUuid != null ? algorithmUuid.hashCode() : 0);
        return result;
    }
}
