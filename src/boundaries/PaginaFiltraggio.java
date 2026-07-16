package boundaries;

import javax.swing.*;

import controllers.MediaController;
import controllers.PlaylistController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaFiltraggio extends JPanel {

    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage;

    // Componenti richiesti dal tuo mock-up
    private JTextField txtTitolo;
    private JComboBox<String> comboTipo;
    
    public PaginaFiltraggio(MediaController mediaCtrl, PlaylistController playCtrl, HomePage home) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // --- 1. HEADER (HomeButton) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnHome = new JButton("< Torna alla Home"); // Il tuo HomeButton
        JLabel lblTitoloView = new JLabel("Ricerca Avanzata Elementi", SwingConstants.CENTER);
        lblTitoloView.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        pnlHeader.add(btnHome, BorderLayout.WEST);
        pnlHeader.add(lblTitoloView, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. FORM DI FILTRAGGIO (Centro) ---
        JPanel pnlForm = new JPanel(new GridLayout(4, 1, 10, 15));
        
        // TitoloText e TitoloTextField
        pnlForm.add(new JLabel("Inserisci il Titolo da cercare:"));
        txtTitolo = new JTextField();
        pnlForm.add(txtTitolo);

        // ComboTipo
        pnlForm.add(new JLabel("Seleziona la Tipologia:"));
        String[] tipiContenuto = {"Tutti", "Audio", "Video"};
        comboTipo = new JComboBox<>(tipiContenuto);
        pnlForm.add(comboTipo);

        add(pnlForm, BorderLayout.CENTER);

        // --- 3. BOTTONI DI AZIONE (FiltraButton e PlaylistButton) ---
        JPanel pnlBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        
        JButton btnFiltra = new JButton("Applica Filtro e Cerca");
        btnFiltra.setBackground(new Color(52, 152, 219));
        btnFiltra.setForeground(Color.WHITE);
        btnFiltra.setFont(new Font("Tahoma", Font.BOLD, 14));

        JButton btnPlaylist = new JButton("Vai alle Mie Playlist"); // Il PlaylistButton del tuo schema
        
        pnlBottoni.add(btnFiltra);
        pnlBottoni.add(btnPlaylist);
        add(pnlBottoni, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI
        // ==========================================

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.cambiaPannelloCentrale(new JPanel()); // Svuota la home
            }
        });

        btnPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ⚠️ IN FUTURO: Qui chiami la classe CatalogoPlaylistView per mostrare le playlist
                CatalogoPlaylist vistaPlaylist = new CatalogoPlaylist(playlistController, homePage);
                homePage.cambiaPannelloCentrale(vistaPlaylist);
            }
        });

        btnFiltra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titoloCercato = txtTitolo.getText().trim();
                String tipoSelezionato = (String) comboTipo.getSelectedItem();

                // ⚠️ IN FUTURO: La logica esatta dell'EBC è questa:
                // 1. Chiedi al MediaController di cercare nel DB usando i due filtri
                // List<ElementoMultimediale> risultati = mediaController.cercaElementiAvanzata(titoloCercato, tipoSelezionato);
                
                // 2. Crei la schermata del Catalogo passandogli la lista VERA appena ottenuta
                // CatalogoElementiView catalogo = new CatalogoElementiView(mediaController, playlistController, homePage, risultati);
                
                // Per ora, visto che stiamo simulando, apriamo il catalogo vuoto/finto
                CatalogoElementi catalogo = new CatalogoElementi(mediaController, playlistController, homePage);
                homePage.cambiaPannelloCentrale(catalogo);
            }
        });
    }
}
