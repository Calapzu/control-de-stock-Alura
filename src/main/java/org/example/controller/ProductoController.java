package org.example.controller;

import org.example.factory.ConectionFactory;
import org.example.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {
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

    public List<Map<String, String>> listar() throws SQLException {
        /*Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "root"
        );*/
        final Connection connection = new ConectionFactory().recuperaConexion();

        try (connection) {

            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD " +
                    "FROM PRODUCTO");

            try (preparedStatement) {


                boolean result = preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getResultSet();

                List<Map<String, String>> resultado = new ArrayList<>();

                while (resultSet.next()) {
                    Map<String, String> fila = new HashMap<>();
                    fila.put("ID", String.valueOf(resultSet.getInt("ID")));
                    fila.put("NOMBRE", resultSet.getString("NOMBRE"));
                    fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
                    fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

                    resultado.add(fila);
                }

                return resultado;
            }
        }
    }

    public void guardar(Producto producto) throws SQLException {
        String nombre = producto.getNombre();
        String descripcion = producto.getDescripcion();
        int cantidad = producto.getCantidad();
        Integer maximaCantidad = 50;

        ConectionFactory factory = new ConectionFactory();
        final Connection connection = factory.recuperaConexion();

        try (connection) {

            connection.setAutoCommit(false);

            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTO (nombre, descripcion, cantidad)"
                            + " VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            try (preparedStatement) {
                do {
                    int cantidadParaGuardar = Math.min(cantidad, maximaCantidad);

                    ejecutarRegistro(producto, preparedStatement);

                    cantidad -= maximaCantidad;

                } while (cantidad > 0);

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
                System.out.println(
                        String.format(
                                "Fue insertado en el producto de ID %d",
                                resultSet.getInt(1)));
            }
        }
    }
}