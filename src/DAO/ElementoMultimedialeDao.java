package DAO;

import entities.ElementoMultimediale;

public interface ElementoMultimedialeDao {
	
	int salvaContenuto(ElementoMultimediale e);
	
	ElementoMultimediale trovaContenutoDaID(int ID);
	
	boolean modificaContenuto(ElementoMultimediale e);
	
	boolean cancellaContenuto(ElementoMultimediale e);
	
}
