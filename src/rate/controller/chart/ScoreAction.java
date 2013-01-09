package rate.controller.chart;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Session;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import rate.engine.task.FVC2006Task;
import rate.model.TaskEntity;
import rate.util.HibernateUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: yyk
 * Date: 13-1-8
 * Time: 下午10:26
 * To change this template use File | Settings | File Templates.
 */
public class ScoreAction extends ActionSupport {

    private String taskUuid;
    private TaskEntity task;
    private FVC2006Task fvc2006Task;
    private final Session session = HibernateUtil.getSession();

    public void setTaskUuid(String taskUuid) throws Exception {
        this.taskUuid = taskUuid;
        this.task = (TaskEntity)session.createQuery("from TaskEntity where uuid = :uuid")
                .setParameter("uuid", taskUuid)
                .list().get(0);
        this.fvc2006Task = new FVC2006Task(task);
    }

    public JFreeChart getChart() {
        return chart;
    }

    private JFreeChart chart;

    private void addDistribution(XYSeriesCollection xySeriesCollection,
                                 String distributionName, String filePath, double interval)
            throws Exception {

        XYSeries xySeries = new XYSeries(distributionName);

        List<String> lines = FileUtils.readLines(new File(filePath));

        double top = interval;
        int count = 0;
        int totalCount = lines.size();
        for (String line: lines) {
            line = StringUtils.strip(line);
            String rs[] = line.split(" ");
            double score = Double.parseDouble(rs[rs.length-1]);

            while (score > top) {
                xySeries.add(top - interval/2, (double)count/totalCount);
                count = 0;
                top += interval;
            }
            count++;
        }
        xySeries.add(top - interval/2, (double)count/totalCount);

        xySeriesCollection.addSeries(xySeries);
    }


    public String execute() throws Exception {
        int column = 40;
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        double interval = 1.00/column;
        xySeriesCollection.setIntervalWidth(interval);

        addDistribution(xySeriesCollection, "Genuine", fvc2006Task.getGenuineFilePath(), interval);
        addDistribution(xySeriesCollection, "Imposter", fvc2006Task.getImposterFilePath(), interval);

        this.chart = ChartFactory.createHistogram(
                "", // title
                "Score", // x label
                "Percentage", // y label
                xySeriesCollection,
                PlotOrientation.VERTICAL, true,
                false,
                false
        );
        chart.setBackgroundPaint(Color.WHITE);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setForegroundAlpha(0.6f);
        plot.getDomainAxis().setRange(new Range(0.0, 1.0));
        plot.getRangeAxis().setRange(0.0, 1.0);

        Title t = chart.getSubtitle(0);
        t.setBorder(0, 0, 0, 0);

        return SUCCESS;
    }
}
