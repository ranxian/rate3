package rate.controller.benchmark;

import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.UserAlgorithmEntity;
import rate.util.DebugUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 下午12:40
 * To change this template use File | Settings | File Templates.
 */
public class ShowAction extends BenchmarkActionBase {

    public List<AlgorithmVersionEntity> getAlgorithmVersions() {
        List<AlgorithmVersionEntity> algorithmVersions = new ArrayList<AlgorithmVersionEntity>();
        List<AlgorithmVersionEntity> allAlgorithmVersions = session.createQuery("from AlgorithmVersionEntity where algorithm.type=:type order by created desc")
                .setParameter("type", benchmark.getView().getType())
                .list();

        if (!getIsUserSignedIn()) return algorithmVersions;
        for (AlgorithmVersionEntity v : allAlgorithmVersions) {
            AlgorithmEntity a = v.getAlgorithm();

            if (a.getAuthor().getUuid().equals(getCurrentUser().getUuid())) {
                algorithmVersions.add(v);
            }
        }

        int cnt = algorithmVersions.size() <= itemPerPage ? algorithmVersions.size() : itemPerPage;
        return algorithmVersions.subList(0, cnt);
    }

    public String execute() {
        return SUCCESS;
    }
}
