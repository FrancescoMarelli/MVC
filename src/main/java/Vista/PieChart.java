package Vista;

import Controlador.Controlador;
import com.kwabenaberko.newsapilib.models.Article;

import com.kwabenaberko.newsapilib.models.Source;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieChart extends JFrame implements Vista{
    Map<String, Integer> articlesPerSource;
    ArrayList<Article> articulos;
    private Controlador controlador;
    JPanel panelCabecera;
    JLabel labelCabecera;


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
        panelCabecera = crearPanelCabecera(consulta);

        // Configurar el JFrame
        setTitle("Gráfico de Tarta");
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
