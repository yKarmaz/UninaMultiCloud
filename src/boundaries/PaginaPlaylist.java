package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.MediaController;
import controllers.PlaylistController;

public class PaginaPlaylist extends JPanel {

    private PlaylistController playlistController;
    // ⚠️ IN FUTURO: Aggiungi il MediaController qui per poter avviare la riproduzione
    private MediaController mediaController;
    private JPanel homePage;
    
    private JTable tabellaBrani;
    private DefaultTableModel modelloTabella;

    public PaginaPlaylist(PlaylistController playCtrl, MediaController mediaCtrl, JPanel home) {
        this.playlistController = playCtrl;
        this.mediaController = mediaCtrl; 
        this.homePage = home;
        
        inizializzaInterfaccia();
        popolaTabellaConDatiFake(); 
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER (Strutturato su due righe) ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        
        // Riga 1: Tasto Indietro
        JPanel pnlBack = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton btnIndietro = new JButton("< Indietro");
        pnlBack.add(btnIndietro);
        
        // Riga 2: Titolo centrato
        JLabel lblTitolo = new JLabel("La Mia Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitolo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        
        pnlHeader.add(pnlBack);
        pnlHeader.add(lblTitolo);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. TABELLA (Centro) ---
        String[] colonne = {"Titolo", "Autore", "Durata", "Tipo"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabellaBrani = new JTable(modelloTabella);
        tabellaBrani.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaBrani.setRowHeight(25);
        
        add(new JScrollPane(tabellaBrani), BorderLayout.CENTER);

        // --- 3. FOOTER (Tasto Riproduci) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRiproduci = new JButton("Apri Brano Selezionato");
        btnRiproduci.setBackground(new Color(52, 152, 219));
        btnRiproduci.setForeground(Color.WHITE);
        btnRiproduci.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlFooter.add(btnRiproduci);
        
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI
        // ==========================================

        btnIndietro.addActionListener(e -> {
            System.out.println("Torno indietro al Catalogo Playlist...");
            // homePage.cambiaPannelloCentrale(new CatalogoPlaylist(playlistController, homePage));
        });

        btnRiproduci.addActionListener(e -> {
            int rigaSelezionata = tabellaBrani.getSelectedRow();
            if (rigaSelezionata == -1) {
                JOptionPane.showMessageDialog(PaginaPlaylist.this, 
                    "Seleziona prima un brano dalla lista!", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String titoloSelezionato = (String) modelloTabella.getValueAt(rigaSelezionata, 0);
            
            // ⚠️ IN FUTURO: DELEGA L'APERTURA. Passiamo 'this' come pannello precedente!
            // PaginaElemento dettaglio = new PaginaElemento(mediaController, playlistController, homePage, elementoReale, this);
            // homePage.cambiaPannelloCentrale(dettaglio);
            
            // LOGICA FAKE PER TEST
            PaginaElemento vistaDettaglio = new PaginaElemento(mediaController, playlistController, homePage, titoloSelezionato, this);
            
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.getContentPane().add(vistaDettaglio);
            topFrame.revalidate();
            topFrame.repaint();
        });
    }

    private void popolaTabellaConDatiFake() {
        modelloTabella.addRow(new Object[]{"Bohemian Rhapsody", "Queen", "05:55", "Audio"});
        modelloTabella.addRow(new Object[]{"Stairway to Heaven", "Led Zeppelin", "08:02", "Audio"});
        modelloTabella.addRow(new Object[]{"Live at Wembley", "Queen", "1:50:00", "Video"});
    }
}