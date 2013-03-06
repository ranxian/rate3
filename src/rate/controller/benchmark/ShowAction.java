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
        Collection<AlgorithmVersionEntity> algorithmVersions = hsession.createQuery("from AlgorithmVersionEntity where algorithm.protocol=:protocol order by created desc")
                .setParameter("protocol", benchmark.getProtocol())
                .setMaxResults(itemPerPage)
                .list();

        return algorithmVersions;
    }

    public String execute() {
        return SUCCESS;
    }
}
