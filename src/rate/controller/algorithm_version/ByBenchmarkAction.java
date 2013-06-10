package rate.controller.algorithm_version;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.controller.RateActionBase;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-4
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class ByBenchmarkAction extends RateActionBase {

    private final static Logger logger = Logger.getLogger(ByBenchmarkAction.class);

    private String benchmarkUuid;

    public BenchmarkEntity getBenchmark() {
        return benchmark;
    }

    private BenchmarkEntity benchmark;

    public void setUuid(String uuid) {
        benchmarkUuid = uuid;
        benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", uuid)
                .list().get(0);
    }

    private Collection<AlgorithmVersionEntity> algorithmVersions;

    public Collection<AlgorithmVersionEntity> getAlgorithmVersions() {
        return algorithmVersions;
    }

    public String execute() throws Exception {
        if (!getIsUserSignedIn())
            return "eLogin";
        algorithmVersions = new ArrayList<AlgorithmVersionEntity>();

        algorithmVersions = session.createQuery("select version from AlgorithmVersionEntity version where version.algorithm.user.uuid=:uuid order by created desc ")
                .setParameter("uuid", getCurrentUser().getUuid()).setMaxResults(itemPerPage).list();

        Long cnt = (Long) session.createQuery("select count(*) from AlgorithmVersionEntity version where version.algorithm.user.uuid=:uuid")
                .setParameter("uuid", getCurrentUser().getUuid()).list().get(0);
        setNumOfItems(cnt);
        return SUCCESS;
    }

}
