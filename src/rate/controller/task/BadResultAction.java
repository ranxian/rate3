package rate.controller.task;

import rate.engine.task.BadResult;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-22
 * Time: 下午11:28
 */
public class BadResultAction extends TaskActionBase {
    private String resultType;
    private String num;
    // 应该有一个基类啥的
    private BadResult result;

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public BadResult getResult() {
        return this.result;
    }
    public String execute() throws Exception {
        result = new BadResult();
        result.setResultType(resultType);
        result.setNum(num);
        result.setGeneralTask(generalTask);
        result.generateInfo();
        return SUCCESS;
    }
}
