package entities;

import java.util.UUID;

public class PlaylistCondivisa extends Playlist {
	private String URL_Invito;

	public PlaylistCondivisa(int ID, String nome, Utente proprietario) {
		super(ID, nome, proprietario, "Condivisa");
		this.URL_Invito = generaURL(); 
	}
	
	public String getURL_Invito() { return URL_Invito; }
	
	public String generaURL() {
		String token = UUID.randomUUID().toString().substring(0, 8);
		return "https://uninamulticloud.it/join/" + token;
	}
}
