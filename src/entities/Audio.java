package entities;

import java.time.Duration;
import java.time.LocalDate;

public class Audio extends ElementoMultimediale {
	
	private int idAudio;
	private int bitRate;
	
	public Audio(int idAudio, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario, int bitRate ) {
		super(idAudio, titolo, durata, dataCreazione, immagineCopertina, proprietario);
		// TODO Auto-generated constructor stub
		this.bitRate = bitRate;
		this.idAudio = idAudio;
	}

	public Audio(int idAudio, String descrizione, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario, int bitRate) {
		super(idAudio, descrizione, titolo, durata, dataCreazione, immagineCopertina, proprietario);
		// TODO Auto-generated constructor stub
		this.bitRate = bitRate;
		this.idAudio = idAudio;
	}
	
	
	
	public int getBitRate() {
		return bitRate;
	}

	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}

	public void riproduciAudio()
	{
		
	}

	public int getIdAudio() {
		return idAudio;
	}

	public void setIdAudio(int idAudio) {
		this.idAudio = idAudio;
	}

}
