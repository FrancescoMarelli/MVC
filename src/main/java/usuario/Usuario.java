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
    private String query;

    public Usuario(String name, Controlador controlador) {
        this.name = name;
        this.controlador = controlador;
        this.vista = new Vista2();
        setQuery();
    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
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
        JDialog dialog = new JDialog();
        dialog.setTitle("Asignar Temática de Consulta");
        dialog.setSize(500, 200);
        dialog.setLayout(new GridLayout(4, 1));

        // Agregar el JTextArea para ingresar la temática de la consulta
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Agregar el botón OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query = textArea.getText();
                textArea.setText("");

                try {
                    buscar();
                } catch (ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Agregar el JComboBox para seleccionar el tipo de vista
        JComboBox<String> tipoVistaComboBox = new JComboBox<>(new String[]{"Vista1", "Vista2"});
        tipoVistaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarTipoVista((String) tipoVistaComboBox.getSelectedItem());
            }
        });

        dialog.add(new JLabel("Introduce la temática de la consulta:"));
        dialog.add(textArea);
        dialog.add(new JLabel("Seleccionar Tipo de Vista: "));
        dialog.add(tipoVistaComboBox);
        dialog.add(okButton);

        // Centra el diálogo en la pantalla
        dialog.setLocationRelativeTo(null);


        // Muestra el cuadro de diálogo
        dialog.setVisible(true);
    }
    private void cambiarTipoVista(String tipoVista) {
        // Cambiar el tipo de vista según la opción seleccionada en el JComboBox
        if ("Vista1".equals(tipoVista)) {
            this.vista = new Vista2();
        } else if ("Vista2".equals(tipoVista)) {
            vista = new NewsGUI();
        }
    }

    public void buscar() throws InterruptedException, ExecutionException {
        getControlador().hacerBusqueda(getVista(), getQuery());
    }
}
