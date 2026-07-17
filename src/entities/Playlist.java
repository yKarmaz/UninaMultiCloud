package entities;

import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.ChiaveGenerataNonValidaException;

public abstract class Playlist {
	private int ID;
    private String nome;
    private LocalDate dataCreazione;
    private Utente proprietario; 
    private ArrayList<ElementoMultimediale> elementi; 
    String tipologia;
    public Playlist(int ID, String nome, Utente proprietario, String tipologia) {
    	if(ID < 0) throw new ChiaveGenerataNonValidaException();
    	this.ID = ID;
        this.nome = nome;
        this.proprietario = proprietario;
        this.dataCreazione = LocalDate.now();
        this.elementi = new ArrayList<>();
        this.tipologia = tipologia;
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

    public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public boolean rimuoviElemento(ElementoMultimediale e) {
        return this.elementi.remove(e);
    }


	public void setIdPlaylist(int int1) {
		// TODO Auto-generated method stub
		
		ID = int1;
	}


	public void setNome(String string) { //metodo di test da cancellare
		// TODO Auto-generated method stub
		nome = string;
	}
}