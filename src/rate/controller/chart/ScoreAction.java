package rate.controller.chart;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.math.RandomUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-8
 * Time: 下午10:26
 * To change this template use File | Settings | File Templates.
 */
public class ScoreAction extends ActionSupport {

    public JFreeChart getChart() {
        return chart;
    }

    private JFreeChart chart;

    public String execute() {

        // chart creation logic...
        XYSeries dataSeries = new XYSeries(new Integer(1)); // pass a key for this serie
        for (int i = 0; i <= 100; i++) {
            dataSeries.add(i, RandomUtils.nextInt());
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection(dataSeries);

        ValueAxis xAxis = new NumberAxis("Raw Marks");
        ValueAxis yAxis = new NumberAxis("Moderated Marks");

        // set my chart variable
        chart =
                new JFreeChart( "Moderation Function", JFreeChart.DEFAULT_TITLE_FONT,
                        new XYPlot( xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES)),
                        false);
        chart.setBackgroundPaint(java.awt.Color.white);


        return SUCCESS;
    }
}
