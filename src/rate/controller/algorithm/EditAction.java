package rate.controller.algorithm;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:40
 */
public class EditAction extends AlgorithmActionBase {
    public String execute() throws Exception {
        if (!isAuthor()) return "eNotAuthor";

        return SUCCESS;
    }
}
