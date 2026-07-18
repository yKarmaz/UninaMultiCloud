package DAO;


import java.util.ArrayList;

import entities.Playlist;
import entities.Utente;

public interface PlaylistDao {
	
	Playlist salvaPlaylist(Playlist playlist);
	
	Playlist trovaPlaylist(Playlist playlist);
	
	boolean modificaPlaylist(Playlist playlist);
	
	boolean cancellaPlaylist(Playlist playlist);
	
	ArrayList<Playlist> listaPlaylistProprie(Utente utente);
	
	ArrayList<Playlist> listaPlaylistInCondivisioneConMe(Utente utente);
	
	ArrayList<Playlist> trovaTutteLePubbliche();
	
	ArrayList<Playlist> trovaPlaylistPrivateUtente(int idUtente);
}
