package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controllers.MediaController;
import controllers.PlaylistController;
import entities.ElementoMultimediale;
import entities.Audio;

public class CatalogoElementi extends JPanel {
    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage;

    private JTextField txtRicerca;
    private JTable tabellaElementi;
    private DefaultTableModel modelloTabella;
    
    
    private List<ElementoMultimediale> listaRisultati;

    public CatalogoElementi(MediaController mediaCtrl, PlaylistController playCtrl, HomePage homePage) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = homePage;
        
        inizializzaInterfaccia();
        
        // Al primo avvio carica tutto il catalogo
        eseguiRicerca(""); 
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
        pnlRicerca.add(new JLabel("Cerca Titolo: "));
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

        // --- 3. FOOTER ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnApri = new JButton("Vedi Dettagli Elemento");
        btnApri.setBackground(new Color(52, 152, 219));
        btnApri.setForeground(Color.WHITE);
        pnlFooter.add(btnApri);
        add(pnlFooter, BorderLayout.SOUTH);

        // --- 4. EVENTI ---
        
        btnIndietro.addActionListener(e -> {
            // Torna alla schermata vuota della Dashboard
            homePage.cambiaPannelloCentrale(new JPanel());
        });

        btnFiltra.addActionListener(e -> {
            // Lancia la ricerca passando il testo
            eseguiRicerca(txtRicerca.getText().trim());
        });

        btnApri.addActionListener(e -> {
            int riga = tabellaElementi.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un elemento!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            //Prendo l'oggetto e lo passa a PaginaElemento
            ElementoMultimediale elSelezionato = listaRisultati.get(riga);
            homePage.cambiaPannelloCentrale(new PaginaElemento(mediaController, playlistController, homePage, elSelezionato, this));
        });
    }

    private void eseguiRicerca(String testo) {
        modelloTabella.setRowCount(0); // Svuota i vecchi risultati
        
        // Uso lo stesso metodo di PaginaFiltraggio fissando il tipo su "Tutti"
        this.listaRisultati = mediaController.filtraElementi(testo, "Tutti");
        
        for(ElementoMultimediale el : listaRisultati) {
            String tipo = (el instanceof Audio) ? "Audio" : "Video";
            String autore = (el.getProprietario() != null) ? el.getProprietario().getUsername() : "Sconosciuto";
            
            modelloTabella.addRow(new Object[]{el.getTitolo(), autore, tipo});
        }
    }
}