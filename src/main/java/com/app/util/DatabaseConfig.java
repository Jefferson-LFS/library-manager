package com.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (in == null) {
                throw new RuntimeException("database.properties não encontrado no classpath. " +
                        "Copie database.properties.example para database.properties e configure as credenciais.");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar database.properties", e);
        }
    }

    public static String getHost()     { return props.getProperty("db.host"); }
    public static int    getPort()     { return Integer.parseInt(props.getProperty("db.port")); }
    public static String getName()     { return props.getProperty("db.name"); }
    public static String getUser()     { return props.getProperty("db.user"); }
    public static String getPassword() { return props.getProperty("db.password"); }
}
