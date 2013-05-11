package rate.engine.CLI;

import org.hibernate.Session;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-10
 * Time: AM11:23
 * To change this template use File | Settings | File Templates.
 */
public class ViewUtils extends BaseUtils {
    private static Session session;

    public static void list() {
        ratePrintln("Listing views");
        session = HibernateUtil.getSession();
        List<ViewEntity> views = session.createQuery("from ViewEntity").list();
        if (views.isEmpty()) {
            ratePrintln("No views.");
            return;
        }
        ratePrintln("Cnt: " + views.size());

        for (ViewEntity view : views) {
            String info = view.getUuid() + " " + view.getName();
            ratePrintln(info);
        }
    }
}
