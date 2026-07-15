package entities;

import java.time.Duration;
import java.time.LocalDate;

public class Video extends ElementoMultimediale {
	
	

	public Video(int idVideo, String descrizione, String titolo, int durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario,  String risoluzione) {
		super(idVideo, descrizione, titolo, durata, dataCreazione, immagineCopertina, proprietario, "video", risoluzione, null);
		// TODO Auto-generated constructor stub
	
	}

	
	
	public void riproduciVideo()
	{
		
	}

	
	
}
