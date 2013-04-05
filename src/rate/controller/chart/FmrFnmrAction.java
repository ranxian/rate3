package rate.controller.chart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-9
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class FmrFnmrAction extends TaskChartActionBase {

    private XYSeries getXYSeries(String xySeriesName, String filePath) throws Exception {
        double x, y;
        XYSeries xySeries = new XYSeries(xySeriesName);
        List<String> lines = FileUtils.readLines(new File(filePath));

        for (String line: lines) {
            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            x = Double.parseDouble(rs[0]);
            y = Double.parseDouble(rs[1]);
            xySeries.add(y, x);
        }

        return xySeries;
    }
    public String execute() throws Exception {

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

        XYSeries xySeriesFMR = getXYSeries("FMR", GeneralTask.getFmrFilePath());
        xySeriesCollection.addSeries(xySeriesFMR);
        XYSeries xySeriesFNMR = getXYSeries("FNMR", GeneralTask.getFnmrFilePath());
        xySeriesCollection.addSeries(xySeriesFNMR);

        chart = ChartFactory.createXYLineChart(
                "", // title
                "Ratio", // x
                "Threshold", // y
                xySeriesCollection,
                PlotOrientation.HORIZONTAL, true,
                true,
                false
        );


        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setForegroundAlpha(0.6f);
        plot.getDomainAxis().setRange(new Range(0.0, 1.0));
        plot.getRangeAxis().setRange(0.0, 1.0);

        Title t = chart.getSubtitle(0);
        t.setBorder(0, 0, 0, 0);

        return SUCCESS;
    }
}
