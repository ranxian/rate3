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

    private final Session session = HibernateUtil.getSession();

    private String outputFilePath;
    private String enrollExeFilePath;
    private String matchExeFilePath;
    private String templateFilePath;
    private String imageFilePath;

    public FVC2006Runner() {
    }

    public void run() throws Exception {
        logger.debug(String.format("%s invoked with task [%s]", this.getClass().getName(), task.getUuid()));

        if (! benchmark.getProtocol().equals(algorithmVersion.getAlgorithmByAlgorithmUuid().getProtocol())) {
            throw new RunnerException("Protocol does not match.");
        }

        this.prepare();

        List<String> lines = FileUtils.readLines(new File(benchmark.filePath()));

        String a = StringUtils.join(lines, " ");

        File resultFile = new File(task.getResultFilePath());

        resultFile.createNewFile();
        PrintWriter resultPw = new PrintWriter(resultFile);

        logger.debug(String.format("OutputFilePath [%s]", outputFilePath));
        logger.debug(String.format("ResultFilePath [%s]", task.getResultFilePath()));

        boolean enrollFailed = false;

        logger.trace(String.format("Begin to run [%s] commands", lines.size()/2));
        for (int i=0; i<lines.size(); i+=2) {

            String line1 = StringUtils.strip(lines.get(i));
            String line2 = StringUtils.strip(lines.get(i + 1));

            logger.trace(line1);

            if (line1.startsWith("E")) {
                // try to delete the template file generated last time
                if (new File(templateFilePath).exists()) {
                    new File(templateFilePath).delete();
                }
            }
            else if (line1.startsWith("M")) {
                if (!(new File(templateFilePath).exists()) || enrollFailed) {
                    resultPw.println(String.format("%s -1", line1));
                }
            }

            String cmd = this.genCmdFromLines(line1, line2);
//            logger.trace("Run command ["+cmd+"]");

            Process process = Runtime.getRuntime().exec(String.format("%s", cmd));
            process.waitFor();

            String outputLine = StringUtils.strip(RateConfig.getLastLine(outputFilePath));

            if (line1.startsWith("E")) {
                try {
//                    logger.trace(outputLine);
                    String rs[] = StringUtils.split(outputLine, " ");
                    if (rs[0].equals(imageFilePath)
                            && rs[1].equals(templateFilePath)
                            && rs[2].equals("OK")
                            ) {
                        resultPw.println(String.format("%s 0", line1));
                        enrollFailed = false;
                        resultPw.flush();
                    }
                    else throw new Exception(String.format("Enroll failed %s", line2));
                }
                catch (Exception ex) {
                    logger.error("", ex);
                    resultPw.println(String.format("%s -1", line1));
                    enrollFailed = true;
                }
            }
            else if (line1.startsWith("M")) {
                try {
                    String rs[] = StringUtils.split(outputLine, " ");
                    if (rs[0].equals(imageFilePath)
                            && rs[1].equals(templateFilePath)
                            && rs[2].equals("OK")
                            ) {
                        double matchScore = Double.parseDouble(rs[3]);
                        resultPw.println(String.format("%s 0 %s", line1, String.valueOf(matchScore)));
                        resultPw.flush();
                    }
                }
                catch (Exception ex) {
                    logger.error("", ex);
                    resultPw.println(String.format("%s -1", line1));
                }
            }
        }
        logger.trace("commands finished");

        task.setFinished(HibernateUtil.getCurrentTimestamp());
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();

        this.cleanUp();
    }


    protected void prepare() throws Exception {
        super.prepare();

        outputFilePath = FilenameUtils.concat(task.getTempDirPath(), "output.txt");

        enrollExeFilePath = FilenameUtils.concat(algorithmVersion.dirPath(), "enroll.exe");
        matchExeFilePath = FilenameUtils.concat(algorithmVersion.dirPath(), "match.exe");

        templateFilePath = FilenameUtils.concat(task.getTempDirPath(), "template");
    }

    private String genCmdFromLines(String line1, String line2) {
        String exe = "";
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
