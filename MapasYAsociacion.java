package CasoIntegrador.MapasYAsociacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MapasYAsociacion extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private Map<Integer, String> mapaRelaciones;

    public MapasYAsociacion() {
        setTitle("Gestión de Relaciones");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mapaRelaciones = new HashMap<>();

        String[] columnNames = {"Clave", "Valor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JButton addPairButton = new JButton("Añadir Par");
        addPairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String claveStr = JOptionPane.showInputDialog("Introduce la clave:");
                String valor = JOptionPane.showInputDialog("Introduce el valor:");

                try {
                    int clave = Integer.parseInt(claveStr);
                    mapaRelaciones.put(clave, valor);
                    actualizarTabla();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MapasYAsociacion.this, "Introduce un número entero como clave.");
                }
            }
        });

        JTextField keyField = new JTextField(5);

        JButton retrieveButton = new JButton("Recuperar");
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyString = keyField.getText();
                try {
                    int key = Integer.parseInt(keyString);
                    if (mapaRelaciones.containsKey(key)) {
                        String value = mapaRelaciones.get(key);
                        JOptionPane.showMessageDialog(MapasYAsociacion.this, "El valor asociado a " + key + " es: " + value);
                    } else {
                        JOptionPane.showMessageDialog(MapasYAsociacion.this, "No se encontró ninguna asociación para la clave " + key);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MapasYAsociacion.this, "Introduce un número entero como clave.");
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Clave: "));
        inputPanel.add(keyField);
        inputPanel.add(retrieveButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(addPairButton, BorderLayout.NORTH);

        getContentPane().add(mainPanel);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Map.Entry<Integer, String> entry : mapaRelaciones.entrySet()) {
            Object[] rowData = {entry.getKey(), entry.getValue()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapasYAsociacion main = new MapasYAsociacion();
            main.setVisible(true);
        });
    }
}
