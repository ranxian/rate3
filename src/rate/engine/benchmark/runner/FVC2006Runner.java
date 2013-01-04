package rate.engine.benchmark.runner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.AlgorithmVersionEntity;
import rate.model.BenchmarkEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

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

        for (int i=0; i<lines.size(); i+=2) {
            String line1 = lines.get(i);
            String line2 = lines.get(i+1);

            if (line1.startsWith("E")) {
                // try to delete the template file generated last time
                if (new File(templateFilePath).exists()) {
                    FileUtils.forceDelete(new File(templateFilePath));
                }
            }
            else if (line1.startsWith("M")) {
                // TODO: if template not found
            }

            String cmd = this.genCmdFromLines(line1, line2);
            logger.trace(cmd);

            Process process = Runtime.getRuntime().exec(String.format("cmd /c %s", cmd));
            process.waitFor();
        }

    }

    private final Session session = HibernateUtil.getSession();
    private TaskEntity task;

    private String enrollOutputFilePath;
    private String matchOutputFilePath;
    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String templateFilePath;

    private void prepare() throws IOException {
        session.beginTransaction();

        task = new TaskEntity();
        task.setAlgorithmVersionByAlgorithmVersionUuid(getAlgorithmVersion());
        task.setBenchmarkByBenchmarkUuid(getBenchmark());
        session.save(task);

        FileUtils.forceMkdir(new File(task.tempDirPath()));
        FileUtils.forceMkdir(new File(task.dirPath()));

        enrollOutputFilePath = FilenameUtils.concat(task.tempDirPath(), "enrollOutput.txt");
        matchOutputFilePath = FilenameUtils.concat(task.tempDirPath(), "matchOutput.txt");
        //logger.debug(enrollOutputFilePath);
        //logger.debug(matchOutputFilePath);

        enrollExeFilePath = FilenameUtils.concat(getAlgorithmVersion().dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(getAlgorithmVersion().dirPath(), "match.exe");

        templateFilePath = FilenameUtils.concat(task.tempDirPath(), "template");

        task.setCreated(HibernateUtil.getCurrentTimestamp());
        session.save(task);
        session.getTransaction().commit();
    }

    private String genCmdFromLines(String line1, String line2) {
        String exe = "";
        String output = "";
        if (line1.startsWith("E")) {
            exe = enrollExeFilePath;
            output = enrollOutputFilePath;
        }
        else if (line1.startsWith("M")) {
            exe = matchExeFilePath;
            output = matchOutputFilePath;
        }
        else {
            return "";
        }

        // TODO: for debug perpose
        output = enrollOutputFilePath;

        // enroll.exe image template config output
        // match.exe image template config output
        // config is 0 now
        List<String> list = new ArrayList<String>();
        list.add(exe);
        String imageFilePath = FilenameUtils.concat(RateConfig.getSampleRootDir(), StringUtils.strip(line2));
        list.add(imageFilePath);
        list.add(templateFilePath);
        list.add("0");
        list.add(output);

        return StringUtils.join(list, " ");
    }
}
