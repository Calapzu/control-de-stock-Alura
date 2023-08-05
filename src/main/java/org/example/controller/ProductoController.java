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
        this.productoDAO = new ProductoDAO(new ConectionFactory().recuperaConexion());
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
        ConectionFactory factory = new ConectionFactory();
        final Connection con = factory.recuperaConexion();

        try (con) {

            final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
                    + " NOMBRE = ? "
                    + ", DESCRIPCION = ? "
                    + ", CANTIDAD = ? "
                    + " WHERE ID = ? ");

            try (statement) {

                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);

                statement.execute();

                int updateCount = statement.getUpdateCount();

                System.out.println(id + " " + nombre + " " + descripcion);

                return updateCount;
            }
        }
    }

    public int eliminar(Integer id) throws SQLException {
        final Connection connection = new ConectionFactory().recuperaConexion();

        try (connection) {

            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

            try (preparedStatement) {

                preparedStatement.setInt(1, id);

                preparedStatement.execute();

                int updateCount = preparedStatement.getUpdateCount();

                return updateCount;
            }
        }
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
