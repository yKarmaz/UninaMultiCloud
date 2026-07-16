package DAO;

import entities.Playlist;

public interface PlaylistDao {
	
	Playlist salvaPlaylist(Playlist playlist);
	
	Playlist trovaPlaylist(Playlist playlist);
	
	boolean modificaPlaylist(Playlist playlist);
	
	boolean cancellaPlaylist(Playlist playlist);
}
