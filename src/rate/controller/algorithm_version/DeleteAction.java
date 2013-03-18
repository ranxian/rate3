package rate.controller.algorithm_version;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;

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
        if (!isAuthor()) return "eNotAuthor";

        session.beginTransaction();
        this.algorithm = algorithmVersion.getAlgorithm();
        FileUtils.deleteDirectory(new File(algorithmVersion.dirPath()));
        session.delete(algorithmVersion);
        session.getTransaction().commit();
        return SUCCESS;
    }
}
