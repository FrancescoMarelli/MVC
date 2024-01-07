import Vista.NewsGUI;
import modelo.Modelo;
import modelo.ModeloAPI;
import Vista.*;
import Controlador.*;
import usuario.Usuario;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Vista vista = new NewsGUI();
        Modelo modeloAPI = new ModeloAPI();
        Controlador controlador = new Controlador(modeloAPI, vista);
        Usuario usuario = new Usuario("Sergio", controlador);

        usuario.buscar();
    }
}



