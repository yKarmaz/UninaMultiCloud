package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import controllers.*;
import entities.*;

public class PaginaFiltraggio extends JPanel {
    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage;
    private JTextField txtTitolo;
    private JComboBox<String> comboTipo;
    private JTable tabellaElementi;
    private DefaultTableModel modelloTabella;
    
    // FONDAMENTALE: Lista reale degli elementi in memoria
    private List<ElementoMultimediale> listaRisultati;

    public PaginaFiltraggio(MediaController mediaCtrl, PlaylistController playCtrl, HomePage home) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        inizializzaInterfaccia();
        eseguiRicerca("", "Tutti"); // Carica tutto all'inizio
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlFiltri = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFiltri.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField(15);
        pnlFiltri.add(txtTitolo);
        comboTipo = new JComboBox<>(new String[]{"Tutti", "Audio", "Video"});
        pnlFiltri.add(comboTipo);
        JButton btnCerca = new JButton("Cerca");
        pnlFiltri.add(btnCerca);
        add(pnlFiltri, BorderLayout.NORTH);

        String[] colonne = {"Titolo", "Autore", "Tipologia"};
        modelloTabella = new DefaultTableModel(colonne, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabellaElementi = new JTable(modelloTabella);
        add(new JScrollPane(tabellaElementi), BorderLayout.CENTER);

        JButton btnApriElemento = new JButton("Apri Selezionato");
        add(btnApriElemento, BorderLayout.SOUTH);

        btnCerca.addActionListener(e -> eseguiRicerca(txtTitolo.getText().trim(), (String) comboTipo.getSelectedItem()));

        btnApriElemento.addActionListener(e -> {
            int riga = tabellaElementi.getSelectedRow();
            if (riga == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un elemento!");
                return;
            }
            // Estraggo l'OGGETTO REALE e lo passo alla pagina di dettaglio, passando "this" per poter tornare indietro
            ElementoMultimediale elSelezionato = listaRisultati.get(riga);
            homePage.cambiaPannelloCentrale(new PaginaElemento(mediaController, playlistController, homePage, elSelezionato, this));
        });
    }

    private void eseguiRicerca(String filtroTesto, String filtroTipo) {
        modelloTabella.setRowCount(0);
        // Chiamata vera al DB tramite Controller
        this.listaRisultati = mediaController.filtraElementi(filtroTesto, filtroTipo);
        
        for(ElementoMultimediale el : listaRisultati) {
            String tipo = (el instanceof Audio) ? "Audio" : "Video";
            String autore = el.getProprietario() != null ? el.getProprietario().getUsername() : "N/A";
            modelloTabella.addRow(new Object[]{el.getTitolo(), autore, tipo});
        }
    }
}