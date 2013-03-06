package rate.controller.sessions;
import com.opensymphony.xwork2.ActionContext;
import rate.controller.RateActionBase;
import rate.model.UserEntity;

import java.util.Map;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-6
 * Time: 下午1:39
 */
public class CreateAction extends RateActionBase {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public String getRefer_url() {
        return refer_url;
    }

    public void setRefer_url(String refer_url) {
        this.refer_url = refer_url;
    }

    private String refer_url;

    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        UserEntity user = UserEntity.authenticate(username, password);
        if (user != null) {
            session.put("user-uuid", user.getUuid());
            return SUCCESS;
        }
        else return ERROR;
    }
}
