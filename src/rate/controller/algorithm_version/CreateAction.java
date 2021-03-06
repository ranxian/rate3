package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class CreateAction extends AlgorithmVersionActionBase {
    private final static Logger logger = Logger.getLogger(CreateAction.class);

    public void setEnrollExe(File enrollExe) {
        this.enrollExe = enrollExe;
    }

    private File enrollExe;

    public void setMatchExe(File matchExe) {
        this.matchExe = matchExe;
    }

    private File matchExe;


    public String execute() {
        try {
            session.beginTransaction();
            //this.algorithmVersion = new AlgorithmVersionEntity();
            algorithmVersion.setAlgorithm(this.algorithm);
            session.save(algorithmVersion);

            File dir = new File(algorithmVersion.dirPath());
            if (!dir.exists()) {
                logger.trace(dir.toString() + "does not exists, try mkdirs");
                dir.mkdirs();
                //FileUtils.forceMkdir(dir);
            }


            File dst = new File(FilenameUtils.concat(algorithmVersion.dirPath(), "enroll.exe"));
            FileUtils.copyFile(enrollExe, dst);
            dst = new File(FilenameUtils.concat(algorithmVersion.dirPath(), "match.exe"));
            FileUtils.copyFile(matchExe, dst);

            algorithm.setUpdated(HibernateUtil.getCurrentTimestamp());
            session.saveOrUpdate(algorithm);

            session.getTransaction().commit();
            return SUCCESS;
        }
        catch (IOException ex) {
            logger.error(ex);
            DebugUtil.debug("error happens");
            return ERROR;
        }
    }
}
