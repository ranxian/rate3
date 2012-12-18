package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import rate.model.AlgorithmEntity;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 12-12-18
 * Time: 下午9:43
 */
public class NewAction extends ActionSupport {
    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithm) {
        this.algorithm = algorithm;
    }

    AlgorithmEntity algorithm;

    public String execute() throws Exception {
        return SUCCESS;
    }
}
