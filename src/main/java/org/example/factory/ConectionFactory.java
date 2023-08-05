package org.example.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionFactory {

    private DataSource dataSource;
    public ConectionFactory() {
        var poolDataSouerce = new ComboPooledDataSource();
        poolDataSouerce.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
        poolDataSouerce.setUser("root");
        poolDataSouerce.setPassword("root");
        poolDataSouerce.setMaxPoolSize(10);

        this.dataSource = poolDataSouerce;
    }

    public Connection recuperaConexion(){
        try{
            return this.dataSource.getConnection();
        }catch (SQLException e){
         throw new RuntimeException(e);
        }

    }
}
