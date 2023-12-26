package Vista;

import com.kwabenaberko.newsapilib.models.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface Vista {
    void mostrarArticulos(ArrayList<Article> articulos, String consulta) throws ExecutionException, InterruptedException;
}
