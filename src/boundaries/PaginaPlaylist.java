package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controllers.*;
import entities.*;

public class PaginaPlaylist extends JPanel {
    private PlaylistController playlistController;
    private MediaController mediaController;
    private HomePage homePage;
    
    private Playlist playlistCorrente; // L'oggetto reale
    private JPanel pannelloPrecedente;
    
    private JTable tabellaBrani;
    private DefaultTableModel modelloTabella;
    private List<ElementoMultimediale> listaBraniReali;

    public PaginaPlaylist(PlaylistController playCtrl, MediaController mediaCtrl, HomePage home, Playlist p, JPanel prev) {
        this.playlistController = playCtrl;
        this.mediaController = mediaCtrl;
        this.homePage = home;
        this.playlistCorrente = p;
        this.pannelloPrecedente = prev;
        
        inizializzaInterfaccia();
        caricaBraniDalDB();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        JLabel lblTitolo = new JLabel("Playlist: " + playlistCorrente.getNome(), SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 24));
        
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        pnlHeader.add(lblTitolo, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        String[] colonne = {"Titolo", "Tipologia", "Durata"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabellaBrani = new JTable(modelloTabella);
        add(new JScrollPane(tabellaBrani), BorderLayout.CENTER);

        JButton btnApri = new JButton("Apri Brano Selezionato");
        add(btnApri, BorderLayout.SOUTH);

        btnIndietro.addActionListener(e -> homePage.cambiaPannelloCentrale(pannelloPrecedente));

        btnApri.addActionListener(e -> {
            int riga = tabellaBrani.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un brano!");
                return;
            }
            ElementoMultimediale el = listaBraniReali.get(riga);
            // Salto alla pagina del singolo elemento, passando this come precedente
            homePage.cambiaPannelloCentrale(new PaginaElemento(mediaController, playlistController, homePage, el, this));
        });
    }

    private void caricaBraniDalDB() {
        modelloTabella.setRowCount(0);
        
        listaBraniReali = playlistController.getBraniPlaylist(playlistCorrente);
        
        for(ElementoMultimediale el : listaBraniReali) {
            String tipo = (el instanceof Audio) ? "Audio" : "Video";
            modelloTabella.addRow(new Object[]{el.getTitolo(), tipo, el.getDurata()});
        }
    }
}