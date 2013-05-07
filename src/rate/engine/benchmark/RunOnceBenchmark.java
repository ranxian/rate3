package rate.engine.benchmark;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.BenchmarkEntity;
import rate.model.SampleEntity;
import rate.model.ViewEntity;
import rate.util.BaseXX;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import javax.sound.sampled.Line;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-7
 * Time: PM4:11
 * To change this template use File | Settings | File Templates.
 */
public class RunOnceBenchmark extends GeneralBenchmark {
    private static final Logger logger = Logger.getLogger(RunOnceBenchmark.class);

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;


    public void prepare() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        BenchmarkEntity benchmark = new BenchmarkEntity();
        benchmark.setGenerator("Run Once Generator");
        benchmark.setName("RunOnce");
        benchmark.setType("RunOnce");
        benchmark.setView((ViewEntity)session.createQuery("from ViewEntity").list().get(0));
        session.save(benchmark);
        session.getTransaction().commit();
        session.close();
        setBenchmark(benchmark);
        prepareBenchmarkDir();
    }

    public BenchmarkEntity generate() throws Exception {
        logger.info("Begin generate run once benchmark" + benchmark.getUuid());
        PrintWriter writer = new PrintWriter(new FileWriter(benchmark.getHexFilePath()));

        String[] lines = content.split(System.getProperty("line.separator"));
        for (String line : lines ) {
            String[] sp = line.split(" ");
            if (!uuidTable.containsKey(sp[0])) {
                uuidTable.put(sp[0], BaseXX.parse(uuidTable.size()+1));
                SampleEntity sample = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid").setParameter("uuid", sp[0])
                        .list().get(0);
                enrollMap.put(sp[0], sample.getFile());
            }

            if (!uuidTable.containsKey(sp[1])) {
                uuidTable.put(sp[1], BaseXX.parse(uuidTable.size()+1));
                SampleEntity sample = (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid").setParameter("uuid", sp[1])
                        .list().get(0);
                enrollMap.put(sp[0], sample.getFile());
            }

            DebugUtil.debug(String.format("%s %s %s\n", uuidTable.get(sp[0]), uuidTable.get(sp[1]), sp[2]));
            writer.printf("%s %s %s\n", uuidTable.get(sp[0]), uuidTable.get(sp[1]), sp[2]);
        }

        writer.close();

        printUuidTable();

        return benchmark;
    }
}
