package Controlador;
import Vista.*;
import Vista.Vista;
import modelo.Modelo;
import modelo.ModeloAPI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class Controlador {
    private Modelo modeloAPI;
    private Vista vista;
    private String vistaString;

    public Controlador(Modelo modeloAPI, Vista vista){
        this.modeloAPI = modeloAPI;
        this.vista = new DashBoard(this);
        this.vistaString = "DashBoard";
    }

    public Modelo getModelo(){
        return this.modeloAPI;
    }

    public void hacerBusqueda() throws ExecutionException, InterruptedException {
        try {
            if(getModelo().getQueryName() != null)
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
        dialog.setResizable(false);
        dialog.setSize(500, 200);
        dialog.setLayout(new BorderLayout(10, 10)); // Añadido espacio entre los componentes

        // Panel para la temática de la consulta
        JPanel queryPanel = new JPanel(new BorderLayout());
        queryPanel.setBorder(new EmptyBorder(10, 10, 0, 10)); // Márgen superior
        queryPanel.add(new JLabel("Introduce la temática de la consulta:"), BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        if(modeloAPI.getQueryName() != null){
            textArea.setText(modeloAPI.getQueryName());
        }
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setColumns(50); // Hacer el JTextArea más ancho
        queryPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel para seleccionar el tipo de vista
        JPanel typePanel = new JPanel(new BorderLayout());
        typePanel.setBorder(new EmptyBorder(10, 10, 0, 10)); // Márgen superior
        typePanel.add(new JLabel("Seleccionar Tipo de Vista: "), BorderLayout.NORTH);

        // Agregar el botón OK
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        okButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Realizar la búsqueda solo si se ha ingresado un tema de consulta
                if (!textArea.getText().isEmpty()) {
                    try {
                            cambiarTipoVista(vistaString);
                            modeloAPI.setQueryName(textArea.getText());
                            hacerBusqueda();
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

        JComboBox<String> tipoVistaComboBox = new JComboBox<>(new String[]{"Dashboard", "Vista Noticias", "Barras", "Tarta", "Lineas"});
        tipoVistaComboBox.setSelectedItem(vistaString);
        tipoVistaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cambiarTipoVista((String) tipoVistaComboBox.getSelectedItem());
                vistaString = (String) tipoVistaComboBox.getSelectedItem();
            }
        });
        typePanel.add(tipoVistaComboBox, BorderLayout.CENTER);

        dialog.add(queryPanel, BorderLayout.NORTH);
        dialog.add(typePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Centra el diálogo en la pantalla
        dialog.setLocationRelativeTo(null);

        // Centra el diálogo en la pantalla
        dialog.setLocationRelativeTo(null);
        dialog.setIconImage(new ImageIcon("src/main/img/nalogo.png").getImage());


        // Muestra el cuadro de diálogo
        SwingUtilities.invokeLater(() -> dialog.setVisible(true)); // Mostrar el diálogo en el hilo de despacho de eventos
    }

    private void cambiarTipoVista(String tipoVista) {
        // Verificar si ya existe una instancia de la vista
        if ("Dashboard".equals(tipoVista)) {
            vista = new DashBoard(this);
        } else if ("Vista Noticias".equals(tipoVista)) {
            vista = new VistaDefecto(this);
        } else if ("Barras".equals(tipoVista)) {
            vista = new BarChart(this);
        } else if ("Tarta".equals(tipoVista)) {
            vista = new PieChart(this);
        } else if ("Lineas".equals(tipoVista)) {
            vista = new LineChart(this);
        }
    }
}
