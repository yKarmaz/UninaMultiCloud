package controllers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import DAO.ElementoMultimedialeDao;
import DAO.impl.ElementoMultimedialeDAO_Impl;
import databaseConnection.DBConnection;
import entities.Audio;
import entities.Video;
import entities.ElementoMultimediale;
import entities.Utente;

public class MediaController {
    
    private ElementoMultimedialeDao mediaDao;
    private SessionController sessionCtrl;

    // Richiede il SessionController nel costruttore!
    public MediaController(SessionController sessionCtrl) {
        this.sessionCtrl = sessionCtrl;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            this.mediaDao = new ElementoMultimedialeDAO_Impl(conn);
        } catch (Exception e) {
            System.err.println("Errore: Impossibile collegare MediaController al DB");
            e.printStackTrace();
        }
    }

    public boolean caricaNuovoElemento(String titolo, String descrizione, String percorso, String tipo) {
        // 1. Chiedo al Session chi è l'utente. Se è null, blocco l'operazione.
        Utente proprietario = sessionCtrl.getUtenteLoggato();
        if (proprietario == null) {
            System.err.println("Errore: Nessun utente loggato. Impossibile fare l'upload.");
            return false;
        }

        // 2. Creo l'Entity corretta (Dati dummy per durata, data e bitrate/risoluzione per ora)
        ElementoMultimediale nuovoElemento;
        if (tipo.equals("Audio")) {
            nuovoElemento = new Audio(0, descrizione, titolo, 200, java.time.LocalDate.now(), percorso, proprietario, 320);
        } else {
            nuovoElemento = new Video(0, descrizione, titolo, 200, java.time.LocalDate.now(), percorso, proprietario, "1080p");
        }

        // 3. Passo tutto al DAO del tuo compagno
        int idGenerato = mediaDao.salvaContenuto(nuovoElemento);
        return idGenerato > 0;
    }

    public List<ElementoMultimediale> filtraElementi(String testoDaCercare, String tipo) {
        // Il Controller fa da ponte. Qui potresti aggiungere logiche di business 
        return mediaDao.cercaElementi(testoDaCercare, tipo);
    }
}