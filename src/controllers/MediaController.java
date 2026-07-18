package controllers;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import DAO.ElementoMultimedialeDao;
import DAO.FruizioneDao;
import DAO.impl.ElementoMultimedialeDAO_Impl;
import DAO.impl.FruizioneDAO_Impl;
import databaseConnection.DBConnection;
import entities.Audio;
import entities.Video;
import entities.Fruizione;
import entities.ElementoMultimediale;
import entities.Utente;
//commento
public class MediaController {
    private ElementoMultimedialeDao mediaDao;
    private FruizioneDao fruizioneDao; // Serve per registrare gli ascolti
    private SessionController sessionCtrl;

    public MediaController(SessionController sessionCtrl) {
        this.sessionCtrl = sessionCtrl;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            this.mediaDao = new ElementoMultimedialeDAO_Impl(conn);
            this.fruizioneDao = new FruizioneDAO_Impl(conn);
        } catch (Exception e) {
            System.err.println("Errore: Impossibile collegare MediaController al DB");
            e.printStackTrace();
        }
    }

    public boolean caricaNuovoElemento(String titolo, String descrizione, String percorso, String tipo) {
        Utente proprietario = sessionCtrl.getUtenteLoggato();
        if (proprietario == null) {
            System.err.println("Errore: Nessun utente loggato. Impossibile fare l'upload.");
            return false;
        }

        ElementoMultimediale nuovoElemento;
        // Valori dummy per durata e bitrate. In un progetto finito si estraggono dai metadati del file.
        if (tipo.equals("Audio")) {
            nuovoElemento = new Audio(0, descrizione, titolo, 200, java.time.LocalDate.now(), percorso, proprietario, 320);
        } else {
            nuovoElemento = new Video(0, descrizione, titolo, 200, java.time.LocalDate.now(), percorso, proprietario, "1080p");
        }

        int idGenerato = mediaDao.salvaContenuto(nuovoElemento);
        return idGenerato > 0;
    }

    public List<ElementoMultimediale> filtraElementi(String testoDaCercare, String tipo) {
        return mediaDao.cercaElementi(testoDaCercare, tipo);
    }
    
    public void registraFruizione(ElementoMultimediale el) {
        Utente u = sessionCtrl.getUtenteLoggato();	
        System.out.println("DEBUG INSERIMENTO DI FRUIZIONE: ID Utente: " + u.getIdUtente());
        System.out.println("DEBUG INSERIMENTO DI FRUIZIONE: ID Elemento: " + el.getIdElemento());
        if(u != null) {
            Fruizione f = new Fruizione(el.getIdElemento(), u.getIdUtente(), LocalDateTime.now());
            fruizioneDao.SalvaFruizione(f);
            System.out.println("Fruizione registrata nel database per il brano: " + el.getTitolo());
        }
    }
}