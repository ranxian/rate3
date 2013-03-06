package rate.controller.view;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Query;
import rate.controller.RateActionBase;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:38
 */
public class    IndexAction extends RateActionBase {
    public List<ViewEntity> getViews() {
        return views;
    }

    public void setViews(List<ViewEntity> views) {
        this.views = views;
    }

    private List<ViewEntity> views;

    public String execute() throws Exception {
        Query q = HibernateUtil.getSession().createQuery("from ViewEntity order by generated desc");
        views = q.list();
        return SUCCESS;
    }
}
