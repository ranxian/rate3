package rate.controller.view;

import com.opensymphony.xwork2.ActionSupport;
import rate.model.ViewEntity;

/**
 * Created by XianRan
 * Time: 下午9:16
 */
public class NewAction extends ActionSupport {
    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    ViewEntity view;

    public String execute() throws Exception {
        return SUCCESS;
    }
}
