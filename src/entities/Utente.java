package entities;

import java.util.ArrayList;

public class Utente {
	
	private int idUtente;
	private String Matricola;
	private String nome;
	private String cognome;
	private String username;
	private String email;
	private String password;
	private ArrayList <ElementoMultimediale> elementiPubblicati;
	private ArrayList <Playlist> miePlaylist;
	
	public Utente(int idUtente, String matricola, String nome, String cognome, String username, String email, String password) {
		this.idUtente = idUtente;
		this.Matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	
	public int getIdUtente() {
		return idUtente;
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
		return this.elementiPubblicati;
	}
	
	public ArrayList <Playlist> getMiePlaylist(){
		return this.miePlaylist;
	}
}
