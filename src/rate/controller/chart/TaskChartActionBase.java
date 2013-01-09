package rate.controller.chart;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import org.jfree.chart.JFreeChart;
import rate.engine.task.FVC2006Task;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class TaskChartActionBase extends ActionSupport {

    protected JFreeChart chart;
    private String taskUuid;
    protected TaskEntity task;
    protected FVC2006Task fvc2006Task;
    protected final Session session = HibernateUtil.getSession();

    public void setTaskUuid(String taskUuid) throws Exception {
        this.taskUuid = taskUuid;
        this.task = (TaskEntity)session.createQuery("from TaskEntity where uuid = :uuid")
                .setParameter("uuid", taskUuid)
                .list().get(0);
        this.fvc2006Task = new FVC2006Task(task);
    }

    public JFreeChart getChart() {
        return chart;
    }
}
