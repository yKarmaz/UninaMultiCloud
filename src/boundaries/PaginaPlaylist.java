package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.PlaylistController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Importa le tue entity
// import entities.Playlist;
// import entities.ElementoMultimediale;

public class PaginaPlaylist extends JPanel {

    private PlaylistController playlistController;
    private HomePage homePage;
    
    // ⚠️ IN FUTURO: Questa classe dovrà ricevere la Playlist specifica che l'utente ha cliccato
    // private Playlist playlistSelezionata;

    private JTable tabellaBrani;
    private DefaultTableModel modelloTabella;

    /**
     * Costruttore
     * ⚠️ IN FUTURO: Aggiungi "Playlist playlistSelezionata" tra i parametri
     */
    public PaginaPlaylist(PlaylistController playlistController, HomePage homePage) {
        this.playlistController = playlistController;
        this.homePage = homePage;
        // this.playlistSelezionata = playlistSelezionata;
        
        inizializzaInterfaccia();
        popolaTabellaConDatiFake(); // Da sostituire poi con i dati veri
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER (Tasto Indietro + Titolo) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        
        JButton btnIndietro = new JButton("< Indietro");
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        
        // ⚠️ IN FUTURO: Il testo sarà playlistSelezionata.getNome()
        JLabel lblTitolo = new JLabel("Dettaglio Playlist: Nome Della Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 20));
        pnlHeader.add(lblTitolo, BorderLayout.CENTER);
        
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. TABELLA (Centro) ---
        // Il Modello gestisce i DATI, la JTable gestisce la GRAFICA
        String[] colonne = {"Titolo", "Autore", "Durata", "Tipo"};
        
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce all'utente di modificare il testo scrivendo nella tabella
            }
        };
        
        tabellaBrani = new JTable(modelloTabella);
        tabellaBrani.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleziona solo una riga alla volta
        
        // JScrollPane è vitale, altrimenti l'intestazione delle colonne scompare!
        JScrollPane scrollPane = new JScrollPane(tabellaBrani);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. FOOTER (Tasto Riproduci) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRiproduci = new JButton("Riproduci Selezionato");
        btnRiproduci.setBackground(new Color(46, 204, 113));
        btnRiproduci.setForeground(Color.WHITE);
        pnlFooter.add(btnRiproduci);
        
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI
        // ==========================================

        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ⚠️ IN FUTURO: Invece di un pannello vuoto, dovrai dirgli di ricaricare 
                // la classe "LeMiePlaylistView" (che farai a breve)
                homePage.cambiaPannelloCentrale(new JPanel()); 
            }
        });

        btnRiproduci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaBrani.getSelectedRow();
                
                // Validazione: controlla se l'utente ha effettivamente cliccato su una riga
                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(PaginaPlaylist.this, 
                        "Seleziona prima un brano dalla lista!", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String titoloSelezionato = (String) modelloTabella.getValueAt(rigaSelezionata, 0);
                
                // ⚠️ IN FUTURO: Qui chiamerai il MediaController per aprire il JDialog (Modale) di riproduzione
                // mediaController.apriPlayer(elementoSelezionato);

                JOptionPane.showMessageDialog(PaginaPlaylist.this, 
                    "Simulazione Riproduzione in corso per: " + titoloSelezionato, "Player", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Metodo per testare la grafica oggi.
     * ⚠️ IN FUTURO: Svuoterai questo metodo e farai un ciclo FOR 
     * sulla lista 'playlistSelezionata.getElementi()' per aggiungere le righe.
     */
    private void popolaTabellaConDatiFake() {
        // Aggiungiamo righe finte al modello
        modelloTabella.addRow(new Object[]{"Bohemian Rhapsody", "Queen", "05:55", "Audio"});
        modelloTabella.addRow(new Object[]{"Stairway to Heaven", "Led Zeppelin", "08:02", "Audio"});
        modelloTabella.addRow(new Object[]{"Live at Wembley", "Queen", "1:50:00", "Video"});
    }
}