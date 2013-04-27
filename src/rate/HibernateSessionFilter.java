package rate;

import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-4-25
 * Time: PM10:53
 * To change this template use File | Settings | File Templates.
 */
public class HibernateSessionFilter implements Filter {
    public void init(FilterConfig filterConfig) {

    }

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } finally {
            rate.util.HibernateUtil.closeSession();
        }
    }
}
