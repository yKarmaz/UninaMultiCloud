package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controllers.*;
import entities.Playlist;

public class CatalogoPlaylist extends JPanel {
    private PlaylistController playlistController;
    private MediaController mediaController;
    private HomePage homePage;
    
    private JTabbedPane pannelloSchede;
    private JTable tabellaPrivate, tabellaPubbliche, tabellaCondivise;
    
    // FONDAMENTALE: Teniamo le liste vere in RAM per poter aprire la playlist cliccata
    private List<Playlist> listPrivate, listPubbliche, listCondivise;

    public CatalogoPlaylist(PlaylistController playlistController, MediaController mediaController, HomePage homePage) {
        this.playlistController = playlistController;
        this.mediaController = mediaController;
        this.homePage = homePage;
        inizializzaInterfaccia();
        caricaDatiReali();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitolo = new JLabel("Catalogo Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(lblTitolo, BorderLayout.NORTH);

        pannelloSchede = new JTabbedPane();
        tabellaPrivate = creaTabella();
        tabellaPubbliche = creaTabella();
        tabellaCondivise = creaTabella();

        pannelloSchede.addTab("Private", new JScrollPane(tabellaPrivate));
        pannelloSchede.addTab("Pubbliche", new JScrollPane(tabellaPubbliche));
        pannelloSchede.addTab("Condivise", new JScrollPane(tabellaCondivise));
        add(pannelloSchede, BorderLayout.CENTER);

        JButton btnApri = new JButton("Apri Playlist");
        add(btnApri, BorderLayout.SOUTH);

        btnApri.addActionListener(e -> {
            int indiceTab = pannelloSchede.getSelectedIndex();
            JTable tabellaAttiva = (indiceTab == 0) ? tabellaPrivate : ((indiceTab == 1) ? tabellaPubbliche : tabellaCondivise);
            List<Playlist> listaAttiva = (indiceTab == 0) ? listPrivate : ((indiceTab == 1) ? listPubbliche : listCondivise);

            int riga = tabellaAttiva.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una playlist!");
                return;
            }
            
            Playlist selezionata = listaAttiva.get(riga);
            // Passo la Playlist REALE alla view di dettaglio
            homePage.cambiaPannelloCentrale(new PaginaPlaylist(playlistController, mediaController, homePage, selezionata, this));
        });
    }

    private JTable creaTabella() {
        String[] colonne = {"Nome Playlist", "Autore"};
        DefaultTableModel mod = new DefaultTableModel(colonne, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable t = new JTable(mod);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return t;
    }

    private void caricaDatiReali() {
        // Popola Private
        listPrivate = playlistController.getMiePlaylistPrivate();
        DefaultTableModel modPriv = (DefaultTableModel) tabellaPrivate.getModel();
        modPriv.setRowCount(0);
        for(Playlist p : listPrivate) modPriv.addRow(new Object[]{p.getNome(), p.getProprietario().getUsername()});

     // Popola Pubbliche (SOLO LE TUE)
        listPubbliche = playlistController.getMiePlaylistPubbliche(); // <-- Usa il nuovo metodo!
        DefaultTableModel modPub = (DefaultTableModel) tabellaPubbliche.getModel();
        modPub.setRowCount(0);
        for(Playlist p : listPubbliche) {
            modPub.addRow(new Object[]{p.getNome(), p.getProprietario().getUsername()});
        }

        // Popola Condivise
        listCondivise = playlistController.getMiePlaylistCondivise();
        DefaultTableModel modCond = (DefaultTableModel) tabellaCondivise.getModel();
        modCond.setRowCount(0);
        for(Playlist p : listCondivise) modCond.addRow(new Object[]{p.getNome(), p.getProprietario().getUsername()});
    }
}