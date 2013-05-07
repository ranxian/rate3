package rate.controller.benchmark;

import rate.model.BenchmarkEntity;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-7
 * Time: PM4:07
 * To change this template use File | Settings | File Templates.
 */
public class RunOnceAction extends BenchmarkActionBase {
    private String benchmarkContent;
    public void setBenchmarkContent(String content) {
        benchmarkContent = content;
    }

    public String getBenchmarkContent() {
        return this.benchmarkContent;
    }

    public String execute() throws Exception {

        return SUCCESS;
    }
}
