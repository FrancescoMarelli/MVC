import Vista.NewsGUI;
import modelo.Modelo;
import Vista.*;
import Controlador.*;
import usuario.Usuario;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Modelo modelo = new Modelo();
        Vista vista = new NewsGUI();
        Usuario usuario1 = new Usuario("lOCO", modelo, vista);
        Controlador controlador = new Controlador(usuario1);
    }
}
