package entities;

import java.util.ArrayList;

import exceptions.ChiaveGenerataNonValidaException;

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
		if(idUtente < 0) throw new ChiaveGenerataNonValidaException("ID utente non valido");
		else this.idUtente = idUtente;
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
		if(this.getPassword().equals(oldPassword))
		{
			password = newPassword;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public ArrayList <ElementoMultimediale> getElementiPubblicati(){
		return this.elementiPubblicati;
	}
	
	public ArrayList <Playlist> getMiePlaylist(){
		return this.miePlaylist;
	}


	public void setMatricola(String matricola) {
		Matricola = matricola;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setIdUtente(int i) //metodo di test, da cancellare
	{
		idUtente = i;
	}
	
	
	
}
