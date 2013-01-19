package rate.controller.database;

import com.opensymphony.xwork2.ActionSupport;
import rate.engine.database.ZipImorter;

import java.util.Date;

/**
 * User: Xian Ran (xranthoar@gmail.com)
 * Date: 13-1-19
 * Time: 下午5:44
 */
public class NewAction extends ActionSupport {
    public String[] getZipFilePaths() throws Exception {
        return ZipImorter.getZipFilePath();
    }

    private String[] zipFilePaths;

    public String getCurrentTime() {
        return currentTime;
    }

    private String currentTime;

    public String execute() throws Exception {
        Date date = new Date();
        currentTime = date.toString();
        return SUCCESS;
    }
}
