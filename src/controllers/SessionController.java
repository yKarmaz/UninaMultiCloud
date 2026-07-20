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
            
            // Inizializza i controller passandogli la sessione
            MediaController mediaCtrl = new MediaController(this);
            PlaylistController playlistCtrl = new PlaylistController(this);
            
            
            ReportController reportCtrl = new ReportController(this); 
            
            // Istanzia e mostra la HomePage
            HomePage home = new HomePage(this, mediaCtrl, playlistCtrl, reportCtrl); 
            home.setVisible(true);
            
            
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