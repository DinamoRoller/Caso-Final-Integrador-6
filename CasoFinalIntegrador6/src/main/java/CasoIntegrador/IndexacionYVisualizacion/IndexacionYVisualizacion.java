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
        tableModel.setRowCount(0);

        indexarRecursivamente(new File(System.getProperty("user.dir")));
    }

    private void indexarRecursivamente(File directorio) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    indexarRecursivamente(archivo);
                } else {
                    tableModel.addRow(new Object[]{archivo.getName(), archivo.getAbsolutePath()});
                }
            }
        }
    }

    private void mostrarRutas() {
        tableModel.setRowCount(0);

        ArrayList<String> rutas = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            rutas.add((String) tableModel.getValueAt(i, 1));
        }

        Collections.sort(rutas);
        
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
