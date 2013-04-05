package rate.engine.task;

import org.hibernate.Session;
import rate.model.ClazzEntity;
import rate.model.SampleEntity;
import rate.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-24
 * Time: 下午5:56
 */
public class BadResult {
    private static final Session session = HibernateUtil.getSession();
    private String resultType;
    private String num;
    private SampleEntity sample1;
    private SampleEntity sample2;
    private ClazzEntity class1;
    private ClazzEntity class2;
    private String score;
    private String log;
    private GeneralTask GeneralTask;

    public SampleEntity getSample1() {
        return sample1;
    }

    public SampleEntity getSample2() {
        return sample2;
    }

    public ClazzEntity getClass1() {
        return class1;
    }

    public ClazzEntity getClass2() {
        return class2;
    }

    public String getScore() {
        return score;
    }

    public String getLog() {
        return log;
    }

    public void setGeneralTask(GeneralTask GeneralTask) {

        this.GeneralTask = GeneralTask;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void generateInfo() throws Exception{
        String logFilePath = GeneralTask.getLogPathByTypeNumber(resultType, num);

        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        String result[] = reader.readLine().split(" ");
        String class1uuid = result[0];
        String sample1uuid = result[1];
        String class2uuid = result[2];
        String sample2uuid = result[3];
        score = result[4];
        class1 = (ClazzEntity)session.createQuery("from ClazzEntity where uuid=:uuid")
                .setParameter("uuid", class1uuid).list().get(0);
        class2 = (ClazzEntity)session.createQuery("from ClazzEntity where uuid=:uuid")
                .setParameter("uuid", class2uuid).list().get(0);
        sample1 = (SampleEntity) session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", sample1uuid).list().get(0);
        sample2 = (SampleEntity) session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", sample2uuid).list().get(0);
        String line;
        StringBuffer logBuffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            logBuffer.append(line);
        }

        this.log = logBuffer.toString();
        this.log = log.replaceAll("\\r\\n", "<br />").replaceAll("\\n", "<br />");
        reader.close();
    }
}
