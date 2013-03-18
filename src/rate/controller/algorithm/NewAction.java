package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 12-12-18
 * Time: 下午9:43
 */
public class NewAction extends RateActionBase {
   public String execute() throws Exception {
        if (!getIsUserSignedIn()) return "eLogin";

        return SUCCESS;
    }
}
