package rate.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.benchmark.runner.RunnerInvoker;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;

/**
 * Created by XianRan
 * Time: 下午12:43
 */
public class RunnerAction extends RateActionBase {

    private final Session session = HibernateUtil.getSession();
    private final Logger logger = Logger.getLogger(RunnerAction.class);
    private String benchmarkUuid;
    private BenchmarkEntity benchmark;
    public void setBenchmarkUuid(String benchmarkUuid) {
        this.benchmarkUuid = benchmarkUuid;
        benchmark = (BenchmarkEntity)session.createQuery("from BenchmarkEntity where uuid=:uuid")
                .setParameter("uuid", benchmarkUuid)
                .list().get(0);
    }

    private String algorithmVersionUuid;
    private AlgorithmVersionEntity algorithmVersion;
    public void setAlgorithmVersionUuid(String algorithmVersionUuid) {
        this.algorithmVersionUuid = algorithmVersionUuid;
        algorithmVersion = (AlgorithmVersionEntity)session.createQuery("from AlgorithmVersionEntity where uuid=:uuid")
                .setParameter("uuid", algorithmVersionUuid)
                .list().get(0);
    }

    private TaskEntity task;
    public TaskEntity getTask() {
        return this.task;
    }

    public String execute() throws Exception {
        if (!getIsUserSignedIn()) {
            return "eLogin";
        }

        task = algorithmVersion.getTaskOn(benchmark);

        if (task != null) {
            return "historyTask";
        } else {
            DebugUtil.debug("Begin run");
            RunnerInvoker.run(benchmark, algorithmVersion);

            return SUCCESS;
        }
    }
}
