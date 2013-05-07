package rate.controller.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import rate.engine.task.SLSBTask;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-14
 * Time: 上午12:50
 */
public class SLSBFrrAction extends TaskChartActionBase  {
    public String execute() throws Exception {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        SLSBTask slsbTask = new SLSBTask(task);

        XYSeries lower = new XYSeries("Lower Bound");
        XYSeries upper = new XYSeries("Upper Bound");

        BufferedReader reader = new BufferedReader(new FileReader(slsbTask.getFrrResultPath()));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            String[] sp = line.split(" ");
            double x = Double.parseDouble(sp[0]);
            double l = Double.parseDouble(sp[1]);
            double u = Double.parseDouble(sp[2]);
            lower.add(x, u);
            upper.add(x, l);
        }
        reader.close();

        xySeriesCollection.addSeries(lower);
        xySeriesCollection.addSeries(upper);

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
