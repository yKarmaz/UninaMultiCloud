package entities;

import java.time.Duration;
import java.time.LocalDate;

public class Audio extends ElementoMultimediale {
	
	
	public Audio(int idAudio, String descrizione, String titolo, int durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario, int bitRate) {
		super(idAudio, descrizione, titolo, durata, dataCreazione, immagineCopertina, proprietario, "audio", null, bitRate);
		// TODO Auto-generated constructor stub
		
	}
	
	
	

	public void riproduciAudio()
	{
		
	}


}
