package entities;

public class PlaylistPrivata extends Playlist {
	    
	public PlaylistPrivata(int ID, String nome, Utente proprietario) {
		super(ID, nome, proprietario, "Privata");
	}
}
