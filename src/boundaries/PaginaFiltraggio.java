package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controllers.MediaController;
import controllers.PlaylistController;
import entities.ElementoMultimediale;
import entities.Audio;

public class PaginaFiltraggio extends JPanel {

    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage; // Usiamo la classe HomePage vera, non JPanel
    
    private JTextField txtTitolo;
    private JComboBox<String> comboTipo;
    private JTable tabellaElementi;
    private DefaultTableModel modelloTabella;

    // FONDAMENTALE: Teniamo i risultati veri in memoria per poterli cliccare
    private List<ElementoMultimediale> listaRisultati;

    public PaginaFiltraggio(MediaController mediaCtrl, PlaylistController playCtrl, HomePage home) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;

        inizializzaInterfaccia();
        
        // Lancio una ricerca vuota all'avvio per mostrare tutto il catalogo
        eseguiRicerca("", "Tutti");
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. SEZIONE ALTA: RICERCA E BOTTONE PLAYLIST ---
        JPanel pnlFiltri = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlFiltri.setBorder(BorderFactory.createTitledBorder("Filtra"));
        
        pnlFiltri.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField(15);
        pnlFiltri.add(txtTitolo);

        String[] tipiMedia = {"Tutti", "Audio", "Video"};
        comboTipo = new JComboBox<>(tipiMedia);
        pnlFiltri.add(comboTipo);

        JButton btnCerca = new JButton("Cerca");
        pnlFiltri.add(btnCerca);

        JButton btnPlaylist = new JButton("Le Mie Playlist");
        btnPlaylist.setBackground(new Color(46, 204, 113)); 
        btnPlaylist.setForeground(Color.WHITE);
        pnlFiltri.add(Box.createHorizontalStrut(20)); 
        pnlFiltri.add(btnPlaylist);

        add(pnlFiltri, BorderLayout.NORTH);

        // --- 2. SEZIONE CENTRALE: TABELLA RISULTATI ---
        String[] colonne = {"Titolo", "Autore", "Tipologia"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabellaElementi = new JTable(modelloTabella);
        tabellaElementi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaElementi.setRowHeight(25);
        add(new JScrollPane(tabellaElementi), BorderLayout.CENTER);

        // --- 3. SEZIONE BASSA: BOTTONI INDIETRO E DETTAGLI ---
        JPanel pnlFooter = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        pnlFooter.add(btnIndietro, BorderLayout.WEST);
        
        JButton btnApriElemento = new JButton("Apri Selezionato");
        pnlFooter.add(btnApriElemento, BorderLayout.EAST);
        add(pnlFooter, BorderLayout.SOUTH);

        // --- 4. EVENTI DEI BOTTONI ---
        
        btnIndietro.addActionListener(e -> {
            // Svuotiamo il pannello centrale per tornare alla dashboard pulita
            homePage.cambiaPannelloCentrale(new JPanel()); 
        });

        btnCerca.addActionListener(e -> {
            String testo = txtTitolo.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();
            eseguiRicerca(testo, tipo);
        });

        btnPlaylist.addActionListener(e -> {
            // Navigazione verso il Catalogo Playlist
            CatalogoPlaylist vistaPlaylist = new CatalogoPlaylist(playlistController, mediaController, homePage);
            homePage.cambiaPannelloCentrale(vistaPlaylist);
        });

        btnApriElemento.addActionListener(e -> {
            int riga = tabellaElementi.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(PaginaFiltraggio.this, "Seleziona un elemento!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Recuperiamo l'oggetto VERO (Audio o Video) dal database, non una banale stringa
            ElementoMultimediale elSelezionato = listaRisultati.get(riga);
            
            PaginaElemento vistaDettaglio = new PaginaElemento(mediaController, playlistController, homePage, elSelezionato, this);
            homePage.cambiaPannelloCentrale(vistaDettaglio);
            
            System.out.println("Elemento cliccato: " + elSelezionato.getTitolo() + " (ID: " + elSelezionato.getIdElemento() + ")");
        });
    }

     // Metodo per interrogare il DB e aggiornare la tabella
    private void eseguiRicerca(String filtroTesto, String filtroTipo) {
        modelloTabella.setRowCount(0); // Pulisce le vecchie righe

        // 1. Chiedo i dati reali al Controller
        this.listaRisultati = mediaController.filtraElementi(filtroTesto, filtroTipo);

        // 2. Popolo la vista
        for(ElementoMultimediale el : listaRisultati) {
            String tipo = (el instanceof Audio) ? "Audio" : "Video";
            String autore = (el.getProprietario() != null) ? el.getProprietario().getUsername() : "Sconosciuto";
            
            modelloTabella.addRow(new Object[]{el.getTitolo(), autore, tipo});
        }
    }
}