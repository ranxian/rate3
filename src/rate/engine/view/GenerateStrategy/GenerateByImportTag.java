package rate.engine.view.GenerateStrategy;

import org.hibernate.Query;
import rate.model.SampleEntity;
import rate.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午4:48
 */
public class GenerateByImportTag extends AbstractGenerateStrategy {
    public String getImportTag() {
        return importTag;
    }

    public void setImportTag(String importTag) {
        this.importTag = importTag;
    }

    String importTag;

    public List<SampleEntity>  getSamples() throws GenerateStrategyException {
        if (importTag==null) {
            throw new GenerateStrategyException("No importTag is specified");
        }

        Query query = HibernateUtil.getSession().createQuery("from SampleEntity where importTag = :tag");
        query.setParameter("tag", importTag);
        return query.list();
    }
}
