package org.example.view;

import org.example.controller.CategoriaController;
import org.example.controller.ProductoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReporteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;

    private DefaultTableModel modelo;

    private CategoriaController categoriaController;
    private ProductoController productoController;

    public ReporteFrame(ControlDeStockFrame controlDeStockFrame) {

        this.categoriaController = new CategoriaController();
        this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);

        tablaReporte = new JTable();
        tablaReporte.setBounds(0, 0, 600, 400);
        container.add(tablaReporte);

        modelo = (DefaultTableModel) tablaReporte.getModel();
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");

        cargaReporte();

        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(controlDeStockFrame);
    }

    private void cargaReporte() {
        var contenido = categoriaController.cargaReporte();
        contenido.forEach(categoria -> {
            modelo
                    .addRow(new Object[]{categoria});

            var productos = this.productoController.listar(categoria);

            productos.forEach(producto -> modelo.addRow(
                    new Object[]{
                            "",
                            producto.getNombre(),
                            producto.getCantidad()
                    }
            ));
        });
    }

}
