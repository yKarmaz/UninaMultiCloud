package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/UninaMultiCloud";
    private static final String USER = "postgres";
    private static final String PASSWORD = "murogiallo";

    private DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connessione al database stabilita con successo");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Errore nell'inizializzazione del driver o della connessione DB:");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}