package entities;

import java.util.ArrayList;

public class Utente {

	private String Matricola;
	private String nome;
	private String cognome;
	private String username;
	private String email;
	private String password;
	private ArrayList <ElementoMultimediale> elementiPubblicati;
	private ArrayList <Playlist> miePlaylist;
	
	public Utente(String matricola, String nome, String cognome, String username, String email, String password) {
		Matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getMatricola() {
		return Matricola;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public boolean modificaPassword(String oldPassword, String newPassword) {
		
	}
	
	public ArrayList <ElementoMultimediale> getElementiPubblicati(){
		
	}
	
	public ArrayList <PlayList> getMiePlaylist(){
		
	}
}
