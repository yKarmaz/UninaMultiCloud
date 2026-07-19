package DAO;

import java.util.List;

import entities.ElementoMultimediale;
import entities.Utente;

public interface ElementoMultimedialeDao {
	
	int salvaContenuto(ElementoMultimediale e);
	
	ElementoMultimediale trovaContenutoDalD(int id);
	
	List<ElementoMultimediale> cercaElementi(String testo, String tipoMedia);
	
	boolean modificaContenuto(ElementoMultimediale e);
	
	boolean cancellaContenuto(ElementoMultimediale e);

	int getSommaVisualizzazioni(Utente u);
	
}
