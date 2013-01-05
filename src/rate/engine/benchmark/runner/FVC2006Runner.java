package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 12-12-31
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class FVC2006Runner extends AbstractRunner {

    private static final Logger logger = Logger.getLogger(FVC2006Runner.class);

    public FVC2006Runner() {
    }

    public void run() throws Exception {

        logger.debug(String.format("%s is invoked", this.getClass().getName()));

        if (getBenchmark()==null || getAlgorithmVersion()==null) {
            throw new RunnerException("No benchmark or algorithmVersion specified");
        }

        if (! getBenchmark().getProtocol().equals(getAlgorithmVersion().getAlgorithmByAlgorithmUuid().getProtocol())) {
            throw new RunnerException("Protocol does not match.");
        }

        this.prepare();

        List<String> lines = FileUtils.readLines(new File(getBenchmark().filePath()));

        File resultFile = new File(task.getResultFilePath());
        PrintWriter resultPw = new PrintWriter(resultFile);

        boolean enrollFailed = false;

        for (int i=0; i<lines.size(); i+=2) {
            String line1 = StringUtils.strip(lines.get(i));
            String line2 = StringUtils.strip(lines.get(i+1));

            int result;

            if (line1.startsWith("E")) {
                // try to delete the template file generated last time
                if (new File(templateFilePath).exists()) {
                    FileUtils.forceDelete(new File(templateFilePath));
                }
            }
            else if (line1.startsWith("M")) {
                if (!(new File(templateFilePath).exists()) || enrollFailed) {
                    resultPw.println(String.format("%s -1", line1));
                }
            }

            String cmd = this.genCmdFromLines(line1, line2);
            logger.trace(cmd);
            Process process = Runtime.getRuntime().exec(String.format("cmd /c %s", cmd));
            process.waitFor();

            String outputLine = StringUtils.strip(RateConfig.getLastLine(outputFilePath));

            if (line1.startsWith("E")) {
                try {
                    String rs[] = StringUtils.split(outputLine, " ");
                    if (rs[0].equals(imageFilePath)
                            && rs[1].equals(templateFilePath)
                            && rs[2].equals("OK")
                            ) {
                        resultPw.println(String.format("%s 0", line1));
                        enrollFailed = false;
                    }
                    else throw new Exception(String.format("Enroll failed %s", line2));
                }
                catch (Exception ex) {
                    logger.error(ex);
                    resultPw.println(String.format("%s -1", line1));
                    enrollFailed = true;
                }
            }
        }

    }

    private final Session session = HibernateUtil.getSession();
    private TaskEntity task;

    private String outputFilePath;
    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String templateFilePath;
    private String imageFilePath;

    private void prepare() throws IOException {
        session.beginTransaction();

        task = new TaskEntity();
        task.setAlgorithmVersionByAlgorithmVersionUuid(getAlgorithmVersion());
        task.setBenchmarkByBenchmarkUuid(getBenchmark());
        session.save(task);

        FileUtils.forceMkdir(new File(task.getTempDirPath()));
        FileUtils.forceMkdir(new File(task.getDirPath()));

        outputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");

        enrollExeFilePath = FilenameUtils.concat(getAlgorithmVersion().dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(getAlgorithmVersion().dirPath(), "match.exe");

        templateFilePath = FilenameUtils.concat(task.getTempDirPath(), "template");

        task.setCreated(HibernateUtil.getCurrentTimestamp());
        session.save(task);
        session.getTransaction().commit();
    }

    private String genCmdFromLines(String line1, String line2) {
        String exe = "";
        String output = "";
        if (line1.startsWith("E")) {
            exe = enrollExeFilePath;
        }
        else if (line1.startsWith("M")) {
            exe = matchExeFilePath;
        }
        else {
            return "";
        }


        // enroll.exe image template config output
        // match.exe image template config output
        // config is 0 now
        List<String> list = new ArrayList<String>();
        list.add(exe);
        imageFilePath = FilenameUtils.concat(RateConfig.getSampleRootDir(), StringUtils.strip(line2));
        list.add(imageFilePath);
        list.add(templateFilePath);
        list.add("0");
        list.add(outputFilePath);

        return StringUtils.join(list, " ");
    }
}
