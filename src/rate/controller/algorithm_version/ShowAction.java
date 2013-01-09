package rate.controller.algorithm_version;

import rate.model.TaskEntity;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 上午11:53
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends AlgorithmVersionActionBase {

    public Collection<TaskEntity> getTasks() {
        return tasks;
    }

    private Collection<TaskEntity> tasks;


    public String execute() {
        tasks = algorithmVersion.getTasksByUuid();

        return SUCCESS;
    }
}
