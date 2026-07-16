package controllers;

import entities.Playlist;
// import entities.PlaylistPubblica;
import entities.Utente;

public class PlaylistController {
    
    private SessionController sessione; // Serve per sapere chi è loggato

    public PlaylistController(SessionController sessione) {
        this.sessione = sessione;
        // Qui in futuro: this.playlistDAO = new PlaylistDAO_Impl(DBConnection.getConnection());
    }

    /**
     * Chiamato dalla Boundary CreaPlaylistView
     */
    public boolean creaNuovaPlaylist(String nome, String tipologia) {
        Utente utenteProprietario = sessione.getUtenteLoggato();
        
        if (utenteProprietario == null) {
            System.err.println("Errore critico: Nessun utente loggato in RAM!");
            return false;
        }

        if (nome.isEmpty()) {
            return false;
        }

        // --- IN FUTURO ---
        // Playlist nuova = null;
        // if (tipologia.equals("Pubblica")) nuova = new PlaylistPubblica(nome, utenteProprietario, "Generale");
        // return playlistDAO.salvaPlaylist(nuova);

        // --- FAKE LOGIC ---
        System.out.println("Simulazione DB: Playlist '" + nome + "' [" + tipologia + "] salvata per l'utente " + utenteProprietario.getUsername());
        return true;
    }
}