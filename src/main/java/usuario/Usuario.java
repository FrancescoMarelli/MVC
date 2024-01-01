package usuario;
import Controlador.Controlador;
import  Vista.*;
import modelo.*;

import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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

    public void setQuery() {
        this.query = "Bitcoin";// por defecto

        // Crear un cuadro de texto para ingresar la consulta
        JTextField textField = new JTextField();
        Object[] message = {"Introduce la temática de la consulta:", textField};

        // Muestra el cuadro de diálogo y espera a que el usuario ingrese texto
        int option = JOptionPane.showOptionDialog(
                null,
                message,
                "Asignar Temática de Consulta",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        // Verifica si el usuario hizo clic en OK y actualiza la consulta
        if (option == JOptionPane.OK_OPTION) {
            this.query = textField.getText();
        }
    }

    public void buscar() throws ExecutionException, InterruptedException {
        getControlador().hacerBusqueda(getVista(), getQuery());
    }
}
