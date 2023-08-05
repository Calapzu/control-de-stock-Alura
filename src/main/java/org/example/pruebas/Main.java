package org.example.pruebas;

import org.example.factory.ConectionFactory;

import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {
        /*Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "root"
        );*/
        Connection connection = new ConectionFactory().recuperaConexion();
        System.out.println("Cerrando la conexion");
        connection.close();
    }
}