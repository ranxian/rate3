package rate.controller.benchmark;

import rate.engine.benchmark.generator.*;
import rate.engine.view.GenerateStrategy.AbstractGenerateStrategy;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.DebugUtil;

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
        benchmark.setView(view);
    }

    public void setBenchmarkType() {
        String generatorStr = benchmark.getGenerator();
        if (generatorStr.matches("(SMALL)|(MEDIUM)|((VERY_)?LARGE)|.*Imposter")) {
            benchmark.setType("General");
        } else if (generatorStr.equals("SLSB")) {
            benchmark.setType("SLSB");
        }
    }

    public String execute() throws Exception {
        session.beginTransaction();
        setBenchmarkType();
        session.save(benchmark);

        String generatorStr = benchmark.getGenerator();
        if (generatorStr.matches("(SMALL)|(MEDIUM)|((VERY_)?LARGE)")) {
            GeneralGenrator generator = new GeneralGenrator();
            generator.setBenchmark(benchmark);
            // This should depend on user's option
            generator.setScale(generatorStr);
            benchmark = generator.generate();
        } else if (generatorStr.equals("OneClassImposter")) {
            OneClassImposterGenerator generator = new OneClassImposterGenerator();
            generator.setBenchmark(benchmark);
            benchmark = generator.generate();
        } else {
            return ERROR;
//        } else if (generatorStr.equals("SLSB")) {
//            SLSBGenerator generator = new SLSBGenerator();
//            generator.setBenchmark(benchmark);
//            generator.setScale("SMALL");
//            benchmark = generator.generate();
        }

        session.update(benchmark);
        session.getTransaction().commit();
        return SUCCESS;
    }
}
