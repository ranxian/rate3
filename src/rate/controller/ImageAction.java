package rate.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import rate.model.SampleEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-24
 * Time: 下午6:54
 */
public class ImageAction extends RateActionBase {
    private String uuid;
    private SampleEntity sample;

    public void setUuid(String uuid) {
        this.uuid = uuid;
        sample = (SampleEntity) HibernateUtil.getSession().createQuery("from SampleEntity where uuid=:uuid").setParameter("uuid", uuid)
                .list().get(0);
    }

    public InputStream getImgStream() throws Exception {
        InputStream stream = new FileInputStream(sample.getFilePath());
        return new FileInputStream(sample.getFilePath());
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
}
