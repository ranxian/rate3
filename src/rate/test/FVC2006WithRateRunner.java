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
        String enrollImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), "bioverify/00921004_V3/2012-05-08_17-45-57.bmp");
        String matchImgPath = FilenameUtils.concat(RateConfig.getSampleRootDir(), "bioverify/00921004_V3/2011-09-28_08-12-29.bmp");

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
        File file = new File(tempOutputPath);
        file.delete();
        file = new File(templatePath);
        file.delete();
    }
}
