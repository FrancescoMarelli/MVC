package Vista;

import com.kwabenaberko.newsapilib.models.Article;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DashBoard extends JFrame implements Vista {

    private ArrayList<Article> articulos;

    public void mostrarArticulos(ArrayList<Article> articulos, String consulta) throws ExecutionException, InterruptedException {
        this.articulos = articulos;

        LineChart lineChart = new LineChart(articulos);
        PieChart pieChart = new PieChart(articulos);
        BarChart barChart = new BarChart(articulos);

        // Crear un contenedor para ambas gráficas
        JPanel chartsPanel = new JPanel(new BorderLayout()); // Cambiado a BorderLayout
        chartsPanel.setBackground(new Color(74, 88, 130)); // Color de fondo principal
        //setResizable(false);

        // Configurar el tamaño de las gráficas
        ChartPanel pieChartPanel = new ChartPanel(pieChart.createChart());
        ChartPanel lineChartPanel = new ChartPanel(lineChart.createChart());
        ChartPanel barChartPanel = new ChartPanel(barChart.createChart());

        // Configurar el tamaño deseado para cada gráfico
        int chartWidth = 500;
        int chartHeight = 400;

        // Establecer el tamaño preferido para cada gráfico
        pieChartPanel.setPreferredSize(new Dimension(chartWidth, chartHeight));
        barChartPanel.setPreferredSize(new Dimension(chartWidth, chartHeight));

        // Agregar las gráficas al panel con la disposición deseada
        // Agregar las gráficas al panel con la disposición deseada
        JPanel topChartsPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Añadido espacio entre gráficos
        topChartsPanel.add(pieChartPanel);
        topChartsPanel.add(barChartPanel);
        topChartsPanel.setBackground(new Color(74, 88, 130)); // Color de fondo del panel superior

        // Crear un nuevo panel para la fila inferior y agregar el LineChart
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(lineChartPanel, BorderLayout.CENTER);
        bottomPanel.setBackground(new Color(74, 88, 130)); // Color de fondo del panel inferior

        // Agregar los paneles al contenedor principal
        chartsPanel.add(topChartsPanel, BorderLayout.NORTH);
        chartsPanel.add(bottomPanel, BorderLayout.CENTER);

        // Añadir espacio adicional entre los gráficos y los bordes de la ventana
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Configurar la ventana principal
        setTitle("Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Hacer la ventana de pantalla completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Agregar el panel de gráficas a la ventana
        getContentPane().add(chartsPanel);

        // Hacer visible la ventana
        setVisible(true);
    }
}
