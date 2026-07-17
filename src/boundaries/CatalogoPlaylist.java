package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.MediaController;
import controllers.PlaylistController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatalogoPlaylist extends JPanel {

    private PlaylistController playlistController;
    private MediaController mediaController;
    private HomePage homePage;

    private JTabbedPane pannelloSchede;
    private JTable tabellaPrivate;
    private JTable tabellaPubbliche;
    private JTable tabellaCondivise;

    public CatalogoPlaylist(PlaylistController playlistController, MediaController mediaController, HomePage homePage) {
        this.playlistController = playlistController;
        this.mediaController = mediaController; // <-- Aggiunto
        this.homePage = homePage;
        
        inizializzaInterfaccia();
        
        // ⚠️ IN FUTURO: Qui chiamerai un metodo come "caricaLeMiePlaylist()" 
        // che interrogherà il Controller per farsi dare 3 liste separate dal DB.
        popolaTabelleConDatiFake(); 
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        JLabel lblTitolo = new JLabel("Il Mio Catalogo Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        pnlHeader.add(lblTitolo, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. SCHEDE (JTabbedPane) ---
        pannelloSchede = new JTabbedPane();
        pannelloSchede.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // Metodo di supporto per non riscrivere 3 volte la creazione della tabella
        tabellaPrivate = creaTabellaStandard();
        tabellaPubbliche = creaTabellaStandard();
        tabellaCondivise = creaTabellaStandard();

        // Aggiungiamo le schede al pannello
        pannelloSchede.addTab("🔒 Private", new JScrollPane(tabellaPrivate));
        pannelloSchede.addTab("🌍 Pubbliche", new JScrollPane(tabellaPubbliche));
        pannelloSchede.addTab("🔗 Condivise", new JScrollPane(tabellaCondivise));

        add(pannelloSchede, BorderLayout.CENTER);

        // --- 3. FOOTER (Tasto Apri) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnApri = new JButton("Apri Playlist Selezionata");
        btnApri.setBackground(new Color(52, 152, 219)); // Azzurro
        btnApri.setForeground(Color.WHITE);
        btnApri.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        pnlFooter.add(btnApri);
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI
        // ==========================================

        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.cambiaPannelloCentrale(new JPanel()); // Torna alla home vuota
            }
        });

        btnApri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Capiamo quale scheda sta guardando l'utente
                JTable tabellaAttiva = ottieniTabellaAttiva();
                
                int rigaSelezionata = tabellaAttiva.getSelectedRow();

                if (rigaSelezionata == -1) {
                    JOptionPane.showMessageDialog(CatalogoPlaylist.this, 
                        "Seleziona una playlist dalla tabella prima di aprirla!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nomePlaylist = (String) tabellaAttiva.getValueAt(rigaSelezionata, 0);

                // ⚠️ IN FUTURO: 
                // 1. Recupererai l'OGGETTO Playlist (tramite ID o tramite la lista in RAM del Controller).
                // 2. Creerai la PaginaPlaylist passandogli quell'oggetto.
                // Esempio fittizio:
                // Playlist p = playlistController.getPlaylistPerNome(nomePlaylist);
                
                // Per ora, apriamo semplicemente l'interfaccia finta di prima
                PaginaPlaylist vistaDettaglio = new PaginaPlaylist(playlistController, mediaController, homePage);
                homePage.cambiaPannelloCentrale(vistaDettaglio);
            }
        });
    }

    /**
     * Metodo di servizio (DRY: Don't Repeat Yourself). 
     * Crea una tabella standard vuota pronta da riempire.
     */
    private JTable creaTabellaStandard() {
        String[] colonne = {"Nome Playlist", "Data Creazione", "N° Brani"};
        DefaultTableModel modello = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        JTable tabella = new JTable(modello);
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabella.setRowHeight(25);
        return tabella;
    }

    /**
     * Capisce quale tab è attualmente aperta per sapere in quale tabella cercare la selezione
     */
    private JTable ottieniTabellaAttiva() {
        int indiceTab = pannelloSchede.getSelectedIndex();
        if (indiceTab == 0) return tabellaPrivate;
        if (indiceTab == 1) return tabellaPubbliche;
        return tabellaCondivise;
    }

    private void popolaTabelleConDatiFake() {
        // Riempiamo la tabella Private
        DefaultTableModel modPrivate = (DefaultTableModel) tabellaPrivate.getModel();
        modPrivate.addRow(new Object[]{"Studio Notturno", "14/07/2026", "12"});
        modPrivate.addRow(new Object[]{"Palestra", "10/06/2026", "45"});

        // Riempiamo la tabella Pubbliche
        DefaultTableModel modPubbliche = (DefaultTableModel) tabellaPubbliche.getModel();
        modPubbliche.addRow(new Object[]{"Rock Classico", "01/01/2026", "104"});

        // Riempiamo la tabella Condivise
        DefaultTableModel modCondivise = (DefaultTableModel) tabellaCondivise.getModel();
        modCondivise.addRow(new Object[]{"Viaggio in Auto", "05/05/2026", "32"});
    }
}