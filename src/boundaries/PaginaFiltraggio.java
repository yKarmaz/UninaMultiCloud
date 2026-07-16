package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.MediaController;
import controllers.PlaylistController;
// import boundaries.HomePage; // ⚠️ IN FUTURO: Scommentare quando unisci col compagno

public class PaginaFiltraggio extends JPanel {

    private MediaController mediaController;
    private PlaylistController playlistController;
    private JPanel homePage; // ⚠️ IN FUTURO: Cambiare in HomePage

    private JTextField txtTitolo;
    private JComboBox<String> comboTipo;
    private JTable tabellaElementi;
    private DefaultTableModel modelloTabella;

    public PaginaFiltraggio(MediaController mediaCtrl, PlaylistController playCtrl, JPanel home) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        
        inizializzaInterfaccia();
        
        // Simulo una ricerca vuota all'avvio per riempire la tabella
        eseguiRicerca("", "Tutti"); 
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ==========================================
        // 1. SEZIONE ALTA: RICERCA E BOTTONE PLAYLIST
        // ==========================================
        JPanel pnlFiltri = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlFiltri.setBorder(BorderFactory.createTitledBorder("Filtra"));

        pnlFiltri.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField(15);
        pnlFiltri.add(txtTitolo);

        // La tendina con la "M" (Media) del tuo schema
        String[] tipiMedia = {"Tutti", "Audio", "Video"};
        comboTipo = new JComboBox<>(tipiMedia);
        pnlFiltri.add(comboTipo);
        
        JButton btnCerca = new JButton("Cerca");
        pnlFiltri.add(btnCerca);

        // Il famoso "Bottone Playlist" del tuo mock-up
        JButton btnPlaylist = new JButton("Bottone Playlist");
        btnPlaylist.setBackground(new Color(46, 204, 113)); // Verde per risaltare
        btnPlaylist.setForeground(Color.WHITE);
        pnlFiltri.add(Box.createHorizontalStrut(20)); // Spazio vuoto per distanziarlo
        pnlFiltri.add(btnPlaylist);

        add(pnlFiltri, BorderLayout.NORTH);

        // ==========================================
        // 2. SEZIONE CENTRALE: TABELLA RISULTATI
        // ==========================================
        String[] colonne = {"Item (Titolo)", "Autore", "Tipologia"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabellaElementi = new JTable(modelloTabella);
        tabellaElementi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaElementi.setRowHeight(25);
        
        add(new JScrollPane(tabellaElementi), BorderLayout.CENTER);

        // ==========================================
        // 3. SEZIONE BASSA: BOTTONI INDIETRO E DETTAGLI
        // ==========================================
        JPanel pnlFooter = new JPanel(new BorderLayout());
        
        JButton btnIndietro = new JButton("< Indietro");
        pnlFooter.add(btnIndietro, BorderLayout.WEST);

        JButton btnApriElemento = new JButton("Apri Selezionato");
        pnlFooter.add(btnApriElemento, BorderLayout.EAST);

        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI DEI BOTTONI
        // ==========================================

        // EVENTO: < Indietro
        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Torno indietro...");
                // ⚠️ IN FUTURO: homePage.cambiaPannelloCentrale(new JPanel());
            }
        });

        // EVENTO: Cerca
        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testo = txtTitolo.getText().trim();
                String tipo = (String) comboTipo.getSelectedItem();
                eseguiRicerca(testo, tipo);
            }
        });

        // EVENTO: Bottone Playlist (Il salto al Catalogo Playlist)
        btnPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Salto al Catalogo delle Playlist...");
                
                // ⚠️ IN FUTURO: Quando avrai l'HomePage vera, de-commenta queste righe.
                // Questa è la riga che crea il collegamento esatto della tua freccia!
                /*
                CatalogoPlaylist vistaPlaylist = new CatalogoPlaylist(playlistController, homePage);
                homePage.cambiaPannelloCentrale(vistaPlaylist);
                */
            }
        });

        // EVENTO: Apri l'elemento selezionato nella tabella
        btnApriElemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int riga = tabellaElementi.getSelectedRow();
                if (riga == -1) {
                    JOptionPane.showMessageDialog(PaginaFiltraggio.this, 
                        "Seleziona un elemento!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String titoloSelezionato = (String) modelloTabella.getValueAt(riga, 0);
                System.out.println("Apro i dettagli di: " + titoloSelezionato);
                
                // ⚠️ IN FUTURO: Qui recuperi l'elemento vero dal DB e lo passi alla PaginaElemento
                /*
                ElementoMultimediale el = listaRisultati.get(riga);
                PaginaElemento vistaDettaglio = new PaginaElemento(mediaController, playlistController, homePage, el);
                homePage.cambiaPannelloCentrale(vistaDettaglio);
                */
            }
        });
    }

    /**
     * Metodo per aggiornare la tabella
     */
    private void eseguiRicerca(String filtroTesto, String filtroTipo) {
        modelloTabella.setRowCount(0); 
        
        // ⚠️ IN FUTURO: DELEGA AL CONTROLLER
        // this.listaRisultati = mediaController.filtraElementi(filtroTesto, filtroTipo);
        // for(ElementoMultimediale el : listaRisultati) { modelloTabella.addRow(...); }

        System.out.println("Ricerca nel DB per Titolo: '" + filtroTesto + "' - Tipo: " + filtroTipo);
        
        // Dati di plastica per test
        modelloTabella.addRow(new Object[]{"Item 1", "Autore 1", "Audio"});
        modelloTabella.addRow(new Object[]{"Item 2", "Autore 2", "Video"});
        modelloTabella.addRow(new Object[]{"Item 3", "Autore 3", "Audio"});
    }
}