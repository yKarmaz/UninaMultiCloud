package entities;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class ElementoMultimediale {
	private int idElemento;
	private String descrizione;
	private String titolo;
	private Duration durata;
	private LocalDate dataCreazione;
	private String immagineCopertina;
	private int numVisualizzazioni;
	private Utente proprietario;
	private ArrayList<Utente> listaFruitori = new ArrayList<Utente>();
	public ElementoMultimediale(int idElemento, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario ){
		this.idElemento = idElemento;
		descrizione = "";
		this.titolo = titolo;
		this.durata = durata;
		this.dataCreazione = dataCreazione;
		this.immagineCopertina = immagineCopertina;
		numVisualizzazioni = 0;
		this.proprietario = proprietario;
	}
	
	public ElementoMultimediale(int idElemento, String descrizione, String titolo, Duration durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario) {
		this.idElemento = idElemento;
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
	public Utente getProprietario() {
		return proprietario;
	}
	public void setProprietario(Utente proprietario) {
		this.proprietario = proprietario;
	}
	public ArrayList<Utente> getListaFruitori() {
		return listaFruitori;
	}
	public void setListaFruitori(ArrayList<Utente> listaFruitori) {
		this.listaFruitori = listaFruitori;
	}

	public String visualizzaDettagli()
	{
		String stringaRitorno = "Titolo: " + titolo + "; Durata: " + durata + "; Data Creazione" + dataCreazione + "; Visualizzazioni: " + numVisualizzazioni + "; Copertina: "+ immagineCopertina + "; Descrizione: " + descrizione; 
		return stringaRitorno;
	}
	public void addFruitore(Utente u)
	{
		listaFruitori.add(u);
	}
	public void removeFruitore(Utente u)
	{
		 listaFruitori.remove(u);
	}
	
	
}
