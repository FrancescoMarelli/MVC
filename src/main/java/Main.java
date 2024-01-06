import Vista.NewsGUI;
import modelo.Modelo;
import Vista.*;
import Controlador.*;
import usuario.Usuario;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Vista vista = new NewsGUI();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo, vista);
        Usuario usuario = new Usuario("Sergio", controlador);

        usuario.buscar();
    }
}
