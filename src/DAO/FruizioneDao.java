package DAO;

import java.sql.Timestamp;

import entities.*;

public interface FruizioneDao {

int salvaPlaylist(Fruizione fruizione);
	
	Fruizione trovaPlaylistDaID(Utente u, ElementoMultimediale e, Timestamp momentoFruizione );
	
	boolean modificaFruizione(Fruizione fruizione);
	
	boolean cancellaFruizione(Fruizione fruizione);
}
