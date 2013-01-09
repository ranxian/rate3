package rate.controller.task;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.task.FVC2006Task;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends TaskActionBase {

    public String execute() throws Exception {
        return SUCCESS;
    }
}
