package rate.view;
import rate.model.UserEntity;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-5
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailAction {
    public String execute() throws Exception {
        return "success";
    }

    public String getName() {
        return "this is a test";
    }

    private String uuid;
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String s) {
        uuid = s;
    }

}
