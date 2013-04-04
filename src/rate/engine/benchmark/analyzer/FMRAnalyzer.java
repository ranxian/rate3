package rate.engine.benchmark.analyzer;

import org.apache.commons.lang.StringUtils;
import rate.util.RateConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-4
 * Time: 下午3:05
 */
public class FMRAnalyzer extends BasicAnalyzer {
    public FMRAnalyzer(String f1, String f2) {
        super(f1, f2);
    }

    public void analyze() throws Exception {
        super.analyze();
        BufferedReader imposterReader = new BufferedReader(new FileReader(inputFilePath));
        File fmrFile = new File(outputFilePath);
        fmrFile.createNewFile();
        PrintWriter fmrPw = new PrintWriter(fmrFile);

        int countOfLines = RateConfig.getCountOfLines(inputFilePath);
        int i=0;
        double p=-1, matchScore=0;
        while (true) {
            String line = imposterReader.readLine();
            if (line==null) break;

            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            matchScore = Double.parseDouble(rs[rs.length-1]);

            if (matchScore>p) {
                if (p!=-1)
                    fmrPw.println(String.format("%f %f", p, 1 - (double)i/countOfLines));
                p = matchScore;
            }
            i++;
        }
        fmrPw.println(String.format("%f 0", matchScore));
        fmrPw.close();
    }
}
