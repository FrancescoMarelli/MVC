package Vista;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
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
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BarChart extends JFrame implements Vista{
    private ArrayList<Article> articulos;
    private Map<String, Integer> authorsPerSource;
    private ArrayList<String> sources;

    public BarChart(ArrayList<Article> articles) {
        super("Gráfico de Barras");
        this.articulos = articles;
        this.sources = new ArrayList<>();
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
            this.sources.add(source);
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

         // Obtener la lista de fuentes
         List<String> sourceList = new ArrayList<>(authorsPerSource.keySet());

         // Asignar un color diferente a cada barra (fuente)
         for (int i = 0; i < sourceList.size(); i++) {
             Paint color = getDistinctColor();
             plot.getRenderer().setSeriesPaint(i, color);
         }

         return chart;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        authorsPerSource.forEach((source, authorCount) -> {
            dataset.addValue(authorCount, "Autores", source);
        });

        return dataset;
    }

    private Paint getDistinctColor() {
        // Genera un color RGB aleatorio
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return new Color(r, g, b);
    }


}
