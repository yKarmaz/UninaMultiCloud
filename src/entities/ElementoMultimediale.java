package entities;

import java.time.Duration;
import java.time.LocalDate;

public class ElementoMultimediale {
	private String descrizione;
	private String titolo;
	private Duration durata;
	private LocalDate dataCreazione;
	private String immagineCopertina;
	private int numVisualizzazioni;
	private Utente proprietario;
	public ElementoMultimediale(String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario) {
		descrizione = "";
		this.titolo = titolo;
		this.durata = durata;
		this.dataCreazione = dataCreazione;
		this.immagineCopertina = immagineCopertina;
		numVisualizzazioni = 0;
		this.proprietario = proprietario;
	}
	
	public ElementoMultimediale(String descrizione, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina) {
		this.descrizione = descrizione;
		this.titolo = titolo;
		this.durata = durata;
		this.dataCreazione = dataCreazione;
		this.immagineCopertina = immagineCopertina;
		numVisualizzazioni = 0;
		this.proprietario = proprietario;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Duration getDurata() {
		return durata;
	}

	public void setDurata(Duration durata) {
		this.durata = durata;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getImmagineCopertina() {
		return immagineCopertina;
	}

	public void setImmagineCopertina(String immagineCopertina) {
		this.immagineCopertina = immagineCopertina;
	}

	public int getNumVisualizzazioni() {
		return numVisualizzazioni;
	}

	public void setNumVisualizzazioni(int numVisualizzazioni) {
		this.numVisualizzazioni = numVisualizzazioni;
	}
	
	public String visualizzaDettagli()
	{
		String stringaRitorno = "Titolo: " + titolo + "; Durata: " + durata + "; Data Creazione" + dataCreazione + "; Visualizzazioni: " + numVisualizzazioni + "; Copertina: "+ immagineCopertina + "; Descrizione: " + descrizione; 
		return stringaRitorno;
	}
	
	
	
}
