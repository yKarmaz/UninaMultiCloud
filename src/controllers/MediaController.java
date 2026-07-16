package controllers;

import java.util.ArrayList;
import java.util.List;
import entities.ElementoMultimediale;
// import entities.Audio; // Assicurati di importare le tue Entity
// import entities.Video;

public class MediaController {
    
    public MediaController() {
        // Qui in futuro istanzierai il MediaDAO passandogli la DBConnection
        // this.mediaDAO = new MediaDAO_Impl(DBConnection.getConnection());
    }

    /**
     * Questo metodo verrà chiamato dalla tua Boundary (es. CatalogView)
     * quando l'utente clicca su "Cerca" o apre il catalogo.
     */
    public List<ElementoMultimediale> cercaBrani(String testoRicerca) {
        
        // --- IN FUTURO SARA' COSI' ---
        // return mediaDAO.cercaPerTitoloOAutore(testoRicerca);
        
        // --- FAKE DATA PER TESTARE LA GUI OGGI ---
        List<ElementoMultimediale> risultatiFinti = new ArrayList<>();
        System.out.println("Ricerca simulata per: " + testoRicerca);
        
        // Nota: Adatta questi costruttori a quelli reali che hai nelle tue classi Audio/Video
        // risultatiFinti.add(new Audio("Bohemian Rhapsody", "Queen", 355));
        // risultatiFinti.add(new Video("Live at Wembley", "Queen", 7200));
        
        return risultatiFinti;
    }

    /**
     * Chiamato dalla Boundary UploadView
     */
    public boolean caricaNuovoElemento(String titolo, String autore, String percorsoFile, String tipo) {
        // Validazione base
        if(titolo.isEmpty() || autore.isEmpty() || percorsoFile.isEmpty()) {
            return false;
        }
        
        // --- IN FUTURO ---
        // Passerai i dati al DAO per fare l'INSERT nel DB
        
        System.out.println("Simulazione: Elemento '" + titolo + "' di " + autore + " caricato con successo!");
        return true; 
    }
}
