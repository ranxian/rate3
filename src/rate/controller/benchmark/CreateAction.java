package rate.controller.benchmark;

import rate.engine.benchmark.generator.AbstractGenerator;
import rate.engine.benchmark.generator.GeneralGenrator;
import rate.engine.view.GenerateStrategy.AbstractGenerateStrategy;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-26
 * Time: 下午12:04
 */
public class CreateAction extends BenchmarkActionBase {
    public void setBenchmark(BenchmarkEntity benchmark) {
        this.benchmark = benchmark;
    }

    private String viewUuid;

    private ViewEntity view;

    public String getViewUuid() {
        return viewUuid;
    }

    public void setViewUuid(String viewUuid) {
        this.viewUuid = viewUuid;
        view = (ViewEntity)session.createQuery("from ViewEntity where uuid=:uuid").setParameter("uuid", viewUuid)
        .list().get(0);
    }

    public String execute() throws Exception {
        String generatorStr = benchmark.getGenerator();
        if (generatorStr.startsWith("General-")) {
            GeneralGenrator generator = new GeneralGenrator();
            generator.setBenchmark(benchmark);
            // This should depend on user's option
            generator.setScale("SMALL");
            benchmark = generator.generate();
        }
        return SUCCESS;
    }
}