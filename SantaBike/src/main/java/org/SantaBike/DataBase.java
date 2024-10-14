package org.SantaBike;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {
    private static Connection connection; // Note que a conexão é mantida por meio de um padrão Singleton.

    public static ResultSet consultarResulta(String SQL) { // um atalho para realizar uma consulta e retornar seu ResultSet
        Connection Conexao = DataBase.getConnection();
        PreparedStatement consult;
        try {
            consult = Conexao.prepareStatement(SQL);
            ResultSet result = consult.executeQuery();
            return result;
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement consultarPuro(String SQL) { // um atalho para realizar uma consulta e retornar um PreparedStatement
        Connection Conexao = DataBase.getConnection();
        PreparedStatement consult;
        try {
            consult = Conexao.prepareStatement(SQL);
            return consult;
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return null;
    }

    public static void updateDB(String SQL){ // realiza sql do tipo create/update/delete
        Connection Conexao = DataBase.getConnection();
        PreparedStatement statement;
        try {
            statement = Conexao.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
    }

    public static void updateDB(String SQL, Object[] parametros) { // updateDB com parâmetros separados
        Connection Conexao = DataBase.getConnection();
        PreparedStatement statement;
        try {
            statement = Conexao.prepareStatement(SQL);
            
            // Definir os parâmetros
            for (int i = 0; i < parametros.length; i++) {
                statement.setObject(i + 1, parametros[i]);
            }
            
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                Properties properties = new Properties();
                try (InputStream inputStream = new FileInputStream("db.properties")) {
                    properties.load(inputStream);
                }
                String server = properties.getProperty("db.server");
                String database = properties.getProperty("db.database");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                String url = String.format("jdbc:postgresql://%s/%s",server,database);
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("-> Banco conectado com sucesso.");
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Sem conexão com o banco de dados.");
            }
        }
        return connection;
    }
}
