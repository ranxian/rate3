package rate.engine.view.GenerateStrategy;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import rate.model.SampleEntity;
import rate.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午4:48
 */
public class GenerateAllStrategy extends AbstractGenerateStrategy {

    private final Logger logger = Logger.getLogger(GenerateAllStrategy.class);

    public GenerateAllStrategy() {
        this.setGenerator("GenerateAllGenerator");
    }

    public String getViewName() {
        String viewName = "FullView_" + new SimpleDateFormat("dd_MM_yyyy").format(new Date());
        logger.debug(String.format("ViewName [%s]", viewName));
        return viewName;
    }

    public List<SampleEntity>  getSamples() throws GenerateStrategyException {

        if (getViewName()==null || getGenerator()==null) {
            throw new GenerateStrategyException("No viewName or no generator name specified");
        }

        Query query = HibernateUtil.getSession().createQuery("from SampleEntity");

        return query.list();
    }
}
