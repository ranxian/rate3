package rate.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class ViewSampleEntityPK implements Serializable {
    private String viewUuid;

    @Id
    @Column(name = "view_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    public String getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(String viewUuid) {
        this.viewUuid = viewUuid;
    }

    private String sampleUuid;

    @Type(type="UUIDType")
    @Id
    @Column(name = "sample_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    public String getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(String sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewSampleEntityPK that = (ViewSampleEntityPK) o;

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
