package Vista;

import Controlador.Controlador;
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

public class PieChart extends JFrame implements Vista{
    Map<String, Integer> articlesPerSource;
    ArrayList<Article> articulos;
    private Controlador controlador;


    public PieChart(ArrayList<Article> articles) {
        super("Gráfico de Tarta");
        this.articulos = articles;
        this.articlesPerSource = countArticlesPerSource(articles);
    }

    public PieChart(Controlador controlador) {
        this.controlador = controlador;
    }

    public void mostrarArticulos(ArrayList<Article> articulos, String consulta) {
        this.articulos = articulos;
        this.articlesPerSource = countArticlesPerSource(articulos);

        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);

        // Configurar el JFrame
        setTitle("Gráfico de Tarta");
        setIconImage(new ImageIcon("src/main/img/nalogo.png").getImage());
        setMinimumSize(new Dimension(1000, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Agregar el panel de gráfico al JFrame
        getContentPane().add(chartPanel);

        // Hacer visible el JFrame
        setVisible(true);

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
