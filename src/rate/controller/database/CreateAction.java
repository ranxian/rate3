package rate.controller.database;

import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import org.omg.PortableInterceptor.SUCCESSFUL;
import rate.engine.database.ZipImorter;
import rate.engine.view.GenerateStrategy.GenerateByImportTagStrategy;
import rate.engine.view.Generator;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

/**
 * User: Xian Ran (xranthoar@gmail.com)
 * Date: 13-1-19
 * Time: 下午5:44
 */
public class CreateAction extends ActionSupport {
    public String getImportTag() {
        return importTag;
    }

    public void setImportTag(String importTag) {
        this.importTag = importTag;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    private String importTag;
    private String zipPath;
    private String type;
    private ZipImorter importer = new ZipImorter();
    private ViewEntity view = new ViewEntity();

    public String execute() throws Exception {
        GenerateByImportTagStrategy strategy = new GenerateByImportTagStrategy();
        strategy.setImportTag(importTag);
        Generator generator = new Generator();
        generator.setGenerateStrategy(strategy);

        zipPath = RateConfig.getZipRootDir() + "/" + zipPath;

        System.out.println(zipPath);
        // Import zip, add samples
        this.importer.main(importTag, type, zipPath);

        // Generate views
        view = generator.generate();
        return SUCCESS;
    }
}
