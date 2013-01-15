package rate.engine.benchmark.generator;

import com.sun.corba.se.spi.orb.ParserImplBase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午9:04
 */
public class OneClassImposterGenerator extends GeneralImposterGenerator {

    private static final Logger logger = Logger.getLogger(OneClassImposterGenerator.class);

    public void setImposterClazzUuid(String imposterClazzUuid) {
        ClazzEntity clazz = (ClazzEntity)session.createQuery("from ClazzEntity where uuid=:uuid")
                .setParameter("uuid", imposterClazzUuid)
                .list().get(0);
        this.setImposterClazz(clazz);
    }

    public void setImposterClazz(ClazzEntity imposterClazz) {
        this.imposterClazz = imposterClazz;
    }

    private ClazzEntity imposterClazz;

    public BenchmarkEntity generate() throws Exception {

        this.setBenchmarkName(String.format("Imposter[%s]", this.imposterClazz.getUuid()));
        logger.trace(String.format("Imposter class [%s]", this.imposterClazz.getUuid()));

        // imposter
        List<Pair<ClazzEntity, List<SampleEntity>>> imposterClassAndSamples = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();
        List<SampleEntity> imposterSamples = new ArrayList<SampleEntity>(
                session.createQuery("from SampleEntity where clazz=:clazz order by rand()")
                .setParameter("clazz", this.imposterClazz)
                .setMaxResults(2).list()
        );
        imposterClassAndSamples.add(new ImmutablePair<ClazzEntity, List<SampleEntity>>(imposterClazz, imposterSamples));
        this.setImposterClassAndSamples(imposterClassAndSamples);

        // imposted
        List<Pair<ClazzEntity, List<SampleEntity>>> impostedClassAndSamples = new ArrayList<Pair<ClazzEntity, List<SampleEntity>>>();
        List<ClazzEntity> impostedClasses = new ArrayList<ClazzEntity>(
                session.createQuery("from ClazzEntity where uuid!=:uuid")
                        .setParameter("uuid", this.imposterClazz.getUuid())
                        .list()
        );
        for (ClazzEntity impostedClazz: impostedClasses) {
            List<SampleEntity> impostedSamples = new ArrayList<SampleEntity>(
                    session.createQuery("from SampleEntity where clazz=:clazz order by rand()")
                            .setParameter("clazz", impostedClazz)
                            .setMaxResults(2).list()
            );
            impostedClassAndSamples.add(new ImmutablePair<ClazzEntity, List<SampleEntity>>(impostedClazz, impostedSamples));
            logger.trace(String.format("New imposted class [%s] [%d]", impostedClazz.getUuid(), impostedClassAndSamples.size()));
        }
        this.setImpostedClassAndSamples(impostedClassAndSamples);

        return super.generate();
    }
}
