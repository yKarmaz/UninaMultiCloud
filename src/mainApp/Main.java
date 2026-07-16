package mainApp;

import java.sql.Connection;
import java.sql.SQLException;
import databaseConnection.DBConnection;
import controllers.SessionController;
import boundaries.LoginPage;

public class Main {

    public static void main(String[] args) {
        System.out.println("Avvio dell'applicazione UninaMultiCloud...");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SessionController session = new SessionController();
                session.mostraFinestraLogin();
            }
        });
    }
} 	