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
                // Realizar la búsqueda solo si se ha ingresado un tema de consulta
                if (!textArea.getText().isEmpty()) {
                    try {
                        query = textArea.getText();
                        buscar();
                    } catch (ExecutionException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    dialog.dispose(); // Cerrar el diálogo después de realizar la búsqueda
                } else {
                    // Mostrar un mensaje indicando que se debe escribir un tema de consulta
                    JOptionPane.showMessageDialog(null, "Por favor, escriba un tema de consulta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JComboBox<String> tipoVistaComboBox = new JComboBox<>(new String[]{"Vista1", "Vista2", "DashBoard"});
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
        SwingUtilities.invokeLater(() -> dialog.setVisible(true)); // Mostrar el diálogo en el hilo de despacho de eventos
    }


    private void cambiarTipoVista(String tipoVista) {
        vista = new NewsGUI();
        // Cambiar el tipo de vista según la opción seleccionada en el JComboBox
        if ("Vista1".equals(tipoVista)) {
            try {
                buscar();
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if ("Vista2".equals(tipoVista)) {
            this.vista = new Vista2();
            try {
                buscar();
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if ("DashBoard".equals(tipoVista)) {
            vista = new DashBoard();
            try {
                buscar();
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void buscar() throws InterruptedException, ExecutionException {
        // Verificar si se ha ingresado un tema de consulta
        if (getQuery() != null && !getQuery().isEmpty()) {
            // Verificar si se ha seleccionado una vista
                getControlador().hacerBusqueda(getVista(), getQuery());
        }
    }

}
