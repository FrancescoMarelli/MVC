package Controlador;

import Vista.Vista;
import com.kwabenaberko.newsapilib.models.Article;
import modelo.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Controlador {
    private Vista vista;
    private Modelo modelo;

    public Controlador(Modelo modelo, Vista vista) throws ExecutionException, InterruptedException {
        this.modelo = modelo;
        this.vista = vista;
        try {
            vista.mostrarArticulos(modelo.getArticles("bitcoin"), modelo.getQueryName());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
