package entities;

import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.ChiaveGenerataNonValidaException;

public class ElementoMultimediale {
	private int idElemento;
	private String descrizione;
	private String titolo;
	private int durata; //in secondi
	private LocalDate dataCreazione;
	private String immagineCopertina;
	private int numVisualizzazioni;
	private Utente proprietario;
	private String tipoElemento;
	private String risoluzione;
	private Integer bitRate;
	private ArrayList<Utente> listaFruitori = new ArrayList<Utente>();

	
	public ElementoMultimediale(int idElemento, String descrizione, String titolo, int durata, LocalDate dataCreazione, String immagineCopertina, Utente proprietario, String tipoElemento, String risoluzione, Integer bitRate) {
		if(idElemento < 0) throw new ChiaveGenerataNonValidaException("ID elemento non valido");
		this.idElemento = idElemento;
		this.descrizione = descrizione;
		this.titolo = titolo;
		this.durata = durata;
		this.dataCreazione = LocalDate.now();
		this.immagineCopertina = immagineCopertina;
		numVisualizzazioni = 0;
		this.proprietario = proprietario;
		this.tipoElemento = tipoElemento;
	    this.risoluzione = risoluzione;
	    this.bitRate = bitRate;
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

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
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

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getRisoluzione() {
		return risoluzione;
	}

	public void setRisoluzione(String risoluzione) {
		this.risoluzione = risoluzione;
	}

	public Integer getBitRate() {
		return bitRate;
	}

	public void setBitRate(Integer bitRate) {
		this.bitRate = bitRate;
	}

	public int getIdElemento() {
		return idElemento;
	}
	
	
}
