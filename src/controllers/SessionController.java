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
            Connection conn = DBConnection.getInstance().getConnection();
            this.utenteDAO = new UtenteDAO_Impl(conn);
        } catch (SQLException e) {
            System.err.println("Impossibile connettersi al Database.");
            e.printStackTrace();
        }
    }

    public void mostraFinestraLogin() {
        this.paginaLogin = new LoginPage(this);
        this.paginaLogin.setVisible(true);
    }

    public void eseguiLogin(String username, String password) {
        Utente utenteTrovato = this.utenteDAO.trovaUtente(username, password);
        
        if (utenteTrovato != null) {
            this.utenteLoggato = utenteTrovato;
            this.paginaLogin.dispose(); // Chiude il login
            
            // Inizializza i controller di business passandogli la sessione
            MediaController mediaCtrl = new MediaController(this);
            PlaylistController playlistCtrl = new PlaylistController(this);
            
            // IN FUTURO: Scommenta questo quando farai il ReportController
            // ReportController reportCtrl = new ReportController(this); 
            
            // Istanzia e mostra la HomePage vera e propria
            HomePage home = new HomePage(this, mediaCtrl, playlistCtrl); 
            home.setVisible(true);
            
            System.out.println("Login effettuato con successo per: " + utenteLoggato.getUsername());
        } else {
            this.paginaLogin.mostraErrore("Credenziali errate! Riprova.");
        }
    }

    public void eseguiLogout(JFrame homeView) {
        this.utenteLoggato = null;
        homeView.dispose();
        mostraFinestraLogin();
    }

    public Utente getUtenteLoggato() {
        return this.utenteLoggato;
    }
}