
package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controllers.*;
import entities.Playlist;
import entities.PlaylistPubblica;

public class CatalogoPubbliche extends JPanel {
    private PlaylistController playlistController;
    private MediaController mediaController;
    private HomePage homePage;
    
    private JTable tabellaPubbliche;
    private DefaultTableModel modelloTabella;
    private List<Playlist> listaPubblicheGlobali;

    public CatalogoPubbliche(PlaylistController playCtrl, MediaController mediaCtrl, HomePage home) {
        this.playlistController = playCtrl;
        this.mediaController = mediaCtrl;
        this.homePage = home;
        
        inizializzaInterfaccia();
        caricaPubbliche();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        JLabel lblTitolo = new JLabel("Esplora Playlist Pubbliche", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        pnlHeader.add(lblTitolo, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        String[] colonne = {"Nome Playlist", "Autore", "Categoria"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        tabellaPubbliche = new JTable(modelloTabella);
        tabellaPubbliche.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaPubbliche.setRowHeight(25);
        add(new JScrollPane(tabellaPubbliche), BorderLayout.CENTER);

        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnApri = new JButton("Apri Playlist");
        btnApri.setBackground(new Color(52, 152, 219));
        btnApri.setForeground(Color.WHITE);
        pnlFooter.add(btnApri);
        add(pnlFooter, BorderLayout.SOUTH);

        // EVENTI
        btnIndietro.addActionListener(e -> {
            // Torna alla pagina di filtraggio elementi
            homePage.cambiaPannelloCentrale(new PaginaFiltraggio(mediaController, playlistController, homePage));
        });

        btnApri.addActionListener(e -> {
            int riga = tabellaPubbliche.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una playlist!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Playlist selezionata = listaPubblicheGlobali.get(riga);
            homePage.cambiaPannelloCentrale(new PaginaPlaylist(playlistController, mediaController, homePage, selezionata, this));
        });
    }

    private void caricaPubbliche() {
        // Chiama il metodo che estrae TUTTE le pubbliche dal DB
        listaPubblicheGlobali = playlistController.getPlaylistPubbliche();
        modelloTabella.setRowCount(0);
        
        for(Playlist p : listaPubblicheGlobali) {
            String categoria = "N/A";
            if(p instanceof PlaylistPubblica) {
                categoria = ((PlaylistPubblica) p).getCategoria();
            }
            modelloTabella.addRow(new Object[]{p.getNome(), p.getProprietario().getUsername(), categoria});
        }
    }
}