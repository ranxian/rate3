package rate.engine.view;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.view.GenerateStrategy.AbstractGenerateStrategy;
import rate.model.SampleEntity;
import rate.model.ViewEntity;
import rate.model.ViewSampleEntity;
import rate.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午4:42
 */
public class Generator {
    private static final Logger logger = Logger.getLogger(Generator.class);

    public AbstractGenerateStrategy getGenerateStrategy() {
        return generateStrategy;
    }

    public void setGenerateStrategy(AbstractGenerateStrategy generateStrategy) {
        this.generateStrategy = generateStrategy;
    }

    private AbstractGenerateStrategy generateStrategy;

    public ViewEntity generate() throws Exception {
        if (generateStrategy==null) {
            throw new Exception("No generate strategy specified");
        }
        List<SampleEntity> samples = generateStrategy.getSamples();
        if (samples.size()==0) {
            return null;
        }

        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();

            ViewEntity view = new ViewEntity();
            view.setName(generateStrategy.getViewName());
            view.setGenerator(generateStrategy.getGenerator());
            view.setType("FINGERVEIN"); // TODO: maybe treat it like name and generator
            session.save(view);
            for (SampleEntity sample: samples) {
                ViewSampleEntity toBeInsert = new ViewSampleEntity();
                toBeInsert.setViewUuid(UUID.fromString(view.getUuid()));
                toBeInsert.setSampleUuid(sample.getUuid());
                session.save(toBeInsert);
            }
            session.getTransaction().commit();
            return view;
        }
        catch (Exception ex) {
            logger.debug(ex);
            throw ex;
        }
    }
}
