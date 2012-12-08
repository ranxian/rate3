package rate.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-8
 * Time:    下午10:46
 */
@javax.persistence.IdClass(rate.model.ViewSampleEntityPK.class)
@javax.persistence.Table(name = "view_sample", schema = "", catalog = "rate3")
@Entity
public class ViewSampleEntity {
    private byte[] viewUuid;

    @javax.persistence.Column(name = "view_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public byte[] getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(byte[] viewUuid) {
        this.viewUuid = viewUuid;
    }

    private byte[] sampleUuid;

    @javax.persistence.Column(name = "sample_uuid", nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Id
    public byte[] getSampleUuid() {
        return sampleUuid;
    }

    public void setSampleUuid(byte[] sampleUuid) {
        this.sampleUuid = sampleUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewSampleEntity that = (ViewSampleEntity) o;

        if (!Arrays.equals(sampleUuid, that.sampleUuid)) return false;
        if (!Arrays.equals(viewUuid, that.viewUuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = viewUuid != null ? Arrays.hashCode(viewUuid) : 0;
        result = 31 * result + (sampleUuid != null ? Arrays.hashCode(sampleUuid) : 0);
        return result;
    }
}
