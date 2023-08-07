package org.example.dao;

import org.example.factory.ConectionFactory;
import org.example.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoDAO {

    private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public int modificar(Producto producto){

        try{

            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCTO SET "
                    + " NOMBRE = ? "
                    + ", DESCRIPCION = ? "
                    + ", CANTIDAD = ? "
                    + " WHERE ID = ? "
            );

            return ejecutarModificacion(producto, preparedStatement);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();

        try {

            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD " +
                    "FROM PRODUCTO");

            try (preparedStatement) {

                preparedStatement.execute();

                final ResultSet resultSet = preparedStatement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD")
                        );
                        resultado.add(fila);
                    }
                }
            }
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardar(Producto producto) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTO (nombre, descripcion, cantidad)"
                            + " VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            try (preparedStatement) {
                /*do {
                    int cantidadParaGuardar = Math.min(cantidad, maximaCantidad);

                    ejecutarRegistro(producto, preparedStatement);

                    cantidad -= maximaCantidad;

                } while (cantidad > 0);
                */

                ejecutarRegistro(producto, preparedStatement);
                //connection.commit();
                System.out.println("COMMIT");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int ejecutarModificacion(Producto producto, PreparedStatement preparedStatement){

        try (preparedStatement) {


            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setInt(4, producto.getId());

            preparedStatement.execute();

            int updateCount;
            updateCount = preparedStatement.getUpdateCount();

            //System.out.println(id + " " + nombre + " " + descripcion);
           return updateCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void ejecutarRegistro(Producto producto, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setInt(3, producto.getCantidad());

            preparedStatement.execute();

            /**
             * Try With Resources
             * Con la version 7 de JAVA

             try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
             while (resultSet.next()) {
             System.out.println(
             String.format(
             "Fue insertado en el producto de ID %d",
             resultSet.getInt(1)));
             }
             }
             * */

            /**
             * Try With Resources
             * Con la version 9 de JAVA
             */
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            try (resultSet) {
                while (resultSet.next()) {
                    producto.setId(resultSet.getInt(1));
                    System.out.println(
                            String.format(
                                    "Fue insertado en el producto de ID %s",
                                    producto));
                }
            }
        }catch (SQLException e){
            System.out.println("Error" + e);
        }
    }


}

