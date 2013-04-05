package rate.controller.task;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-6
 * Time: 下午9:45
 * To change this template use File | Settings | File Templates.
 */
public class DeleteAction extends TaskActionBase {
    private static final Logger logger = Logger.getLogger(DeleteAction.class);

    public String execute() throws Exception {
        FileUtils.deleteDirectory(new File(task.getDirPath()));
        session.beginTransaction();
        session.delete(task);
        session.getTransaction().commit();

        return SUCCESS;
    }
}
