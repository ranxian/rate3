package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class DeleteAction extends AlgorithmVersionActionBase {

    private static final Logger logger = Logger.getLogger(DeleteAction.class);

    public String execute() throws Exception {
        session.beginTransaction();
        this.algorithm = algorithmVersion.getAlgorithmByAlgorithmUuid();
        FileUtils.deleteDirectory(new File(algorithmVersion.dirPath()));
        session.delete(algorithmVersion);
        session.getTransaction().commit();
        return SUCCESS;
    }
}
