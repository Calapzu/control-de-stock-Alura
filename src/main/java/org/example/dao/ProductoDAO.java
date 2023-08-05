package org.example.dao;

import org.example.modelo.Producto;

import java.sql.*;

public class ProductoDAO {

    final private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardar(Producto producto) throws SQLException {

        try (connection) {

            connection.setAutoCommit(false);

            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTO (nombre, descripcion, cantidad)"
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
                connection.commit();
                System.out.println("COMMIT");
            } catch (Exception e) {
                connection.rollback();
                System.out.println("ROLLBACK");
            }
        }
    }

    private void ejecutarRegistro(Producto producto, PreparedStatement preparedStatement) throws SQLException {

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
    }

}


