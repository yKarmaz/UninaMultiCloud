package entities;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Playlist {
    private String nome;
    private LocalDate dataCreazione;
    private Utente proprietario; 
    private ArrayList<ElementoMultimediale> elementi; 

    public Playlist(String nome, Utente proprietario) {
        this.nome = nome;
        this.proprietario = proprietario;
        this.dataCreazione = LocalDate.now();
        this.elementi = new ArrayList<>();
    }
    

	public String getNome() {
		return nome;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public Utente getProprietario() {
		return proprietario;
	}

	public ArrayList<ElementoMultimediale> getElementi() {
		return elementi;
	}

	public boolean addElemento(ElementoMultimediale e) {
        if (!this.elementi.contains(e)) {
            return this.elementi.add(e);
        }
        return false;
    }

    public boolean rimuoviElemento(ElementoMultimediale e) {
        return this.elementi.remove(e);
    }
}