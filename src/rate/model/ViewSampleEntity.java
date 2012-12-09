package rate.model;

import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@javax.persistence.IdClass(rate.model.ViewSampleEntityPK.class)
@javax.persistence.Table(name = "view_sample", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class ViewSampleEntity {
    private UUID viewUuid;

    @javax.persistence.Column(name = "view_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(UUID viewUuid) {
        this.viewUuid = viewUuid;
    }

    private UUID sampleUuid;

    @javax.persistence.Column(name = "sample_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public UUID getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(UUID sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewSampleEntity that = (ViewSampleEntity) o;

        if (sampleUuid != null ? !sampleUuid.equals(that.sampleUuid) : that.sampleUuid != null) return false;
        if (viewUuid != null ? !viewUuid.equals(that.viewUuid) : that.viewUuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = viewUuid != null ? viewUuid.hashCode() : 0;
        result = 31 * result + (sampleUuid != null ? sampleUuid.hashCode() : 0);
        return result;
    }
}
