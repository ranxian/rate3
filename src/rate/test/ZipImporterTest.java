package rate.test;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import rate.engine.database.ZipImorter;
import rate.engine.view.GenerateStrategy.AbstractGenerateStrategy;
import rate.engine.view.GenerateStrategy.GenerateAllStrategy;
import rate.engine.view.GenerateStrategy.GenerateByImportTagStrategy;
import rate.engine.view.Generator;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-1-17
 * Time: 下午4:11
 */
public class ZipImporterTest {
    private static ZipImorter importer = new ZipImorter();
    private static Session session = HibernateUtil.getSession();

    public static void main(String[] args) throws Exception {
        // Generate view
        ViewEntity view = new ViewEntity();
        GenerateByImportTagStrategy strategy = new GenerateByImportTagStrategy();
        strategy.setImportTag("TEST");
        Generator generator = new Generator();
        generator.setGenerateStrategy(strategy);

        String zipPath = RateConfig.getRootDir() + "/test/testData.zip";
        System.out.print(zipPath);

        // Import zip, add samples to view
        importer.main("testTag2", "FINGERVEIN", zipPath);

        view = generator.generate();
    }
}
