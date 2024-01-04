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
        JPanel chartsPanel = new JPanel(new GridLayout(2, 1)); // Cambiado a 2 filas y 1 columna
        setResizable(false);

        // Configurar el tamaño de las gráficas
        ChartPanel pieChartPanel = new ChartPanel(pieChart.createChart());
        ChartPanel lineChartPanel = new ChartPanel(lineChart.createChart());
        ChartPanel barChartPanel = new ChartPanel(barChart.createChart());

        // Configurar el tamaño deseado para cada gráfico
        int chartWidth = 400;
        int chartHeight = 300;

        // Establecer el tamaño preferido para cada gráfico
        pieChartPanel.setPreferredSize(new Dimension(chartWidth, chartHeight));
        lineChartPanel.setPreferredSize(new Dimension(chartWidth, chartHeight));
        barChartPanel.setPreferredSize(new Dimension(chartWidth*2, chartHeight * 2)); // Doble altura para el BarChart

        // Agregar las gráficas al panel con la disposición deseada
        chartsPanel.add(pieChartPanel);
        chartsPanel.add(lineChartPanel);

        // Crear un nuevo panel para la fila inferior y agregar el BarChart
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(barChartPanel, BorderLayout.CENTER);
        chartsPanel.add(bottomPanel);

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
