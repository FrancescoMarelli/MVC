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

public class PieChart extends JFrame {
    Map<String, Integer> articlesPerSource;
    ArrayList<Article> articulos;


    public PieChart(ArrayList<Article> articles) {
        super("Gráfico de Tarta");
        this.articulos = articles;

        this.articlesPerSource = countArticlesPerSource(articles);
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

        return chart;
    }
}
