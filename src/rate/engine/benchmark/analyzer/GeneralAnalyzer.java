package rate.engine.benchmark.analyzer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.engine.benchmark.runner.AbstractRunner;
import rate.engine.benchmark.runner.FVC2006Runner;
import rate.engine.benchmark.runner.PKURATERunner;
import rate.engine.benchmark.runner.RunnerException;
import rate.engine.task.GeneralTask;
import rate.model.SampleEntity;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;
import rate.util.RateConfig;

import java.io.*;
import java.util.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-5
 * Time: 下午4:53
 */
public class GeneralAnalyzer extends Analyzer implements Comparator<String> {
    private GeneralTask generalTask;
    private static final Logger logger = Logger.getLogger(GeneralAnalyzer.class);
    private static final Session session = HibernateUtil.getSession();

    public void setTask(TaskEntity task) throws Exception {
        this.generalTask = new GeneralTask(task);
    }

    public void analyze() throws Exception {
        prepare();

        splitAndSortResult();

        analyzeFMR(generalTask.getImposterFilePath(), generalTask.getFmrFilePath());

        analyzeFNMR(generalTask.getGenuineFilePath(), generalTask.getFnmrFilePath());

        analyzeErrorRates();

        analyzeROC(generalTask.getFmrFilePath(), generalTask.getFnmrFilePath(), generalTask.getRocFilePath());

        calcBadResult();
    }

    private void splitAndSortResult() throws Exception {
//        logger.trace(String.format("Result file [%s]", resultFilePath));
//        logger.trace(String.format("Genuine file [%s]", genuineFilePath));
//        logger.trace(String.format("Imposter file [%s]", imposterFilePath));

        // split and store in List<String>
        BufferedReader resultReader = new BufferedReader(new FileReader(generalTask.getResultFilePath()));
        List<String> genuineList = new ArrayList<String>();
        List<String> imposterList = new ArrayList<String>();
        while (true) {
            String line = resultReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);

//            logger.trace(String.format("splitting: [%s]", line));

            String rs[] = line.split(" ");
            if (rs.length == 4) continue;
            if ((!rs[2].equals("G")) && (!rs[2].equals("I"))) throw new Exception("Genuine or Imposter not known in result.txt");

            String sample1Uuid = rs[0], sample2Uuid = rs[1], type = rs[2];
            double matchScore = Double.parseDouble(rs[4]);
            String newLine = String.format("%s %s %f", sample1Uuid, sample2Uuid, matchScore);
            if (type.equals("G")) {
                genuineList.add(newLine);
            }
            else {
                imposterList.add(newLine);
            }
        }
        resultReader.close();

        // sort results
        Collections.sort(genuineList, this);
        Collections.sort(imposterList, this);

