package usuario;

import Controlador.Controlador;
import Vista.*;
import modelo.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        dialog.setLayout(new BorderLayout(10, 10)); // Añadido espacio entre los componentes

        // Panel para la temática de la consulta
        JPanel queryPanel = new JPanel(new BorderLayout());
        queryPanel.setBorder(new EmptyBorder(10, 10, 0, 10)); // Márgen superior
        queryPanel.add(new JLabel("Introduce la temática de la consulta:"), BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setColumns(50); // Hacer el JTextArea más ancho
        queryPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel para seleccionar el tipo de vista
        JPanel typePanel = new JPanel(new BorderLayout());
        typePanel.setBorder(new EmptyBorder(10, 10, 0, 10)); // Márgen superior
        typePanel.add(new JLabel("Seleccionar Tipo de Vista: "), BorderLayout.NORTH);

        JComboBox<String> tipoVistaComboBox = new JComboBox<>(new String[]{"Vista1", "Vista2", "Vista3"});
        tipoVistaComboBox.setPreferredSize(new Dimension(100, 10)); // Hacer el JComboBox más pequeño
        tipoVistaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarTipoVista((String) tipoVistaComboBox.getSelectedItem());
            }
        });

        typePanel.add(tipoVistaComboBox, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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

                dialog.dispose(); // Cerrar el diálogo después de hacer la búsqueda
            }
        });
        buttonPanel.add(okButton);

        // Agregar paneles al diálogo
        dialog.add(queryPanel, BorderLayout.NORTH);
        dialog.add(typePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Centra el diálogo en la pantalla
        dialog.setLocationRelativeTo(null);

        // Muestra el cuadro de diálogo
        dialog.setVisible(true);
    }

    private void cambiarTipoVista(String tipoVista) {
        // Cambiar el tipo de vista según la opción seleccionada en el JComboBox
        switch (tipoVista) {
            case "Vista1":
                this.vista = new Vista2();
                break;
            case "Vista2":
                this.vista = new NewsGUI();
                break;
            case "Vista3":
                this.vista = new Vista3();
                break;
        }

        try {
            buscar();
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void buscar() throws InterruptedException, ExecutionException {
        getControlador().hacerBusqueda(getVista(), getQuery());
    }
}
