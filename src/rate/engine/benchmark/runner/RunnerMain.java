package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.engine.benchmark.analyzer.Analyzer;
import rate.engine.benchmark.analyzer.GeneralAnalyzer;
import rate.engine.benchmark.analyzer.SLSBAnalyzer;
import rate.model.AlgorithmEntity;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.DebugUtil;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
public class RunnerMain {

    private static final Logger logger = Logger.getLogger(RunnerMain.class);
    private static Session session = HibernateUtil.getSession();
    private static TaskEntity task;
    private static BenchmarkEntity benchmark;
    private static AlgorithmVersionEntity algorithmVersion;
    private static AlgorithmEntity algorithm;

    public static void setContext(String taskUuid) {
        Query query = session.createQuery("from TaskEntity where uuid = :uuid").setParameter("uuid", taskUuid);
        task = (TaskEntity)query.list().get(0);
        new File(task.getDirPath()).mkdir();
        benchmark = task.getBenchmark();
        algorithmVersion = task.getAlgorithmVersion();
        algorithm = algorithmVersion.getAlgorithm();
    }

    public static String buildDistCommand() {
        List<String> list = new ArrayList<String>();
        list.add(RateConfig.getPython());
        list.add(AbstractRunner.getDistEnginePath());
        list.add(rate.util.RateConfig.getDistEngineServer());
        list.add(benchmark.dirPath());
        list.add(task.getDirPath());
        list.add(algorithmVersion.getBareDir());
        logger.debug("algorithm bare url path is " + algorithmVersion.getBareDir());
        list.add("10000");
        list.add("50000000");
        DebugUtil.debug(list.toString());
        return StringUtils.join(list, " ");
    }

    public static void main(String args[]) {
        try {
            logger.debug("Started");

            // Set context(task, benchmark, algorithm)
            setContext(args[0].split(" ")[0]);

            // Get a runner according to algorithm protocal
            Class<?> runnerClass;
            if (algorithm.getProtocol().equals("PKURATE")) {
                runnerClass = PKURATERunner.class;
            } else if (algorithm.getProtocol().equals("FVC2006")) {
                runnerClass = FVC2006Runner.class;
            } else {
                throw new RunnerException("Algorithm Protocol not Valid.");
            }

            AbstractRunner runner = (AbstractRunner)runnerClass.newInstance();
            runner.setTask(task);
            // Run!
            if (RateConfig.isDistRun()) {
                String cmd = buildDistCommand();
                logger.trace("Run with distributed system command " + cmd);
                logger.trace(cmd);
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                logger.trace("process done");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                File taskDir = new File(task.getDirPath());
                if (!taskDir.exists()) {
                    taskDir.mkdirs();
                }
                File logFIle = new File(task.getDirPath() + "/log.txt");
                logFIle.createNewFile();
                File engineLogFile = new File(RateConfig.getLogDir() + File.separator + "engine.log");
                PrintWriter engineLog = new PrintWriter(new FileWriter(engineLogFile, true));
                PrintWriter writer = new PrintWriter(new FileWriter(logFIle));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    writer.println(line);
                    engineLog.println(new Date().getTime() + ": " + line);
                    writer.flush();
                    engineLog.flush();
                }
                process.waitFor();
                writer.close();
                reader.close();
                engineLog.close();
                FileUtils.moveFile(new File(FilenameUtils.concat(task.getDirPath(), "match_result.txt")), new File(task.getResultFilePath()));

                logger.trace("finished");
            } else {
                logger.info(String.format("Attempt to run task [%s] with runner [%s]", task.getUuid(), runnerClass.getName()));
                runner.run();
                logger.info(String.format("Run task [%s] with runner [%s] finished", task.getUuid(), runnerClass.getName()));
            }

            // Get an analyzer and analyze
            Class<?> analyzerClass;
            if (benchmark.getType().equals("SLSB")) {
                analyzerClass = SLSBAnalyzer.class;
            } else {
                analyzerClass = GeneralAnalyzer.class;
            }

            Analyzer analyzer = (Analyzer)analyzerClass.newInstance();
            analyzer.setTask(task);
            logger.info(String.format("Attempt to analyze task [%s] with analyzer [%s]", task.getUuid(), analyzerClass.getName()));
            analyzer.analyze();
            logger.info(String.format("Analyze task [%s] with analyzer [%s] finished", task.getUuid(), analyzerClass.getName()));


            // Update task state
            task.setFinished(HibernateUtil.getCurrentTimestamp());
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            session.close();

            logger.info("Exit");
        }
        catch (Exception ex) {
            logger.error("", ex);
        }
    }
}
