package rate.db;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
public class ViewSampleEntityPK implements Serializable {
    private byte[] viewUuid;

    @Id
    @Column(name = "view_uuid")
    public byte[] getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(byte[] viewUuid) {
        this.viewUuid = viewUuid;
    }

    private byte[] sampleUuid;

    @Id
    @Column(name = "sample_uuid")
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

        ViewSampleEntityPK that = (ViewSampleEntityPK) o;

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
