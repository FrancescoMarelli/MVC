package Vista;
import Controlador.Controlador;
import com.kwabenaberko.newsapilib.models.Article;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LineChart extends JFrame implements Vista{
    private ArrayList<Article> articulos;
    private Map<String, Integer> articlesPerDay;
    private Controlador controlador;
    JPanel panelCabecera;
    JLabel labelCabecera;

    public LineChart(ArrayList<Article> articles) {
        super("Gráfico de Líneas");
        this.articulos = articles;
        this.articlesPerDay = countArticlesPerDay(articles);
    }
    public LineChart(Controlador controlador) {
        super("Gráfico de Líneas");
        this.controlador = controlador;
    }
    public void mostrarArticulos(ArrayList<Article> articulos, String consulta) {
        this.articulos = articulos;
        this.articlesPerDay = countArticlesPerDay(articulos);

        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        panelCabecera = crearPanelCabecera(consulta);


        // Configurar el JFrame
        setTitle("Gráfico de Lineas");
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

    JFreeChart createChart() {
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
        renderer.setLegendTextFont(0, new Font("SanSerif", Font.PLAIN, 10));

        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
        dateAxis.setTickLabelFont(new Font("SanSerif", Font.PLAIN, 10));

        // Cambiar el color del fondo y del borde del plot
        plot.setBackgroundPaint(new Color(74, 88, 130)); // Color del fondo
        plot.setOutlinePaint(new Color(74, 88, 130));   // Color del borde

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        int maxCount = articlesPerDay.values().stream().max(Integer::compareTo).get();
        yAxis.setRange(0, maxCount + 2);

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
