package org.example.controller;

import org.example.factory.ConectionFactory;
import org.example.modelo.Producto;
import org.example.dao.ProductoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        var factory = new ConectionFactory();
        this.productoDAO = new ProductoDAO(factory.recuperaConexion());
    }

    public int modificar(Producto producto){
       return productoDAO.modificar(producto);
    }

    public int eliminar(Integer id) {
        return productoDAO.eliminar(id);
    }

    public List<Producto> listar()  {
        /*Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "root"
        );*/
        return productoDAO.listar();

    }

    public void guardar(Producto producto){
        /*
        String nombre = producto.getNombre();
        String descripcion = producto.getDescripcion();
        int cantidad = producto.getCantidad();
        Integer maximaCantidad = 50;
        */
        productoDAO.guardar(producto);
    }
}
