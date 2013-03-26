package rate.controller.view;
import com.opensymphony.xwork2.ActionSupport;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.engine.view.GenerateStrategy.GenerateByImportTagStrategy;
import rate.engine.view.Generator;
import rate.model.SampleEntity;
import rate.model.ViewEntity;
import rate.util.HibernateUtil;

import java.util.List;

/**
 * Created by XianRan
 * Time: 下午12:39
 */
public class CreateAction extends RateActionBase {
    private ViewEntity view;

    public ViewEntity getView() {
        return view;
    }

    public void setView(ViewEntity view) {
        this.view = view;
    }

    private String importTag;

    public String getImportTag() {
        return importTag;
    }

    public void setImportTag(String importTag) {
        this.importTag = importTag.trim();
    }

    public String execute() throws Exception {
        if (importTag.equals("")) return "input";
        else {
            GenerateByImportTagStrategy strategy = new GenerateByImportTagStrategy();
            strategy.setImportTag(importTag);
            strategy.setDescription(view.getDescription());
            strategy.setViewName(view.getName());
            Generator generator = new Generator();
            generator.setGenerateStrategy(strategy);
            view = generator.generate();
            return SUCCESS;
        }
    }
}
