package org.example.pruebas;

import org.example.factory.ConectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaUpdate {
    public static void main(String[] args) throws SQLException {
        String nombre = "Yamson";
        String descripcion = "Yamson";
        int cantidad = 1;
        int id = 5;

        ConectionFactory factory = new ConectionFactory();
        Connection con = factory.recuperaConexion();
        Statement statement = con.createStatement();
        statement.execute("UPDATE PRODUCTO SET "
                + " NOMBRE = '" + nombre + "'"
                + ", DESCRIPCION = '" + descripcion + "'"
                + ", CANTIDAD = " + cantidad
                + " WHERE ID = " + id);
    }
}
