package rate.controller.rank;

import rate.controller.RateActionBase;
import rate.model.BenchmarkEntity;

import java.util.Collection;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-6-11
 * Time: 下午4:18
 */
public class IndexAction extends RateActionBase {
    private Collection<BenchmarkEntity> benchmarks;

    public Collection<BenchmarkEntity> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(Collection<BenchmarkEntity> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public String execute() throws Exception {
        benchmarks = session.createQuery("from BenchmarkEntity order by created desc ").list();
        return SUCCESS;
    }
}