        // write to genuine.txt and imposter.txt
        File genuineFile = new File(generalTask.getGenuineFilePath());
        File imposterFile = new File(generalTask.getImposterFilePath());
        File revImposterFile = new File(generalTask.getRevImposterPath());
        genuineFile.createNewFile();
        imposterFile.createNewFile();
        revImposterFile.createNewFile();
        PrintWriter genuinePw = new PrintWriter(genuineFile);
        PrintWriter imposterPw = new PrintWriter(imposterFile);
        PrintWriter revImposterPw = new PrintWriter(revImposterFile);
        for (String line: genuineList) {
            genuinePw.println(line);
        }
        for (String line: imposterList) {
            imposterPw.println(line);
        }
        for (int i = imposterList.size() - 1; i >= imposterList.size() - 20 && i >= 1; i--) {
            revImposterPw.println(imposterList.get(i));
        }
        genuinePw.close();
        imposterPw.close();
        revImposterPw.close();
    }

    public int compare(String s1, String s2) {
        String ss1[] = s1.split(" ");
        String ss2[] = s2.split(" ");
        double d1 = Double.parseDouble(ss1[ss1.length-1]);
        double d2 = Double.parseDouble(ss2[ss2.length-1]);
        return (d1<d2 ? -1 : (d1==d2 ? 0 : 1));
    }

    private void calcBadResult() throws Exception{
        logger.debug("Begin Calculate bad result");
        List<String> genuineList = new ArrayList<String>();
        List<String> imposterList = new ArrayList<String>();

        BufferedReader genuineRd = new BufferedReader(new FileReader(generalTask.getGenuineFilePath()));
        BufferedReader imposterRd = new BufferedReader(new FileReader(generalTask.getRevImposterPath()));

        int count =10;
        while (count != 0 && genuineRd.ready()) {
            count--;
            genuineList.add(genuineRd.readLine());
            new File(generalTask.getLogPathByTypeNumber("genuine", String.valueOf(10-count))).createNewFile();
        }
        count = 10;
        while (count != 0 && imposterRd.ready()) {
            count--;
            imposterList.add(imposterRd.readLine());
            new File(generalTask.getLogPathByTypeNumber("imposter", String.valueOf(10-count))).createNewFile();
        }

        genuineRd.close();
        imposterRd.close();

        int i;
        logger.debug("Begin calc genuine bad result");
        for (i = 0; i < 10 && i < genuineList.size(); i++) {
            String line = genuineList.get(i);
            String info[] = line.split(" ");
            //String s1 = info[0], s2 = info[1];
            // 分布式下怎么处理 log 还是一个问题，暂时关掉这个功能
            //String log = genLog(s1, s2);

            PrintWriter fileWriter = new PrintWriter(generalTask.getLogPathByTypeNumber("genuine", String.valueOf(i + 1)));
            fileWriter.println(line);
            //fileWriter.println(log);
            fileWriter.close();
        }
        logger.debug("Begin calc imposter bad result");
        for (i = 0; i < imposterList.size() && i < 10; i++) {
            String line = imposterList.get(i);
            String info[] = line.split(" ");
            //String  s1 = info[0], s2 = info[1];
            // String log = genLog(s1, s2);
            PrintWriter fileWriter = new PrintWriter(generalTask.getLogPathByTypeNumber("imposter", String.valueOf(imposterList.size() - i)));
            fileWriter.println(line);
            //fileWriter.println(log);
            fileWriter.close();
        }
        logger.debug("bad result generated successfully");
    }

    private void analyzeErrorRates() throws Exception {
//        logger.trace(findFMRonFNMR(0.05));
        double FMR100 = findFMRonFNMR(0.01);
        double FMR1000 = findFMRonFNMR(0.001);
        double zeroFMR = findFMRonFNMR(0);
        double zeroFNMR = findFNMRonFMR(0);
        String EERline = analyzeEER(generalTask.getFmrFilePath(), generalTask.getFnmrFilePath());
        String[] args = EERline.split(" ");
        double EER = Double.parseDouble(args[0]);
        double EER_l = Double.parseDouble(args[1]);
        double EER_h = Double.parseDouble(args[2]);
        logger.trace(String.format("FMR100 %f", FMR100));
        logger.trace(String.format("FMR1000 %f", FMR1000));
        logger.trace(String.format("zeroFMR %f", zeroFMR));
        logger.trace(String.format("zeroFNMR %f", zeroFNMR));
        logger.trace(String.format("EER_l %f", EER_l));
        logger.trace(String.format("EER_h %f", EER_h));

        PrintWriter errorRatePw = new PrintWriter(new File(generalTask.getErrorRateFilePath()));
        errorRatePw.println(String.format("%f %f %f", EER, EER_l, EER_h));
        errorRatePw.println(FMR100);
        errorRatePw.println(FMR1000);
        errorRatePw.println(zeroFMR);
        errorRatePw.println(zeroFNMR);
        errorRatePw.close();
    }

    private double findFMRonFNMR(double fnmr) throws Exception {
        BufferedReader fnmrReader = new BufferedReader(new FileReader(generalTask.getFnmrFilePath()));
        String line;
        double threshold = 0, errorRate = 0;
        while ((line=fnmrReader.readLine())!=null) {
            String rs[] = StringUtils.strip(line).split(" ");
            threshold = Double.parseDouble(rs[0]);
            errorRate = Double.parseDouble(rs[1]);

            if (errorRate>=fnmr) break;
        }
        if (errorRate<fnmr) {
            threshold = 1;
        }

        fnmrReader.close();

        return getFMRonThreshold(threshold);
    }

    private double findFNMRonFMR(double fmr) throws Exception {
        BufferedReader fmrReader = new BufferedReader(new FileReader(generalTask.getFmrFilePath()));
        String line;
        double threshold = 0, errorRate = 0;
        while ((line=fmrReader.readLine())!=null) {
            String rs[] = StringUtils.strip(line).split(" ");
            threshold = Double.parseDouble(rs[0]);
            errorRate = Double.parseDouble(rs[1]);

            if (errorRate<=fmr) break;
        }
        if (errorRate>fmr) {
            threshold = 1;
        }

        fmrReader.close();

        return getFNMRonThreshold(threshold);
    }

    private double getFMRonThreshold(double thresholdIn) throws Exception {
        return getErrorRateOnThreshold(thresholdIn, generalTask.getFmrFilePath());
    }

    private double getFNMRonThreshold(double thresholdIn) throws Exception {
        return getErrorRateOnThreshold(thresholdIn, generalTask.getFnmrFilePath());
    }

    private double getErrorRateOnThreshold(double thresholdIn, String filePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        double threshold = 0, errorRate = 0;
        while ((line=reader.readLine())!=null) {
            String rs[] = StringUtils.strip(line).split(" ");
            threshold = Double.parseDouble(rs[0]);
            errorRate = Double.parseDouble(rs[1]);


            if (threshold>=thresholdIn) break;
        }
        reader.close();
        return errorRate;
    }


    private String genLog(String uuid1, String uuid2) throws Exception {
        logger.trace(String.format("Generate log for sample %s and %s", uuid1, uuid2));
        String protocal = generalTask.getTask().getAlgorithmVersion().getAlgorithm().getProtocol();

        if (protocal.equals("FVC2006")) {
            FVC2006Runner runner = new FVC2006Runner();
            return runner.genLog(uuid1, uuid2);
        } else if (protocal.equals("PKURATE")) {
            PKURATERunner runner = new PKURATERunner();
            runner.setTask(generalTask.getTask());
            return runner.genLog(uuid1, uuid2);
        } else {
            throw new Exception("Protocal not supported");
        }
    }

    private void prepare() throws Exception {
        File genuineResultPath = new File(generalTask.getGenuineResultPath());
        File imposterResultPath = new File(generalTask.getImposterResultPath());

        FileUtils.forceMkdir(genuineResultPath);
        FileUtils.forceMkdir(imposterResultPath);

        File resultFile = new File(generalTask.getResultFilePath());
        resultFile.createNewFile();
    }
}
