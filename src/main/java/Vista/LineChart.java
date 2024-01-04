package Vista;
import com.kwabenaberko.newsapilib.models.Article;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LineChart extends JFrame {
    ArrayList<Article> articulos;

    public LineChart(ArrayList<Article> articles) {
        super("Gráfico de Líneas");
        this.articulos = articles;

        Map<String, Integer> articlesPerDay = countArticlesPerDay(articles);

        JFreeChart chart = createChart(articlesPerDay);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));

        setContentPane(chartPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Map<String, Integer> countArticlesPerDay(ArrayList<Article> articles) {
        Map<String, Integer> articlesPerDay = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Article article : articles) {
            try {
                Date publishDate = dateFormat.parse(article.getPublishedAt());
                String dayKey = dayFormat.format(publishDate);

                articlesPerDay.put(dayKey, articlesPerDay.getOrDefault(dayKey, 0) + 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return articlesPerDay;
    }

    private JFreeChart createChart(Map<String, Integer> articlesPerDay) {
        TimeSeries timeSeries = new TimeSeries("Cantidad de Artículos");

        articlesPerDay.forEach((day, count) -> {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
                timeSeries.addOrUpdate(new org.jfree.data.time.Day(date), count);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        TimeSeriesCollection dataset = new TimeSeriesCollection(timeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Artículos por Día",
                "Fecha",
                "Cantidad de Artículos",
                dataset,
                false,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYSplineRenderer renderer = new XYSplineRenderer();
        plot.setRenderer(renderer);

        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));

        return chart;
    }
}
