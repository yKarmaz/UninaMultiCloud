package DAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import entities.*;

public interface FruizioneDao {

	Fruizione SalvaFruizione(Fruizione fruizione);
	
	Fruizione trovaFruizione(Fruizione fruizione);
	
	Fruizione modificaFruizione(Fruizione vecchiaFruizione, Fruizione nuovaFruizione);
	
	boolean cancellaFruizione(Fruizione fruizione);

	int getFruizioniDiUtente(Utente u);
}
