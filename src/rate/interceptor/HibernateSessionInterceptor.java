package rate.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-4-25
 * Time: PM10:10
 * To change this template use File | Settings | File Templates.
 */
public class HibernateSessionInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(final ActionInvocation invocation) throws Exception {
        try {
            DebugUtil.debug("Intercept");
            return invocation.invoke();
        } finally {
            HibernateUtil.closeSession();
            DebugUtil.debug("Session closed");
        }
    }
}
