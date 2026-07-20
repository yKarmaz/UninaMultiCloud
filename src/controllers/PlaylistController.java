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
import entities.ElementoMultimediale;
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

        Playlist salvata = playlistDao.salvaPlaylist(nuovaPlaylist);
        return salvata != null;
    }

    public List<Playlist> getMiePlaylistPrivate() {
        Utente u = sessionCtrl.getUtenteLoggato();
        if (u == null) return new ArrayList<>();
        return playlistDao.trovaPlaylistPrivateUtente(u.getIdUtente());
    }

    public List<Playlist> getPlaylistPubbliche() {
        return playlistDao.trovaTutteLePubbliche();
    }
    
    public List<Playlist> getMiePlaylistCondivise() {
        Utente u = sessionCtrl.getUtenteLoggato();
        if (u == null) return new ArrayList<>();
        return playlistDao.listaPlaylistInCondivisioneConMeDaMe(u);
    }

    

    public boolean aggiungiElementoAPlaylist(Playlist p, ElementoMultimediale el) {
       
        return playlistDao.aggiungiBrano(p, el);
        
    }

    public List<ElementoMultimediale> getBraniPlaylist(Playlist p) {
       
        return playlistDao.estraiBraniDaPlaylist(p); 
    }
    
    public List<Playlist> getMiePlaylistPubbliche() {
        Utente u = sessionCtrl.getUtenteLoggato();
        if (u == null) return new ArrayList<>();
        
        
        List<Playlist> tutteLeMie = playlistDao.listaPlaylistProprie(u);
        List<Playlist> soloMiePubbliche = new ArrayList<>();
        
       
        if (tutteLeMie != null) {
            for(Playlist p : tutteLeMie) {
                if(p instanceof PlaylistPubblica) {
                    soloMiePubbliche.add(p);
                }
            }
        }
        return soloMiePubbliche;
    }

	public String getCategoria(Playlist p) {
		return playlistDao.getCategoriaPlaylist(p);
	}
	
	public ArrayList<String> getAllCategorie()
	{
		return playlistDao.getAllCategorie();
	}

	public void setCategoria(PlaylistPubblica p, String categoria) {
		p.setCategoria(categoria);
		
	}
}