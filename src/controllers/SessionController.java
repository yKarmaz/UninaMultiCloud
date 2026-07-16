package controllers;

import java.sql.Connection;
import boundaries.*;
import DAO.*;
import DAO.impl.*;
import entities.*;
import databaseConnection.DBConnection;

public class SessionController {
    
    // Lo stato della sessione
    private Utente utenteLoggato;
    
    // Il riferimento al Database
    private UtenteDao utenteDAO; 

    public SessionController() {
        // 1. Chiedo al DBConnection di darmi la connessione aperta
        Connection conn = DBConnection.getConnection();
        
        // 2. Inizializzo il DAO passandogli la connessione
        this.utenteDAO = new UtenteDAO_Impl(conn); 
    }

    /**
     * Riceve i dati dalla LoginPage, interroga il DB e decide che fare.
     */
    public void eseguiLogin(String username, String password, LoginPage loginView) { // Assicurati che LoginPage sia importata
        
        // Chiamo il metodo sull'ISTANZA del DAO (this.utenteDAO), NON sulla classe astratta!
        Utente utenteTrovato = this.utenteDAO.trovaUtente(username, password);

        if (utenteTrovato != null) {
            // 1. Salvo l'utente in RAM
            this.utenteLoggato = utenteTrovato;
            
            // 2. Distruggo la finestra di login
            loginView.dispose();
            
            // 3. Creo gli altri controller
            //MediaController mediaCtrl = new MediaController();
            //PlaylistController playlistCtrl = new PlaylistController();
            //ReportController reportCtrl = new ReportController();

            // 4. Istanzio la HomePage e la accendo
            HomePage home = new HomePage(this, mediaCtrl, playlistCtrl, reportCtrl);
            home.setVisible(true);
            
        } else {
            // Torno alla Boundary per farle mostrare l'errore
            loginView.mostraErrore("Credenziali errate! Riprova.");
        }
    }

    public void eseguiLogout(HomePage homeView) {
        this.utenteLoggato = null;
        homeView.dispose();
        
        LoginPage login = new LoginPage(this);
        login.setVisible(true);
    }

    public Utente getUtenteLoggato() {
        return this.utenteLoggato;
    }
}