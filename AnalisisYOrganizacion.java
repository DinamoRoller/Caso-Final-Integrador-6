package CasoIntegrador.AnalisisYOrganizacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class AnalisisYOrganizacion extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<Venta> ventas;

    public AnalisisYOrganizacion() {
        setTitle("Análisis de Registros de Ventas");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ventas = new ArrayList<>();

        String[] columnNames = {"Producto", "Cantidad", "Precio Unitario"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JButton addVentaButton = new JButton("Agregar Venta");
        JButton sortByNameButton = new JButton("Ordenar por Producto");
        JButton sortByQuantityButton = new JButton("Ordenar por Cantidad");
        JButton sortByPriceButton = new JButton("Ordenar por Precio");

        addVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Nombre del Producto:");
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));
                int precio = Integer.parseInt(JOptionPane.showInputDialog("Precio:"));

                Venta nuevaVenta = new Venta(nombre, cantidad, precio);
                ventas.add(nuevaVenta);

                Object[] rowData = {nuevaVenta.getNombre(), nuevaVenta.getCantidad(), nuevaVenta.getPrecio()};
                tableModel.addRow(rowData);
            }
        });

        sortByNameButton.addActionListener(e -> {
            ventas.sort(Comparator.comparing(Venta::getNombre));
            actualizarTabla();
        });

        sortByQuantityButton.addActionListener(e -> {
            ventas.sort(Comparator.comparingInt(Venta::getCantidad));
            actualizarTabla();
        });

        sortByPriceButton.addActionListener(e -> {
            ventas.sort(Comparator.comparingInt(Venta::getPrecio));
            actualizarTabla();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(addVentaButton);
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByQuantityButton);
        buttonPanel.add(sortByPriceButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Venta venta : ventas) {
            Object[] rowData = {venta.getNombre(), venta.getCantidad(), venta.getPrecio()};
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnalisisYOrganizacion main = new AnalisisYOrganizacion();
            main.setVisible(true);
        });
    }
}

class Venta {
    private String nombre;
    private int cantidad;
    private int precio;

    public Venta(String nombre, int cantidad, int precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecio() {
        return precio;
    }
}
