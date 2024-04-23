package CasoIntegrador.GestionDeDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GestionDeDatos extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<Pareja> datos;

    public GestionDeDatos() {
        setTitle("Interfaz de Usuario");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        datos = new ArrayList<>();

        String[] columnNames = {"Entero 1", "Entero 2"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JButton addButton = new JButton("Añadir Pareja");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String entero1Str = JOptionPane.showInputDialog("Introduzca el primer entero:");
                String entero2Str = JOptionPane.showInputDialog("Introduzca el segundo entero:");

                try {
                    int entero1 = Integer.parseInt(entero1Str);
                    int entero2 = Integer.parseInt(entero2Str);
                    Pareja pareja = new Pareja(entero1, entero2);
                    datos.add(pareja);
                    actualizarTabla();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(GestionDeDatos.this, "Introduzca números enteros válidos.");
                }
            }
        });

        JButton deleteButton = new JButton("Eliminar Seleccionado");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    datos.remove(selectedRow);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(GestionDeDatos.this, "Seleccione la fila a eliminar.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Pareja pareja : datos) {
            Object[] rowData = {pareja.getEntero1(), pareja.getEntero2()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionDeDatos main = new GestionDeDatos();
            main.setVisible(true);
        });
    }
}

class Pareja {
    private int entero1;
    private int entero2;

    public Pareja(int entero1, int entero2) {
        this.entero1 = entero1;
        this.entero2 = entero2;
    }

    public int getEntero1() {
        return entero1;
    }

    public int getEntero2() {
        return entero2;
    }
}
