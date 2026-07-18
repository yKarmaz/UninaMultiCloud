package DAO;

import java.util.List;

import entities.ElementoMultimediale;

public interface ElementoMultimedialeDao {
	
	int salvaContenuto(ElementoMultimediale e);
	
	ElementoMultimediale trovaContenutoDalD(int id);
	
	List<ElementoMultimediale> cercaElementi(String testo, String tipoMedia);
	
	boolean modificaContenuto(ElementoMultimediale e);
	
	boolean cancellaContenuto(ElementoMultimediale e);
	
}
