package Controlador;
import Vista.*;
import usuario.*;
import Vista.Vista;
import com.kwabenaberko.newsapilib.models.Article;
import modelo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Controlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista){

        this.modelo = modelo;
        this.vista = vista;
    }

    public Modelo getModelo(){
        return this.modelo;
    }

    public void hacerBusqueda(Vista vista) throws ExecutionException, InterruptedException {
        try {
            if(getModelo().getQueryName() != null && !getModelo().getQueryName().isEmpty())
                vista.mostrarArticulos(getModelo().getArticles(), getModelo().getQueryName());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setQuery() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Asignar Temática de Consulta");
        dialog.setSize(500, 200);
        dialog.setResizable(false);
        dialog.setLayout(new GridLayout(4, 1));

        // Agregar el JTextArea para ingresar la temática de la consulta
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Agregar el botón OK
        JButton okButton = new JButton("OK");
        okButton.setLayout(new FlowLayout(FlowLayout.CENTER));

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Realizar la búsqueda solo si se ha ingresado un tema de consulta
                if (!textArea.getText().isEmpty()) {
                    try {
                        modelo.setQueryName(textArea.getText());
                        hacerBusqueda(vista);
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
        // Cambiar el tipo de vista según la opción seleccionada en el JComboBox
        if ("Vista1".equals(tipoVista)) {
            this.vista = new NewsGUI();
            try {
                hacerBusqueda(vista);
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if ("Vista2".equals(tipoVista)) {
            this.vista = new Vista2();
            try {
                hacerBusqueda(vista);
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if ("DashBoard".equals(tipoVista)) {
            vista = new DashBoard();
            try {
                hacerBusqueda(vista);
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
