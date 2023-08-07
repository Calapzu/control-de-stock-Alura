package org.example.pruebas;

import org.example.controller.ProductoController;
import org.example.factory.ConectionFactory;
import org.example.modelo.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaDelete {
    public static void main(String[] args) throws SQLException {
        /*
        Connection connection = new ConectionFactory().recuperaConexion();

        Statement statement = connection.createStatement();

        boolean result = statement.execute("DELETE FROM PRODUCTO WHERE ID = 99");

        System.out.println( statement.getUpdateCount());
        */
        String nombre = "Yamson";
        String descripcion = "Calapzu";
        int cantidad = 20;
        int id = 5;

        var productoModificado = new Producto(id, nombre, descripcion, cantidad);

        ProductoController productoController = new ProductoController();
        productoController.eliminar(id);
    }
}
