package rate.controller.benchmark;

import rate.engine.benchmark.*;
import rate.model.BenchmarkEntity;
import rate.model.ViewEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

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

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setViewUuid(String viewUuid) {
        this.viewUuid = viewUuid;
        view = (ViewEntity)session.createQuery("from ViewEntity where uuid=:uuid").setParameter("uuid", viewUuid)
        .list().get(0);
        benchmark.setView(view);
    }

    int B4far;
    int B4frr;

    public int getB4far() {
        return B4far;
    }

    public void setB4far(int b4far) {
        B4far = b4far;
    }

    public int getB4frr() {
        return B4frr;
    }

    public void setB4frr(int b4frr) {
        B4frr = b4frr;
    }

    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    int classCount;
    int sampleCount;


    public String execute() throws Exception {
        session.beginTransaction();
        benchmark.setType(benchmark.getGenerator());
        session.save(benchmark);
        session.getTransaction().commit();
        session.close();

        DebugUtil.debug(benchmark.getGenerator());

        if (benchmark.getGenerator().equals("General")) {
            GeneralBenchmark generalBenchmark = new GeneralBenchmark();
            generalBenchmark.setClassCount(classCount);
            generalBenchmark.setSampleCount(sampleCount);
            generalBenchmark.setBenchmark(benchmark);
            benchmark = generalBenchmark.generate();
        }  else if (benchmark.getGenerator().equals("SLSB")) {
            SLSBBenchmark slsbBenchmark = new SLSBBenchmark();
            slsbBenchmark.setBenchmark(benchmark);
            slsbBenchmark.setClassCount(classCount);
            slsbBenchmark.setSampleCount(sampleCount);
            slsbBenchmark.setB4Frr(this.B4frr);
            slsbBenchmark.setB4Far(this.B4far);
            benchmark = slsbBenchmark.generate();
        }  else if (benchmark.getGenerator().equals("Custom")) {
            CustomBenchmark customBenchmark = new CustomBenchmark();
            customBenchmark.setBenchmark(benchmark);
            customBenchmark.setContent(content);
            benchmark = customBenchmark.generate();
        } else {
            return ERROR;
        }

        session = HibernateUtil.getSession();
        session.beginTransaction();
        DebugUtil.debug(benchmark.getType());
        session.update(benchmark);
        session.getTransaction().commit();
        return SUCCESS;
    }
}
