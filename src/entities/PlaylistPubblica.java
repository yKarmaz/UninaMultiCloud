package entities;


public class PlaylistPubblica extends Playlist {
    private String categoria;
    private int numVisualizzazioni;

    public PlaylistPubblica(String nome, Utente proprietario, String categoria) {
    	super(nome, proprietario);
    	this.categoria = categoria;
    	this.numVisualizzazioni = 0; 
    }

    public String getCategoria() {
		return categoria;
	}

	public int getNumVisualizzazioni() {
		return numVisualizzazioni;
	}

	public void incrementaVisualizzazioni() {
    	this.numVisualizzazioni++;
    }

}
