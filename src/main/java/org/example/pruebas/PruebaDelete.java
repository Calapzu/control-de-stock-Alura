package org.example.pruebas;

import org.example.factory.ConectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaDelete {
    public static void main(String[] args) throws SQLException {
        Connection connection = new ConectionFactory().recuperaConexion();

        Statement statement = connection.createStatement();

        boolean result = statement.execute("DELETE FROM PRODUCTO WHERE ID = 99");

        System.out.println( statement.getUpdateCount());
    }
}
