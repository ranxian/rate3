package rate.engine.view.GenerateStrategy;

import rate.model.SampleEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk-lab7
 * Date: 13-1-15
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
public class GenerateByTimePeriodStrategy extends AbstractGenerateStrategy {

    public GenerateByTimePeriodStrategy() {
        this.setGenerator("GenerateByTimePeriodGenerator");
    }

    public void setStartTimeStamp(Timestamp startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    private Timestamp startTimeStamp;

    public void setEndTimeStamp(Timestamp endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    private Timestamp endTimeStamp;

    public String getDescription() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return String.format("Period start time: %s, end time: %s", format.format(startTimeStamp), format.format(endTimeStamp));
    }

    @Override
    public String getViewName() {
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy");
        Date startDate = new Date();
        startDate.setTime(startTimeStamp.getTime());
        Date endDate = new Date();
        endDate.setTime(endTimeStamp.getTime());

        return String.format("View-%s-to-%s",
                format.format(startDate),
                format.format(endDate));
    }

    public List<SampleEntity> getSamples() {

        List<SampleEntity> samples = session.createQuery("from SampleEntity as S where S.created>:start and S.created<:end")
                .setParameter("start", startTimeStamp)
                .setParameter("end", endTimeStamp)
                //.setMaxResults(100)
                .list();

        return samples;
    }
}
