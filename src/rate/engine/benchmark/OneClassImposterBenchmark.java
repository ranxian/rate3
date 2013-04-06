package rate.engine.benchmark;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import rate.model.BenchmarkEntity;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-6
 * Time: 下午4:17
 */
public class OneClassImposterBenchmark extends GeneralImposterBenchmark {
    private static final Logger logger = Logger.getLogger(OneClassImposterBenchmark.class);

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

    public void setImpostedClassCountLimit(int impostedClassCountLimit) {
        this.impostedClassCountLimit = impostedClassCountLimit;
    }

    private int impostedClassCountLimit=0;

    public void setClazzAndSample() {
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
        Query query =session.createQuery("from ClazzEntity where uuid!=:uuid order by rand()")
                .setParameter("uuid", this.imposterClazz.getUuid());
        if (impostedClassCountLimit!=0) query.setMaxResults(impostedClassCountLimit);
        List<ClazzEntity> impostedClasses = new ArrayList<ClazzEntity>(
                query.list());

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
    }

    public BenchmarkEntity generate() throws Exception {
        if (imposterClazz == null) {
            imposterClazz = (ClazzEntity) session.createQuery("from ClazzEntity order by RAND()").list().get(0);
        }
        benchmark.setName(String.format("Imposter-[%s]", this.imposterClazz.getUuidShort()));
        benchmark.setType("OneClassImposter");
        logger.trace(String.format("Imposter class [%s]", this.imposterClazz.getUuid()));

        setClazzAndSample();

        return super.generate();
    }
}
