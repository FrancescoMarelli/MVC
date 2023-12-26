import Vista.NewsGUI;
import modelo.Modelo;
import Vista.*;
import Controlador.*;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Modelo modelo = new Modelo();
        Vista vista = new NewsGUI();
        Controlador controlador = new Controlador(modelo, vista);

    }
}
