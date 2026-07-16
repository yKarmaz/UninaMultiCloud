package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.MediaController;
import controllers.PlaylistController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import entities.ElementoMultimediale; // ⚠️ IN FUTURO: Ti servirà per la lista reale

public class CatalogoElementi extends JPanel {

    private MediaController mediaController;
    private PlaylistController playlistController; // Serve da passare alla PaginaElemento
    private HomePage homePage;

    private JTextField txtRicerca;
    private JTable tabellaElementi;
    private DefaultTableModel modelloTabella;

    public CatalogoElementi(MediaController mediaController, PlaylistController playlistController, HomePage homePage) {
        this.mediaController = mediaController;
        this.playlistController = playlistController;
        this.homePage = homePage;
        
        inizializzaInterfaccia();
        
        // Al primo avvio mostriamo tutto il catalogo finto
        popolaTabella(mediaController.cercaBrani("")); 
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER E BARRA DI RICERCA ---
        JPanel pnlTop = new JPanel(new BorderLayout(10, 10));
        
        JPanel pnlTitolo = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        JLabel lblTitolo = new JLabel("Esplora Catalogo", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 22));
        pnlTitolo.add(btnIndietro, BorderLayout.WEST);
        pnlTitolo.add(lblTitolo, BorderLayout.CENTER);

        JPanel pnlRicerca = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlRicerca.add(new JLabel("Cerca Autore o Titolo: "));
        txtRicerca = new JTextField(20);
        JButton btnFiltra = new JButton("Filtra");
        pnlRicerca.add(txtRicerca);
        pnlRicerca.add(btnFiltra);

        pnlTop.add(pnlTitolo, BorderLayout.NORTH);
        pnlTop.add(pnlRicerca, BorderLayout.SOUTH);
        add(pnlTop, BorderLayout.NORTH);

        // --- 2. TABELLA RISULTATI ---
        String[] colonne = {"Titolo", "Autore", "Tipologia"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabellaElementi = new JTable(modelloTabella);
        tabellaElementi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabellaElementi.setRowHeight(25);
        
        add(new JScrollPane(tabellaElementi), BorderLayout.CENTER);

        // --- 3. FOOTER (Tasto Apri) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnApri = new JButton("Vedi Dettagli Elemento");
        btnApri.setBackground(new Color(52, 152, 219));
        btnApri.setForeground(Color.WHITE);
        pnlFooter.add(btnApri);
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // EVENTI
        // ==========================================

        btnIndietro.addActionListener(e -> homePage.cambiaPannelloCentrale(new JPanel()));

        btnFiltra.addActionListener(e -> {
            String testo = txtRicerca.getText().trim();
            // Deleghiamo la ricerca al Controller
            // ⚠️ IN FUTURO: mediaController.cercaBrani(testo) ti restituirà una List<ElementoMultimediale> vera
            popolaTabella(null); // Passo null per ora perché stiamo simulando
        });

        btnApri.addActionListener(e -> {
            int riga = tabellaElementi.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un elemento!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String titoloSelezionato = (String) modelloTabella.getValueAt(riga, 0);

            // ⚠️ IN FUTURO: Recupererai l'OGGETTO ElementoMultimediale vero per passarlo alla view successiva.
            // ElementoMultimediale el = listaRisultati.get(riga);
            
            PaginaElemento dettaglio = new PaginaElemento(mediaController, playlistController, homePage, titoloSelezionato);
            homePage.cambiaPannelloCentrale(dettaglio);
        });
    }

    // ⚠️ IN FUTURO: Il parametro sarà List<ElementoMultimediale> risultati
    private void popolaTabella(Object fakeParam) {
        modelloTabella.setRowCount(0); // Svuota la tabella precedente
        
        // Dati finti per provare la grafica
        modelloTabella.addRow(new Object[]{"Bohemian Rhapsody", "Queen", "Audio"});
        modelloTabella.addRow(new Object[]{"Thriller", "Michael Jackson", "Audio"});
        modelloTabella.addRow(new Object[]{"Concerto Live 2026", "Vasco Rossi", "Video"});
    }
}