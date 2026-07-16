/* package mainApp;

import java.sql.Connection;
import java.sql.SQLException;
import databaseConnection.DBConnection;
import controllers.SessionController;
import boundaries.LoginPage;

public class Main {

    public static void main(String[] args) {
        System.out.println("Avvio dell'applicazione UninaMultiCloud...");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SessionController session = new SessionController();
                session.mostraFinestraLogin();
            }
        });
    }
} 	

*/



package mainApp;

import javax.swing.JFrame;
import javax.swing.JPanel;

// Importa il Controller corretto per questa specifica Boundary!
import controllers.*;

// Importa la Boundary. (Nota: io l'avevo chiamata CaricaElementoView, 
// se tu l'hai chiamata solo CaricaElemento, togli il "View")
import boundaries.*; 

public class Main {
    public static void main(String[] args) {
        
        // 1. Creo una finestra finta al volo
        JFrame frameTest = new JFrame("Test Grafica: Carica Elemento");
        frameTest.setSize(800, 600);
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTest.setLocationRelativeTo(null);

     // 2. Istanzio TUTTI i controller necessari
        SessionController fakeSession = new SessionController();
        PlaylistController fakePlaylistCtrl = new PlaylistController(fakeSession);
        MediaController fakeMediaCtrl = new MediaController();

        // 3. Istanzio la vista (Scegli UNA di queste due righe)
        JPanel vistaDaTestare = new PaginaFiltraggio(fakeMediaCtrl, fakePlaylistCtrl, null);
        // JPanel vistaDaTestare = new PaginaFiltraggio(fakeMediaCtrl, fakePlaylistCtrl, null);

        // 4. Incollo la vista nel frame e la accendo
        frameTest.add(vistaDaTestare);
        frameTest.setVisible(true);
    }
}