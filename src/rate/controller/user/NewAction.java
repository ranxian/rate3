package rate.controller.user;

import com.opensymphony.xwork2.ActionSupport;
import rate.controller.RateActionBase;
import rate.model.UserEntity;
import rate.model.ViewEntity;

/**
 * Created by XianRan
 * Time: 下午9:16
 */
public class NewAction extends RateActionBase {
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    UserEntity user;

    public String execute() throws Exception {
        return SUCCESS;
    }
}
