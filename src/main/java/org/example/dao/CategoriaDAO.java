package org.example.dao;

import org.example.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ID, NOMBRE FROM CATEGORIA"
            );

            try (preparedStatement){


            final ResultSet resultSet = preparedStatement.executeQuery();
            try (resultSet) {
                while(resultSet.next()){
                    var categoria = new Categoria(resultSet.getInt("ID"),
                    resultSet.getString("NOMBRE"));

                    resultado.add(categoria);
                }
            };
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}
