package rate.controller.task;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-22
 * Time: 下午11:28
 */
public class BadResultAction extends TaskActionBase {
    private String resultType;
    private String num;
    private String log;

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLog() {
        return log;
    }

    public String execute() throws Exception {
        log = fvc2006Task.getLogByTypeNumber(resultType, num);
        log.replaceAll("\\r\\n", "<br />").replaceAll("\\n", "<br />");
        return SUCCESS;
    }
}
