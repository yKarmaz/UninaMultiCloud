package mainApp;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import databaseConnection.DBConnection;
import entities.*;
import DAO.*;
import DAO.impl.*;
import controllers.*;
import boundaries.*; 

public class Main {

    public static void main(String[] args) {
    	
    	
        System.out.println("Avvio dell'applicazione UninaMultiCloud...");
        
        // Deleghiamo l'avvio della grafica al thread corretto (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            
            // 1. Accendiamo il "cervello" iniziale: il SessionController
            SessionController sessionController = new SessionController();
            
            // 2. Chiediamo al controller di mostrare la prima Boundary (il Login)
            sessionController.mostraFinestraLogin();
            
        });
        
        /*System.out.println("Avvio dell'applicazione UninaMultiCloud...");

        /* 
        // =========================================================================
        // CODICE SERVER: TEST INTERFACCIA GRAFICA (Scommenta per testare la UI)
        // =========================================================================
        JFrame frameTest = new JFrame("Test Grafica: Carica Elemento");
        frameTest.setSize(800, 600);
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTest.setLocationRelativeTo(null);

        SessionController fakeSession = new SessionController();
        PlaylistController fakePlaylistCtrl = new PlaylistController(fakeSession);
        MediaController fakeMediaCtrl = new MediaController();

        JPanel vistaDaTestare = new PaginaFiltraggio(fakeMediaCtrl, fakePlaylistCtrl, null);

        frameTest.add(vistaDaTestare);
        frameTest.setVisible(true);
        */

        // =========================================================================
        // TEST DAO PLAYLIST
        // =========================================================================
        /*try (Connection conn = DBConnection.getInstance().getConnection()) {
            
            System.out.println("--- Connessione al DB stabilita con successo! ---\n");
            
            // Inizializziamo il DAO passandogli la connessione
            /*
            PlaylistDao playlistDao = new PlaylistDAO_Impl(conn);
            
            // Creiamo un utente fittizio (proprietario) che esiste già sul tuo DB con ID 2
            Utente proprietarioTest = new Utente(0, null, null, null, null, null, null);
            proprietarioTest.setIdUtente(2); 
            
            System.out.println("=========================================");
            System.out.println("1. TEST INSERIMENTO (salvaPlaylist)");
            System.out.println("=========================================");
            
            // A. Creiamo e salviamo una Playlist Privata
            Playlist privata = new PlaylistPrivata(0, "I miei preferiti", proprietarioTest);
            System.out.println("[RAM prima del salvataggio] Privata ID: " + privata.getID());
            privata = playlistDao.salvaPlaylist(privata);
            System.out.println("[DB dopo il salvataggio] Privata Salvata! Nuovo ID: " + privata.getID());
            System.out.println("-----------------------------------------");

            // B. Creiamo e salviamo una Playlist Pubblica
            Playlist pubblica = new PlaylistPubblica(0, "Hit del Momento", proprietarioTest, "Musica");
            pubblica = playlistDao.salvaPlaylist(pubblica);
            System.out.println("[DB dopo il salvataggio] Pubblica Salvata! Nuovo ID: " + pubblica.getID());
            System.out.println("-----------------------------------------");

            // C. Creiamo e salviamo una Playlist Condivisa
            Playlist condivisa = new PlaylistCondivisa(0, "Lavoro di Gruppo", proprietarioTest);
            condivisa = playlistDao.salvaPlaylist(condivisa);
            System.out.println("[DB dopo il salvataggio] Condivisa Salvata! Nuovo ID: " + condivisa.getID());
            
            System.out.println("\n=========================================");
            System.out.println("2. TEST RICERCA (trovaPlaylist)");
            System.out.println("=========================================");
            
            // Proviamo a cercare la playlist condivisa appena creata per vedere se legge l'URL autogenerato
            Playlist fintaPlaylistPerRicerca = new PlaylistCondivisa(condivisa.getID(), "h", null);
            Playlist cercata = playlistDao.trovaPlaylist(fintaPlaylistPerRicerca);
            
            if (cercata != null) {
                System.out.println("Playlist Trovata sul DB!");
                System.out.println("- Nome: " + cercata.getNome());
                System.out.println("- Tipo Classe reale: " + cercata.getClass().getSimpleName());
                
                if (cercata instanceof PlaylistCondivisa) {
                    PlaylistCondivisa pc = (PlaylistCondivisa) cercata;
                    System.out.println("- URL Invito Generato dal DB: " + pc.getURL_Invito());
                }
            } else {
                System.out.println("ERRORE: Playlist non trouvata nel DB!");
            }
            
            System.out.println("\n=========================================");
            System.out.println("3. TEST MODIFICA (modificaPlaylist)");
            System.out.println("=========================================");
            
            // Modifichiamo il nome della playlist privata e trasformiamola in Pubblica
            System.out.println("Modifico la playlist privata ID " + privata.getID() + " cambiandole nome...");
            privata.setNome("Rock Classico anni 80");
            
            // Proviamo anche a fare una conversione di tipo estrema: la rendiamo pubblica nel codice
            Playlist convertitaInPubblica = new PlaylistPubblica(privata.getID(), "Rock Classico anni 80", proprietarioTest, "Musica");
            
            boolean esitoModifica = playlistDao.modificaPlaylist(convertitaInPubblica);
            System.out.println("Esito modifica (conversione in pubblica): " + esitoModifica);
            
            // Verifichiamo se sul DB è cambiata davvero cercandola
            Playlist verificaModifica = playlistDao.trovaPlaylist(convertitaInPubblica);
            if (verificaModifica != null) {
                System.out.println("Verifica dopo la modifica:");
                System.out.println("- Nuovo Nome sul DB: " + verificaModifica.getNome());
                System.out.println("- Nuova Classe sul DB (dovrebbe essere PlaylistPubblica): " + verificaModifica.getClass().getSimpleName());
            }
            
            System.out.println("\n=========================================");
            System.out.println("4. TEST CANCELLAZIONE (cancellaPlaylist)");
            System.out.println("=========================================");
            
            // Proviamo a cancellare la playlist condivisa
            System.out.println("Cancellazione della playlist condivisa ID: " + condivisa.getID());
            boolean esitoCancellazione = playlistDao.cancellaPlaylist(condivisa);
            System.out.println("Esito cancellazione: " + esitoCancellazione);
            
            // Verifichiamo che sia sparita davvero
            Playlist verificaCancellata = playlistDao.trovaPlaylist(condivisa);
            System.out.println("Verifica ricerca post-cancellazione (Dovrebbe essere null): " + verificaCancellata);
            System.out.println("=========================================");

            
         // =========================================================================
            // TEST DAO FRUIZIONE
            // =========================================================================
            System.out.println("\n\n=========================================");
            System.out.println("            TEST DAO FRUIZIONE           ");
            System.out.println("=========================================");
            
            // Inizializziamo il DAO delle fruizioni
            FruizioneDao fruizioneDao = new FruizioneDAO_Impl(conn);
            
            // Definiamo i dati fittizi per il test
            // ⚠️ NOTA: IDUtente 2 esiste già (usato sopra). Assicurati che esista anche un IDElemento = 1 nel DB!
            int idUtenteTest = 2; 
            int idElementoTest = 1; 
            java.time.LocalDateTime dataOraTest = java.time.LocalDateTime.now();
            
            System.out.println("=========================================");
            System.out.println("1. TEST INSERIMENTO (SalvaFruizione)");
            System.out.println("=========================================");
            
            Fruizione nuovaFruizione = new Fruizione(idUtenteTest, idElementoTest, dataOraTest);
            System.out.println("[RAM prima del salvataggio] Creata fruizione per Utente: " + idUtenteTest + ", Elemento: " + idElementoTest);
            
            Fruizione fruizioneSalvata = fruizioneDao.SalvaFruizione(nuovaFruizione);
            if (fruizioneSalvata != null) {
                System.out.println("[DB dopo il salvataggio] ✅ Fruizione salvata con successo!");
                System.out.println("- Data registrata: " + fruizioneSalvata.getDataFruizione());
            } else {
                System.out.println("[DB dopo il salvataggio] ❌ ERRORE: Salvataggio fallito!");
            }
            
            System.out.println("\n=========================================");
            System.out.println("2. TEST RICERCA (trovaFruizione)");
            System.out.println("=========================================");
            
            // Cerchiamo la fruizione appena inserita usando i suoi tre attributi identificativi
            Fruizione fruizioneCercata = fruizioneDao.trovaFruizione(nuovaFruizione);
            if (fruizioneCercata != null) {
                System.out.println("✅ Fruizione Trovata sul DB!");
                System.out.println("- ID Utente: " + fruizioneCercata.getIdUtente());
                System.out.println("- ID Elemento: " + fruizioneCercata.getIdElemento());
                System.out.println("- Data e Ora Fruizione: " + fruizioneCercata.getDataFruizione());
            } else {
                System.out.println("❌ ERRORE: Fruizione non trovata nel DB!");
            }
            
            System.out.println("\n=========================================");
            System.out.println("3. TEST MODIFICA (modificaFruizione)");
            System.out.println("=========================================");
            
            // Simuliamo una modifica spostando il timestamp in avanti di due ore
            java.time.LocalDateTime nuovaDataOra = dataOraTest.plusHours(2);
            Fruizione fruizioneModificata = new Fruizione(idUtenteTest, idElementoTest, nuovaDataOra);
            
            System.out.println("Sposto l'orario della fruizione in avanti di 2 ore...");
            Fruizione esitoModificaFruizione = fruizioneDao.modificaFruizione(nuovaFruizione, fruizioneModificata);
            
            if (esitoModificaFruizione != null) {
                System.out.println("✅ Modifica riuscita! Nuova data su RAM: " + esitoModificaFruizione.getDataFruizione());
                
                // Verifichiamo sul DB l'avvenuto cambio cercando lo stato NUOVO
                Fruizione verificaModificaFruizione = fruizioneDao.trovaFruizione(fruizioneModificata);
                if (verificaModificaFruizione != null) {
                    System.out.println("[DB] Verifica post-modifica: Trovata con successo al nuovo orario -> " + verificaModificaFruizione.getDataFruizione());
                } else {
                    System.out.println("[DB] ❌ ERRORE: La modifica non risulta persistita sul Database!");
                }
            } else {
                System.out.println("❌ ERRORE: Il metodo modificaFruizione ha restituito null!");
            }
            
            System.out.println("\n=========================================");
            System.out.println("4. TEST CANCELLAZIONE (cancellaFruizione)");
            System.out.println("=========================================");
            
            // Decidiamo quale stato cancellare in base a com'è andato l'update precedente
            Fruizione daCancellare = (esitoModificaFruizione != null) ? fruizioneModificata : nuovaFruizione;
            
            System.out.println("Cancellazione del record storico dal DB...");
            boolean esitoCancellaFruizione = fruizioneDao.cancellaFruizione(daCancellare);
            System.out.println("Esito cancellazione: " + esitoCancellaFruizione);
            
            // Verifica finale di svuotamento
            Fruizione verificaFruizioneCancellata = fruizioneDao.trovaFruizione(daCancellare);
            System.out.println("Verifica ricerca post-cancellazione (Dovrebbe essere null): " + verificaFruizioneCancellata);
            System.out.println("=========================================");
            */
            
            
        /*    
        } catch (SQLException e) {
            System.err.println("Errore di connessione o esecuzione SQL durante il test:");
            e.printStackTrace();
        }
        
        */
        
    }
}