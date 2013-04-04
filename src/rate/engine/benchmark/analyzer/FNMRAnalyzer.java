package rate.engine.benchmark.analyzer;

import org.apache.commons.lang3.StringUtils;
import rate.util.RateConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午3:10
 */
public class FNMRAnalyzer extends BasicAnalyzer {
    public FNMRAnalyzer(String in, String out) {
        super(in, out);
    }

    public void analyze() throws Exception {
        super.analyze();
        BufferedReader genuineReader = new BufferedReader(new FileReader(inputFilePath));
        File fnmrFile = new File(outputFilePath);
        fnmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fnmrFile);

        int countOfLines = RateConfig.getCountOfLines(inputFilePath);
        int i=0;
        double p=-1, matchScore=0;
        while (true) {
            String line = genuineReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            matchScore = Double.parseDouble(rs[rs.length-1]);

            if (matchScore>p) {
                if (p!=-1)
                    fmrPw.println(String.format("%f %f", p, (double)i/countOfLines));
                p = matchScore;
            }
            i++;
        }
        fmrPw.println(String.format("%f 0", matchScore));
        fmrPw.close();
    }
}
