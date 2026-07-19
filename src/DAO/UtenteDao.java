package DAO;

import entities.Utente;
public interface UtenteDao {
	int salvaUtente(Utente utente);
	
	Utente trovaUtente(String username, String password);
	
	boolean modificaUtente(Utente utente);
	
	boolean cancellaUtente(Utente utente);

	Utente trovaUtenteDaID(int ID);
	
	int contaContenutiPubblicati(Utente u);
}
