package CasoIntegrador.IndexacionYVisualizacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class IndexacionYVisualizacion extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public IndexacionYVisualizacion() {
        setTitle("Indexador de Archivos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Ruta");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton indexarButton = new JButton("Indexar");
        indexarButton.addActionListener(e -> indexarArchivos());
        JButton consultarRutasButton = new JButton("Consultar Rutas");
        consultarRutasButton.addActionListener(e -> mostrarRutas());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(indexarButton);
        buttonPanel.add(consultarRutasButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void indexarArchivos() {
        // Limpiar la tabla antes de indexar nuevos archivos
        tableModel.setRowCount(0);

        // Llamar a la función recursiva para indexar archivos
        indexarRecursivamente(new File(System.getProperty("user.dir")));
    }

    private void indexarRecursivamente(File directorio) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    // Si es un directorio, llamamos de manera recursiva
                    indexarRecursivamente(archivo);
                } else {
                    // Si es un archivo, lo añadimos a la tabla
                    tableModel.addRow(new Object[]{archivo.getName(), archivo.getAbsolutePath()});
                }
            }
        }
    }

    private void mostrarRutas() {
        // Limpiar la tabla antes de mostrar las rutas
        tableModel.setRowCount(0);

        // Recorrer todas las filas de la tabla y agregar las rutas a una lista
        ArrayList<String> rutas = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            rutas.add((String) tableModel.getValueAt(i, 1));
        }

        // Ordenar las rutas alfabéticamente
        Collections.sort(rutas);

        // Mostrar las rutas ordenadas en la tabla
        for (String ruta : rutas) {
            tableModel.addRow(new Object[]{"", ruta});
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IndexacionYVisualizacion ventana = new IndexacionYVisualizacion();
            ventana.setVisible(true);
        });
    }
}
