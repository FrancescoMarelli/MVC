import Vista.NewsGUI;
import modelo.Modelo;
import Vista.*;
import Controlador.*;
import usuario.Usuario;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Modelo modelo = new Modelo();
        //Controlador controlador = new Controlador(new Modelo());
        Usuario usuario1 = new Usuario("lOCO", new Controlador(new Modelo()));

        usuario1.buscar();
    }
}
