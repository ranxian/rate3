package rate.test;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import rate.engine.benchmark.runner.AbstractRunner;
import rate.model.AlgorithmVersionEntity;
import rate.model.SampleEntity;
import rate.util.DebugUtil;
import rate.util.RateConfig;

import java.io.File;
import java.util.Random;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-26
 * Time: 下午3:02
 */
public class FVC2006WithRateRunner extends BaseTest {
    public static void main(String args[]) throws Exception {
        String rateRunnerPath = AbstractRunner.getRateRunnerPath();
        String matchExePath = algorithmVersion.dirPath() + "/match.exe";
        String enrollExePath = algorithmVersion.dirPath() + "/enroll.exe";
        SampleEntity sample1 = (SampleEntity)session.createQuery("from SampleEntity").list().get(0);
        SampleEntity sample2 = (SampleEntity)session.createQuery("from SampleEntity").list().get(1);
        String enrollImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), sample1.getFilePath());
        String matchImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), sample2.getFilePath());

        Random random = new Random();
        String tempOutputPath = FilenameUtils.concat(RateConfig.getTempRootDir(), random.nextInt() + ".txt");
        String templatePath = FilenameUtils.concat(RateConfig.getTempRootDir(), random.nextInt() + "template");
        DebugUtil.debug(tempOutputPath);
        DebugUtil.debug(templatePath);
        String stderrPath = FilenameUtils.concat(RateConfig.getTempRootDir(), random.nextInt() + "stderr.txt");
        String stdoutPath = FilenameUtils.concat(RateConfig.getTempRootDir(), random.nextInt() + "stdout.txt");
        String purfPath = FilenameUtils.concat(RateConfig.getTempRootDir(), random.nextInt() + "purf.txt");

        String enrollArgs = enrollImgPath + " " + templatePath + " 0 " + tempOutputPath;
        String matchArgs = matchImgPath + " " + templatePath + " 0 " + tempOutputPath;
        String matchCmds[] = {rateRunnerPath, "3000", "52428800", stdoutPath, stderrPath, purfPath, matchExePath, matchArgs};
        String enrollCmds[] = {rateRunnerPath, "3000", "52428800", stdoutPath, stderrPath, purfPath, enrollExePath, enrollArgs};
        DebugUtil.debug("start enroll");
        Process enroll = Runtime.getRuntime().exec(enrollCmds);
        enroll.waitFor();
        DebugUtil.debug("enroll finished, start match");
        Process match = Runtime.getRuntime().exec(matchCmds);
        match.waitFor();
        DebugUtil.debug("match finished");

        new File(tempOutputPath).delete();
        new File(templatePath).delete();
        new File(stderrPath).delete();
        new File(stdoutPath).delete();
        new File(purfPath).delete();
    }
}
