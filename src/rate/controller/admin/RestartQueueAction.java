package rate.controller.admin;

import rate.controller.RateActionBase;

import java.io.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-6-6
 * Time: 下午9:47
 */
public class RestartQueueAction extends RateActionBase {
    public String passwd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String execute() throws Exception {
        String cmd = "sudo -S restart rabbitmq-server";
        Process process = Runtime.getRuntime().exec(cmd);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.println(passwd);
        writer.close();
        process.waitFor();
        return SUCCESS;
    }
}
