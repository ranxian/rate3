package rate.engine.benchmark.analyzer;

import org.hibernate.Session;
import rate.util.HibernateUtil;
/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午3:02
 */
public class BasicAnalyzer {
    protected static final Session session = HibernateUtil.getSession();
    protected String inputFilePath;
    protected String outputFilePath;

    public BasicAnalyzer(String f1, String f2) {
        inputFilePath = f1;
        outputFilePath = f2;
    }

    public void analyze() throws Exception {

    }
}
