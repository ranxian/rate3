package rate.controller.chart;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-7
 * Time: 上午12:41
 * To change this template use File | Settings | File Templates.
 */
public class RocAction extends TaskChartActionBase {

    private static final double E = 1e-15;

    private void addROCCurve(XYSeriesCollection xySeriesCollection, String filePath, String xySeriesName, boolean isDET) throws Exception {
        List<String> lines = FileUtils.readLines(new File(filePath));
        XYSeries xySeries = new XYSeries(xySeriesName);

        for (String line: lines) {
            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            double fmr = Double.parseDouble(rs[0]);
            double fnmr = Double.parseDouble(rs[1]);

            if(fmr < E) fmr = E;
            if(fnmr < E) fnmr = E;
            if(fmr == 1.0) fmr = 1.0 - E;
            if(fnmr == 1.0) fnmr = 1.0 - E;

            if (isDET) xySeries.add(fmr, fnmr);
            else xySeries.add(fmr, 1 - fnmr);
        }

        xySeriesCollection.addSeries(xySeries);

    }

    public String execute() throws Exception {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

        addROCCurve(xySeriesCollection, fvc2006Task.getRocFilePath(), "ROC Curve", true);

        String labelX = "FMR", labelY = "FNMR";
        chart = ChartFactory.createXYLineChart(
                "", // title
                labelX, // x
                labelY, // y
                xySeriesCollection,
                PlotOrientation.VERTICAL, true,
                true,
                false
        );

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = (XYPlot) chart.getPlot();

        LogarithmicAxis xAxis = new LogarithmicAxis(labelX);
        xAxis.setRange(new Range(0.00001, 1));
        xAxis.setLog10TickLabelsFlag(true);
        plot.setDomainAxis(xAxis);

        LogarithmicAxis yAxis = new LogarithmicAxis(labelY);
        yAxis.setRange(new Range(0.001, 1));
        yAxis.setLog10TickLabelsFlag(true);
        plot.setRangeAxis(yAxis);

        Title t = chart.getSubtitle(0);
        t.setBorder(0, 0, 0, 0);

        return SUCCESS;
    }
}
