package Vista;

import com.kwabenaberko.newsapilib.models.Article;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BarChart extends JFrame implements Vista{
    private ArrayList<Article> articulos;
    private Map<String, Integer> authorsPerSource;

    public BarChart(ArrayList<Article> articles) {
        super("Gráfico de Barras");
        this.articulos = articles;
        this.authorsPerSource = countAuthorsPerSource(articles);

    }

    @Override
    public void mostrarArticulos(ArrayList<Article> articulos, String consulta) throws ExecutionException, InterruptedException {
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));

        setContentPane(chartPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Map<String, Integer> countAuthorsPerSource(ArrayList<Article> articles) {
        Map<String, Integer> authorsPerSource = new HashMap<>();

        for (Article article : articles) {
            if (article.getSource() == null) continue; // Ignorar artículos sin fuente (source
            if (article.getAuthor() == null) continue; // Ignorar artículos sin autor
            String source = article.getSource().getName();
            int authorsCount = article.getAuthor().split(",").length; // Contar autores separados por coma
            authorsPerSource.put(source, authorsPerSource.getOrDefault(source, 0) + authorsCount);
        }

        return authorsPerSource;
    }

     JFreeChart createChart() {
        CategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Autores por Fuente",
                "Fuente",
                "Número de Autores",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Personalizar el gráfico de barras
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        return chart;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        authorsPerSource.forEach((source, authorCount) -> {
            dataset.addValue(authorCount, "Autores", source);
        });

        return dataset;
    }

}
