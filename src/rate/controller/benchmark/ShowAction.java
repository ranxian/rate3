package rate.controller.benchmark;

import rate.model.AlgorithmVersionEntity;

import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 下午12:40
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends BenchmarkActionBase {

    public Collection<AlgorithmVersionEntity> getAlgorithmVersions() {
        Collection<AlgorithmVersionEntity> algorithmVersions = session.createQuery("from AlgorithmVersionEntity where algorithmByAlgorithmUuid.protocol=:protocol order by created desc")
                .setParameter("protocol", benchmark.getProtocol()).list();

        return algorithmVersions;
    }

    public String execute() {
        return SUCCESS;
    }
}
