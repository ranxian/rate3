package rate.test;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import rate.engine.benchmark.runner.AbstractRunner;
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
public class FVC2006WithRateRunner {
    public static void main(String args[]) throws Exception {
        String rateRunnerPath = AbstractRunner.getRateRunnerPath();
        String matchExePath = "G:\\RATE_ROOT\\algorithms\\1bf71dd1-c6f9-4f3a-b4c0-b28dba70ead5\\811007f1-2b78-43eb-af0d-69d95973747b\\match.exe";
        String enrollExePath = "G:\\RATE_ROOT\\algorithms\\1bf71dd1-c6f9-4f3a-b4c0-b28dba70ead5\\811007f1-2b78-43eb-af0d-69d95973747b\\enroll.exe";
        String enrollImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), "00101153\\V0\\00101153_V0_2013-03-17-18-50-18.bmp");
        String matchImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), "00101153\\V0\\00101153_V0_2013-03-18-01-34-54.bmp");

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
