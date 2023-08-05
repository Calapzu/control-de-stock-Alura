package org.example.pruebas;

import org.example.factory.ConectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaPoolDeConexiones {
    public static void main(String[] args) throws SQLException {
       ConectionFactory conectionFactory = new ConectionFactory();
        for (int i = 0; i < 20; i++) {
            Connection connection = conectionFactory.recuperaConexion();

            System.out.println("Abriendo la conexion de numero "+(i+1));
        }
    }
}
