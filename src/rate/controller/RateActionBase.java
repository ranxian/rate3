package rate.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.AlgorithmEntity;
import rate.model.UserEntity;
import rate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Yuankai
 * Date: 13-1-14
 * Time: 上午2:21
 * To change this template use File | Settings | File Templates.
 */
public class RateActionBase extends ActionSupport implements SessionAware {

    private static final Logger logger = Logger.getLogger(RateActionBase.class);
    protected final Session hsession = HibernateUtil.getSession();
    private Map session;
    public void setSession(Map session) {
        this.session = session;
    }

    public UserEntity getCurrentUser() {
        Map session = ActionContext.getContext().getSession();
        if (session.get("user-uuid") == null) return null;
        Query q = HibernateUtil.getSession().createQuery("from UserEntity where uuid=:uuid");

        q.setParameter("uuid", session.get("user-uuid"));
        List<UserEntity> list = q.list();
        UserEntity user = list.get(0);
        return user;
    }

    public boolean getIsUserSignedIn() {
        Map hsession = ActionContext.getContext().getSession();
        return (hsession.get("user-uuid") != null);
    }

    public int getFirstResult() {
        return (getPage()-1)*itemPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getNextPageUrl() {
        return getPageUrl(page + 1);
    }

    public String getPrevPageUrl() {
        return getPageUrl(page - 1);
    }

    private boolean hasPage(int pageIn) {
        if (pageIn<=0 || (numOfPages>0 && pageIn>numOfPages))
            return false;
        else return true;
    }

    public boolean getIsCurrentPageValid() {
        return hasPage(page);
    }

    public String getPageUrl(int pageIn) {
        if (!hasPage(pageIn))
            return null;

        String uri = ServletActionContext.getRequest().getRequestURI();
        String parameter =  ServletActionContext.getRequest().getQueryString();
        if (parameter==null) parameter = "";
        logger.trace(String.format("Request uri [%s]", uri));
        logger.trace(String.format("Request parameter [%s]", parameter));

        String newURL = null;

        String parameters[] = parameter.split("&");
        List<String>  newParameters = new ArrayList<String>();
        for (String p: parameters) {
            if (p.split("=")[0].equals("page")) {
                continue;
            }
            newParameters.add(p);
        }
        newParameters.add(String.format("page=%d", pageIn));
        String newParameter = StringUtils.join(newParameters, "&");

        return uri+"?"+newParameter;
    }

    protected int page=1;

    public int getNumOfPages() {
        return numOfPages;
    }

    protected int numOfItems;

    public void setNumOfItems(Long numOfPages) {
        this.numOfItems = numOfPages.intValue();
        this.numOfPages = (numOfItems+itemPerPage-1)/itemPerPage;
    }

    protected int numOfPages = -1;

    protected int itemPerPage = 10;
}
