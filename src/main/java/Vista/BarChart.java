package Vista;

import Controlador.Controlador;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BarChart extends JFrame implements Vista{
    private ArrayList<Article> articulos;
    private Map<String, Integer> authorsPerSource;
    private ArrayList<String> sources;
    private Controlador controlador;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    JPanel panelCabecera;
    JLabel labelCabecera;

    public BarChart(ArrayList<Article> articles) {
        super("Gráfico de Barras");
        this.articulos = articles;
        this.sources = new ArrayList<>();
        this.authorsPerSource = countAuthorsPerSource(articles);
    }

    public BarChart(Controlador controlador) {
        super("Gráfico de Barras");
        this.controlador = controlador;
    }

    public void mostrarArticulos(ArrayList<Article> articulos, String consulta) {
        this.articulos = articulos;
        this.sources = new ArrayList<>();
        this.authorsPerSource = countAuthorsPerSource(articulos);


        chart = createChart();
        chartPanel = new ChartPanel(chart);
        panelCabecera = crearPanelCabecera(consulta);

        // Configurar el JFrame
        setTitle("Gráfico de Barras");
        setIconImage(new ImageIcon("src/main/img/nalogo.png").getImage());
        setMinimumSize(new Dimension(1000, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Agregar el panel de gráfico al JFrame
        getContentPane().add(chartPanel);
        getContentPane().add(panelCabecera, BorderLayout.NORTH);

        // Hacer visible el JFrame
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
         chart.getPlot().setBackgroundPaint(new Color(74, 88, 130)); // Ejemplo de color gris claro

         return chart;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        authorsPerSource.forEach((source, authorCount) -> {
            dataset.addValue(authorCount, source, "Autores");
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

    private JPanel crearPanelCabecera(String tituloPagina) {
        panelCabecera = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCabecera.setBackground(new Color(0, 102, 204)); // Azul
        labelCabecera = new JLabel(tituloPagina);
        labelCabecera.setFont(new Font("Arial", Font.BOLD, 24)); // Cambiado a Arial y tamaño 24
        labelCabecera.setForeground(Color.WHITE); // Texto blanco
        panelCabecera.add(labelCabecera);

        ImageIcon iconoLogo = getLogo();
        if (iconoLogo != null) {
            JLabel labelLogo = new JLabel(iconoLogo);
            panelCabecera.add(labelLogo);
        }

        JButton cambiarParametrosButton = new JButton("Cambiar Parámetros");
        cambiarParametrosButton.setFocusable(false);
        cambiarParametrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.setQuery();
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panelCabecera.add(cambiarParametrosButton);

        return panelCabecera;
    }
    ImageIcon getLogo() {
        try {
            ImageIcon originalIcon = new ImageIcon("src/main/img/logo.png");
            Image resizedImage = originalIcon.getImage().getScaledInstance(130, 35, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
