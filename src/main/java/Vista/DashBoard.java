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

        // Crear un contenedor para ambas gráficas
        JPanel chartsPanel = new JPanel(new BorderLayout());
        chartsPanel.setSize(800, 600);
        setResizable(false);

        // Establecer el tamaño deseado para las gráficas (puedes ajustar estos valores)
        int chartWidth = 400;
        int chartHeight = 300;

        // Configurar el tamaño de las gráficas
        ChartPanel pieChartPanel = new ChartPanel(pieChart.createChart());
        pieChartPanel.setPreferredSize(new Dimension(chartWidth+200, chartHeight+200));

        ChartPanel lineChartPanel = new ChartPanel(lineChart.createChart());
        lineChartPanel.setPreferredSize(new Dimension(chartWidth-100, chartHeight-200));

        chartsPanel.add(pieChartPanel, BorderLayout.NORTH);
        chartsPanel.add(lineChartPanel, BorderLayout.CENTER);

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
