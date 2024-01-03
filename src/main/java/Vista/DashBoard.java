package Vista;

import com.kwabenaberko.newsapilib.models.Article;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DashBoard extends JFrame implements Vista{

    private ArrayList<Article> articulos;


    public void mostrarArticulos(ArrayList<Article> articulos, String consulta) throws ExecutionException, InterruptedException {
        this.articulos = articulos;

        // Crear la gráfica de líneas
        SwingUtilities.invokeLater(() -> new LineChart(articulos));
    }

}
