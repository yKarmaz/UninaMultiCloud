package entities;

import java.time.Duration;
import java.time.LocalDate;

public class Video extends ElementoMultimediale {
	
	private int idVideo;
	private String risoluzione;
	
	public Video(int idVideo, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina,	Utente proprietario, String risoluzione) {
		super(idVideo, titolo, durata, dataCreazione, immagineCopertina, proprietario);
		// TODO Auto-generated constructor stub
		this.risoluzione = risoluzione;
		this.setIdVideo(idVideo);
	}

	public Video(int idVideo, String descrizione, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario, String risoluzione) {
		super(idVideo, descrizione, titolo, durata, dataCreazione, immagineCopertina, proprietario);
		// TODO Auto-generated constructor stub
		this.risoluzione = risoluzione;
		this.setIdVideo(idVideo);
	}

	public String getRisoluzione() {
		return risoluzione;
	}

	public void setRisoluzione(String risoluzione) {
		this.risoluzione = risoluzione;
	}
	
	
	public void riproduciVideo()
	{
		
	}

	public int getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(int idVideo) {
		this.idVideo = idVideo;
	}
	
	
}
