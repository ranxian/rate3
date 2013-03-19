package rate.controller.user;

import rate.controller.RateActionBase;
import rate.model.TaskEntity;

import java.util.List;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-18
 * Time: 下午4:48
 */
public class TasksAction extends RateActionBase {
    private List<TaskEntity> tasks;

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    private String running;

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public String execute() throws Exception {
        if (!getIsUserSignedIn()) return "eLogin";

        return SUCCESS;
    }
}
