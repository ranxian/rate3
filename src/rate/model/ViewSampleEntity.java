package rate.model;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
@IdClass(ViewSampleEntityPK.class)
@Table(name = "view_sample", schema = "", catalog = "rate3")
@Entity
public class ViewSampleEntity {
    private byte[] viewUuid;

    @Column(name = "view_uuid")
    @Id
    public byte[] getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(byte[] viewUuid) {
        this.viewUuid = viewUuid;
    }

    private byte[] sampleUuid;

    @Column(name = "sample_uuid")
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
