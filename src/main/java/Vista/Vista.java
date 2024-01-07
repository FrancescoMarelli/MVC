package Vista;

import com.kwabenaberko.newsapilib.models.Article;

import javax.swing.*;
import java.util.ArrayList;


import java.util.concurrent.ExecutionException;

public interface Vista {
    void mostrarArticulos(ArrayList<Article> articulos, String consulta) throws ExecutionException, InterruptedException;
}
