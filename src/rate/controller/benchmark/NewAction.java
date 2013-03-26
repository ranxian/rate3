package rate.controller.benchmark;

import rate.model.BenchmarkEntity;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-26
 * Time: 上午11:58
 */
public class NewAction extends BenchmarkActionBase {
    private String viewUuid;

    public String getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(String viewUuid) {
        this.viewUuid = viewUuid;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
}
