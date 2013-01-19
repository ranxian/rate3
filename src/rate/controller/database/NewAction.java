package rate.controller.database;

import com.opensymphony.xwork2.ActionSupport;
import rate.engine.database.ZipImorter;

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
    public String execute() throws Exception {
        return SUCCESS;
    }
}
