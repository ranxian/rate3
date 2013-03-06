package rate.controller.sessions;

import com.opensymphony.xwork2.ActionContext;
import rate.controller.RateActionBase;

import java.util.Map;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-6
 * Time: 下午1:39
 */
public class DeleteAction extends RateActionBase {
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        session.remove("user-uuid");
        return SUCCESS;
    }
}
