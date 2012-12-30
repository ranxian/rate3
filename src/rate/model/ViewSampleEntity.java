package rate.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@IdClass(ViewSampleEntityPK.class)
@Table(name = "view_sample", schema = "", catalog = "rate3")
@Entity
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class ViewSampleEntity {
    private String viewUuid;

    @Column(name = "view_uuid", nullable = false, insertable = false, updatable = false, length = 16, precision = 0)
    @Id
    public String getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(String viewUuid) {
        this.viewUuid = viewUuid;
    }

    private String sampleUuid;

    @Type(type="UUIDType")
    @Column(name = "sample_uuid", nullable = false, insertable = false, updatable = false, length = 16, precision = 0)
    @Id
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

    private ViewEntity viewByViewUuid;

    @ManyToOne
    @JoinColumn(name = "view_uuid", referencedColumnName = "uuid", nullable = false)
    public ViewEntity getViewByViewUuid() {
        return viewByViewUuid;
    }

    public void setViewByViewUuid(ViewEntity viewByViewUuid) {
        this.viewByViewUuid = viewByViewUuid;
    }

    private SampleEntity sampleBySampleUuid;

    @ManyToOne
    @JoinColumn(name = "sample_uuid", referencedColumnName = "uuid", nullable = false)
    public SampleEntity getSampleBySampleUuid() {
        return sampleBySampleUuid;
    }

    public void setSampleBySampleUuid(SampleEntity sampleBySampleUuid) {
        this.sampleBySampleUuid = sampleBySampleUuid;
    }
}
