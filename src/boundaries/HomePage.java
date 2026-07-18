import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.*;
public class HomePage extends JFrame {

    // Il controller principale per il logout e per avere i dati dell'utente
    private SessionController sessionController;
    
    // Gli altri controller a cui i bottoni dovranno mandare i comandi
    private MediaController mediaController;
    private PlaylistController playlistController;
    private ReportController reportController;

    // Il pannello centrale dove caricheremo dinamicamente le altre schermate
    private JPanel pannelloCentrale;

    /**
     * Costruttore: riceve i controller per non violare l'EBC
     */
    public HomePage(SessionController sessionController, MediaController mediaController, 
                    PlaylistController playlistController, ReportController reportController) {
        
        this.sessionController = sessionController;
        this.mediaController = mediaController;
        this.playlistController = playlistController;
        this.reportController = reportController;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setTitle("UninaMultiCloud - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra
        setLayout(new BorderLayout());

        // ==========================================
        // 1. SIDEBAR (Menu Laterale)
        // ==========================================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(7, 1, 10, 15)); // 7 righe, 1 colonna, con spaziatura
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setPreferredSize(new Dimension(220, 0)); // Larghezza fissa del menu

        // Creazione dei bottoni come dal tuo Mock-up
        JButton btnCerca = new JButton("Cerca / Filtra");
        JButton btnMiePlaylist = new JButton("Le Mie Playlist");
        JButton btnCreaPlaylist = new JButton("Crea Playlist");
        JButton btnNuovoElemento = new JButton("Nuovo Elemento");
        JButton btnReport = new JButton("Report Statistiche");
        JButton btnLogout = new JButton("Logout");
        btnLogout.setForeground(Color.RED); // Mettiamo il logout in rosso per far capire che è un'azione di uscita

        // Aggiungiamo i bottoni al menu laterale
        sidebar.add(new JLabel("Menu Principale", SwingConstants.CENTER));
        sidebar.add(btnCerca);
        sidebar.add(btnMiePlaylist);
        sidebar.add(btnCreaPlaylist);
        sidebar.add(btnNuovoElemento);
        sidebar.add(btnReport);
        sidebar.add(btnLogout);

        // ==========================================
        // 2. PANNELLO CENTRALE (Il contenitore dinamico)
        // ==========================================
        pannelloCentrale = new JPanel();
        pannelloCentrale.setLayout(new CardLayout()); // IL SEGRETO È QUI
        
        // Schermata di benvenuto di default
        JPanel pnlBenvenuto = new JPanel(new GridBagLayout());
        pnlBenvenuto.add(new JLabel("Benvenuto su UninaMultiCloud! Seleziona un'azione dal menu."));
        pannelloCentrale.add(pnlBenvenuto, "BENVENUTO");

        // Assembliamo la finestra
        add(sidebar, BorderLayout.WEST);
        add(pannelloCentrale, BorderLayout.CENTER);

        // ==========================================
        // 3. EVENTI DEI BOTTONI (Deleghe ai Controller)
        // ==========================================
        
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deleghiamo al SessionController la chiusura e il ritorno al Login
                sessionController.eseguiLogout(HomePage.this); 
            }
        });

        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Il controller si occupa di caricare i dati e dire a questa finestra di cambiare pannello
                mediaController.apriCatalogo(HomePage.this);
            }
        });

        btnMiePlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playlistController.apriMiePlaylist(HomePage.this);
            }
        });

        // (Puoi aggiungere gli altri ActionListener seguendo questa identica logica)
    }

    /**
     * Metodo pubblico che i Controller useranno per "iniettare" 
     * i nuovi JPanel al centro della finestra.
     */
    public void cambiaPannelloCentrale(JPanel nuovoPannello) {
        pannelloCentrale.removeAll(); // Pulisce il centro
        pannelloCentrale.add(nuovoPannello); // Inserisce la nuova vista (es. il Catalogo)
        pannelloCentrale.revalidate();
        pannelloCentrale.repaint(); // Aggiorna la grafica
    }
}