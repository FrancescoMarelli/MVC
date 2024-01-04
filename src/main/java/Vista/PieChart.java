package Vista;

import com.kwabenaberko.newsapilib.models.Article;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PieChart extends JFrame{
    Map<String, Integer> articlesPerSource;
    ArrayList<Article> articulos;


    public PieChart(ArrayList<Article> articles) {
        super("Gráfico de Tarta");
        this.articulos = articles;
        this.articlesPerSource = countArticlesPerSource(articles);
/*        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 390));

        setContentPane(chartPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);*/
    }


    private Map<String, Integer> countArticlesPerSource(ArrayList<Article> articles) {
        Map<String, Integer> articlesPerSource = new HashMap<>();

        for (Article article : articles) {
            Source source = article.getSource();
            if (source != null) {
                String sourceName = source.getName();
                articlesPerSource.put(sourceName, articlesPerSource.getOrDefault(sourceName, 0) + 1);
            }
        }

        return articlesPerSource;
    }

     JFreeChart createChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        articlesPerSource.forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Artículos por Fuente",
                dataset,
                true,
                true,
                false
        );
         chart.getPlot().setBackgroundPaint(new Color(74, 88, 130)); // Ejemplo de color gris claro

         return chart;
    }
}
