package controllers;

import java.sql.Connection;

import DAO.ElementoMultimedialeDao;
import DAO.FruizioneDao;
import DAO.UtenteDao;
import DAO.impl.ElementoMultimedialeDAO_Impl;
import DAO.impl.FruizioneDAO_Impl;
import DAO.impl.UtenteDAO_Impl;
import databaseConnection.DBConnection;
import entities.Utente;

public class ReportController {
	private SessionController sessionCtrl;
	private UtenteDao utenteDao;
	private ElementoMultimedialeDao mediaDao;
	private FruizioneDao fruizioneDao;
	public ReportController(SessionController sessionCtrl) {
        this.sessionCtrl = sessionCtrl;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            this.utenteDao = new UtenteDAO_Impl(conn);
            this.mediaDao = new ElementoMultimedialeDAO_Impl(conn);
            this.fruizioneDao = new FruizioneDAO_Impl(conn);
        } catch (Exception e) {
            System.err.println("Errore: Impossibile collegare MediaController al DB");
            e.printStackTrace();
        }
    }
	public int getNumeroContenutiPubblicati(Utente u)
	{
		return utenteDao.contaContenutiPubblicati(u);
	}
	
	public int getNumeroElementiVisualizzati(Utente u)
	{
		return fruizioneDao.getFruizioniDiUtente(u);

	}
	
	public int getNumeroVisualizzazioni(Utente u)
	{
		return mediaDao.getSommaVisualizzazioni(u);
	}
	
	public Utente getUtenteLoggato()
	{
		return sessionCtrl.getUtenteLoggato();
	}
}
