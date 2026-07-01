package com.app.database;

import com.app.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        return getConnection(
                DatabaseConfig.getHost(),
                DatabaseConfig.getPort(),
                DatabaseConfig.getName(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword()
        );
    }

    public static Connection getConnection(
            final String enderecoIP,
            final int enderecoPorta,
            final String nomeBanco,
            final String usuario,
            final String senha
    ) throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://" + enderecoIP + ":" + enderecoPorta + "/" + nomeBanco,
                usuario,
                senha
        );
    }

}