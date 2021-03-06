package rate.model;

import org.hibernate.annotations.TypeDef;
import rate.util.UUIDType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    上午11:08
 */
@TypeDef(name = "UUIDType", typeClass = UUIDType.class)
public class ViewSampleEntityPK implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewSampleEntityPK that = (ViewSampleEntityPK) o;

        if (sample != null ? !sample.equals(that.sample) : that.sample != null) return false;
        if (view != null ? !view.equals(that.view) : that.view != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = view != null ? view.hashCode() : 0;
        result = 31 * result + (sample != null ? sample.hashCode() : 0);
        return result;
    }

    private ViewEntity view;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "view_uuid", referencedColumnName = "uuid")
    @Id
    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity viewByViewUuid) {
        this.view = viewByViewUuid;
    }

    private SampleEntity sample;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "sample_uuid", referencedColumnName = "uuid")
    @Id
    public SampleEntity getSample() {
        return sample;
    }

    public void setSample(SampleEntity sampleBySampleUuid) {
        this.sample = sampleBySampleUuid;
        this.clazz = this.sample.getClazz();
    }

    private ClazzEntity clazz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_uuid", referencedColumnName = "uuid")
    @Id
    public ClazzEntity getClazz() {
        return clazz;
    }

    public void setClazz(ClazzEntity clazzByClazzUuid) {
        this.clazz = clazzByClazzUuid;
    }
}
