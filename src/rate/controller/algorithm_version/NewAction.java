package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class NewAction extends AlgorithmVersionActionBase {

    private static final Logger logger = Logger.getLogger(NewAction.class);

    public String execute() {
        if (!isAuthor()) return "eNotAuthor";
        return SUCCESS;
    }
}
