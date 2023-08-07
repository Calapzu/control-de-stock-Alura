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
            var querySelect = "SELECT ID, NOMBRE FROM CATEGORIA";
            System.out.println(querySelect);

            final PreparedStatement preparedStatement = connection.prepareStatement(querySelect);

            try (preparedStatement) {


                final ResultSet resultSet = preparedStatement.executeQuery();
                try (resultSet) {
                    while (resultSet.next()) {
                        var categoria = new Categoria(resultSet.getInt("ID"), resultSet.getString("NOMBRE"));

                        resultado.add(categoria);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            var querySelect = "SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD" + " FROM CATEGORIA C " + "INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID";

            System.out.println(querySelect);

            final PreparedStatement preparedStatement = connection.prepareStatement(querySelect);

            try (preparedStatement) {


                final ResultSet resultSet = preparedStatement.executeQuery();
                try (resultSet) {
                    while (resultSet.next()) {
                        int categoriaId = resultSet.getInt("ID");
                        String categoriaNombre = resultSet.getString("NOMBRE");

                        var categoria = resultado
                                .stream()
                                .filter(cat -> cat.getId().equals(categoriaId))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(categoriaId, categoriaNombre);

                                    resultado.add(cat);
                                    return cat;
                                });

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}
