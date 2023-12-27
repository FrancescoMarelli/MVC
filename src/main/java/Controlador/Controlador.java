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
    private Usuario usuario;

    public Controlador(Usuario usuario) throws ExecutionException, InterruptedException {
        this.usuario = usuario;
        try {
            usuario.getVista().mostrarArticulos(usuario.getModelo().getArticles("bitcoin"), usuario.getModelo().getQueryName());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
