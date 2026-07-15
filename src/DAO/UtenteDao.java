package DAO;

import entities.Utente;
public interface UtenteDao {
	int salvaUtente(Utente utente);
	
	Utente trovaUtenteDaID(int id);
	
	boolean modificaUtente(Utente utente);
	
	boolean cancellaUtente(Utente utente);
}
