package usuario;
import Controlador.Controlador;
import  Vista.*;
import modelo.*;

import java.util.concurrent.ExecutionException;

public class Usuario {
    private String name;
    private Vista vista;
    private Controlador controlador;
    private String query;

    public Usuario(String name, Controlador controlador) {
        this.name = name;
        this.controlador = controlador;
        this.vista = new NewsGUI();
        setQuery();
    }

    public String getName() {
        return name;
    }
    public String getQuery() {
        return query;
    }

    public void setVista(Vista vista){
        this.vista = vista;
    }

    public Vista getVista() {
        return vista;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setQuery(){
        //llamar cuadro de texto para setear la tematica de consulta
        this.query = "Bitcoin";// de momento ponemos bitcoin
    }

    public void buscar() throws ExecutionException, InterruptedException {
        getControlador().hacerBusqueda(getVista(), getQuery());
    }
}
