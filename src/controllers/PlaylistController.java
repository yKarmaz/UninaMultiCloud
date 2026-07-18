package controllers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import DAO.PlaylistDao;
import DAO.impl.PlaylistDAO_Impl;
import databaseConnection.DBConnection;
import entities.Playlist;
import entities.PlaylistPrivata;
import entities.PlaylistPubblica;
import entities.PlaylistCondivisa;
import entities.Utente;

public class PlaylistController {
    
    private PlaylistDao playlistDao;
    private SessionController sessionCtrl;

    public PlaylistController(SessionController sessionCtrl) {
        this.sessionCtrl = sessionCtrl;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            this.playlistDao = new PlaylistDAO_Impl(conn);
        } catch (Exception e) {
            System.err.println("Errore: Impossibile collegare PlaylistController al DB");
            e.printStackTrace();
        }
    }

    public boolean creaNuovaPlaylist(String nome, String tipo, String categoria) {
        Utente proprietario = sessionCtrl.getUtenteLoggato();
        if (proprietario == null) return false;

        Playlist nuovaPlaylist;
        
        // Smisto le Entity in base alla tendina della grafica
        switch (tipo) {
            case "Pubblica":
                nuovaPlaylist = new PlaylistPubblica(0, nome, proprietario, categoria);
                break;
            case "Condivisa":
                nuovaPlaylist = new PlaylistCondivisa(0, nome, proprietario);
                break;
            default:
                nuovaPlaylist = new PlaylistPrivata(0, nome, proprietario);
                break;
        }

        // Chiamo il DAO del tuo compagno
        Playlist salvata = playlistDao.salvaPlaylist(nuovaPlaylist);
        return salvata != null; 
    }

    // ⚠️ STUB: Metodi per popolare le tabelle del CatalogoPlaylist
    public List<Playlist> getMiePlaylistPrivate() {
        return playlistDao.trovaPlaylistPrivateUtente(sessionCtrl.getUtenteLoggato().getIdUtente());
        
    }

    public List<Playlist> getPlaylistPubbliche() {
        return playlistDao.trovaTutteLePubbliche();
        
    }
}