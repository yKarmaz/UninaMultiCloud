package mainApp;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import databaseConnection.DBConnection;
import entities.*;
import DAO.*;
import DAO.impl.*;
import controllers.*;
import boundaries.*; 

public class Main {

    public static void main(String[] args) {
    	
    	
        System.out.println("Avvio dell'applicazione UninaMultiCloud...");
        
        // Deleghiamo l'avvio della grafica al thread corretto (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            
            // 1. Accendiamo il "cervello" iniziale: il SessionController
            SessionController sessionController = new SessionController();
            
            // 2. Chiediamo al controller di mostrare la prima Boundary (il Login)
            sessionController.mostraFinestraLogin();
            
        });
       
        
    }
}