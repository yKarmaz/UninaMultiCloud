package mainApp;

import javax.swing.SwingUtilities;

import controllers.*; 

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
       
        
    }
}