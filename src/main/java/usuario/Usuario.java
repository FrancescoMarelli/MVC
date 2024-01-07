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
    private Controlador controlador;

    public Usuario(String name, Controlador controlador) {
        this.name = name;
        this.controlador = controlador;
        setQuery();
    }

    public String getName() {
        return name;
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
                getControlador().hacerBusqueda();
    }

}
