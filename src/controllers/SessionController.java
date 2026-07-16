package controllers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;

import boundaries.*;
import DAO.*;
import DAO.impl.*;
import entities.*;
import databaseConnection.DBConnection;

public class SessionController {
    
    private Utente utenteLoggato;
    private UtenteDao utenteDAO; 
    private LoginPage paginaLogin;

    public SessionController() {
        try {
            // Recupero correttamente la connessione tramite il Singleton
            Connection conn = DBConnection.getInstance().getConnection();
            this.utenteDAO = new UtenteDAO_Impl(conn); 
        } catch (SQLException e) {
            System.err.println("Impossibile connettersi al Database.");
            e.printStackTrace();
        }
    }

    /**
     * Metodo di avvio dell'applicazione che accende la schermata di Login
     */
    public void mostraFinestraLogin() {
        // Passiamo 'this' in modo che la Login possa interagire con questo controller
        this.paginaLogin = new LoginPage(this);
        this.paginaLogin.setVisible(true);
    }

    /**
     * Riceve i dati dalla LoginPage, interroga il DB e decide le sorti della GUI.
     */
    public void eseguiLogin(String username, String password) {
        // Chiamo il metodo sull'istanza del DAO
        Utente utenteTrovato = this.utenteDAO.trovaUtente(username, password);

        if (utenteTrovato != null) {
            // 1. Salvo l'utente in sessione
            this.utenteLoggato = utenteTrovato;
            
            // 2. Chiudo e distruggo la finestra di login corrente
            this.paginaLogin.dispose();
            
            // 3. Inizializzo i controller secondari se necessario
            // MediaController mediaCtrl = new MediaController();
            // PlaylistController playlistCtrl = new PlaylistController();
            // ReportController reportCtrl = new ReportController();

            // 4. Istanzio la HomePage e la mostro
            // HomePage home = new HomePage(this, mediaCtrl, playlistCtrl, reportCtrl);
            // home.setVisible(true);
            System.out.println("Login effettuato con successo per: " + utenteLoggato.getUsername());
            
        } else {
            // Chiedo direttamente alla mia view di mostrare l'errore
            this.paginaLogin.mostraErrore("Credenziali errate! Riprova.");
        }
    }

    public void eseguiLogout(JFrame homeView) {
        this.utenteLoggato = null;
        homeView.dispose();
        
        // Riapro la schermata di login
        mostraFinestraLogin();
    }

    public Utente getUtenteLoggato() {
        return this.utenteLoggato;
    }
}