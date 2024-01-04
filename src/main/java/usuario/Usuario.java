package usuario;
import Controlador.Controlador;
import  Vista.*;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class Usuario {
    private String name;
    private Vista vista;
    private Controlador controlador;

    public Usuario(String name, Controlador controlador) {
        this.name = name;
        this.controlador = controlador;
        this.vista = new Vista2();
        setQuery();
    }

    public String getName() {
        return name;
    }


    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public Vista getVista() {
        return vista;
    }

    public Controlador getControlador() {
        return controlador;
    }


    public void setQuery() {
        controlador.setQuery();
    }

    public void buscar() throws InterruptedException, ExecutionException {
        // Verificar si se ha ingresado un tema de consulta
            // Verificar si se ha seleccionado una vista
                getControlador().hacerBusqueda(getVista());
    }

}
