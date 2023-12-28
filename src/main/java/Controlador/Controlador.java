package Controlador;
import usuario.*;
import Vista.Vista;
import com.kwabenaberko.newsapilib.models.Article;
import modelo.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Controlador {
    private Modelo modelo;

    public Controlador(Modelo modelo){
        this.modelo = modelo;
    }

    public Modelo getModelo(){
        return this.modelo;
    }

    public void hacerBusqueda(Vista vista, String query) throws ExecutionException, InterruptedException {
        try {
            vista.mostrarArticulos(getModelo().getArticles(query), getModelo().getQueryName());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
