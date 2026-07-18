package boundaries;

import javax.swing.*;
import java.awt.*;
import controllers.MediaController;
import controllers.PlaylistController;
import entities.ElementoMultimediale;
import entities.Audio;
import entities.Video;

public class PaginaElemento extends JPanel {
    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage; // Ora usa la VERA HomePage
    
    // IL VERO OGGETTO, addio dati finti!
    private ElementoMultimediale elementoReale; 
    
    // Riferimento al pannello da cui proveniamo (es. PaginaFiltraggio o PaginaPlaylist)
    private JPanel pannelloPrecedente; 
    private JComboBox<String> comboMiePlaylist;

    // Costruttore corretto: accetta l'oggetto ElementoMultimediale e la HomePage
    public PaginaElemento(MediaController mediaCtrl, PlaylistController playCtrl, HomePage home, ElementoMultimediale elemento, JPanel pannelloPrecedente) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        this.elementoReale = elemento;
        this.pannelloPrecedente = pannelloPrecedente;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- 1. HEADER (Tasto Indietro) ---
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnIndietro = new JButton("< Indietro");
        pnlHeader.add(btnIndietro);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. CONTENUTO CENTRALE COMPATTO ---
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));

        // Usiamo i getter dell'oggetto VERO caricato dal Database
        JLabel lblTitolo = new JLabel(elementoReale.getTitolo());
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Preveniamo un NullPointerException se il proprietario non è settato correttamente
        String nomeAutore = (elementoReale.getProprietario() != null) ? elementoReale.getProprietario().getUsername() : "Sconosciuto";
        JLabel lblAutore = new JLabel("Autore: " + nomeAutore);
        lblAutore.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAutore.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Visto che abbiamo l'oggetto, mostriamo i veri dettagli!
        String tipo = (elementoReale instanceof Audio) ? "Audio" : "Video";
        JLabel lblDettagli = new JLabel("Tipologia: " + tipo + " | Durata: " + elementoReale.getDurata() + " sec");
        lblDettagli.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblDettagli.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlCentro.add(lblTitolo);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlCentro.add(lblAutore);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlCentro.add(lblDettagli);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 40)));

        // --- 3. SEZIONE AGGIUNGI A PLAYLIST ---
        JPanel pnlAggiungi = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlAggiungi.setBorder(BorderFactory.createTitledBorder("Aggiungi alle tue Playlist"));
        pnlAggiungi.setMaximumSize(new Dimension(500, 80));
        
        // !! PROSSIMO OBIETTIVO !!
        // Ora queste sono stringhe finte. Il prossimo step sarà chiamare playlistController 
        // per avere la vera lista di playlist dell'utente e popolare questa tendina.
        String[] playlistFittizie = {"Studio Notturno", "Rock Classico", "Palestra"};
        comboMiePlaylist = new JComboBox<>(playlistFittizie);
        
        JButton btnAggiungi = new JButton("Aggiungi");
        pnlAggiungi.add(new JLabel("Seleziona: "));
        pnlAggiungi.add(comboMiePlaylist);
        pnlAggiungi.add(btnAggiungi);
        
        pnlCentro.add(pnlAggiungi);
        add(pnlCentro, BorderLayout.CENTER);

        // --- 4. FOOTER (Riproduci) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JButton btnRiproduci = new JButton("▶ RIPRODUCI ORA");
        btnRiproduci.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnRiproduci.setBackground(new Color(46, 204, 113));
        btnRiproduci.setForeground(Color.WHITE);
        pnlFooter.add(btnRiproduci);
        add(pnlFooter, BorderLayout.SOUTH);

        // ====== EVENTI ======
        
        btnIndietro.addActionListener(e -> {
            // MAGIA: Ritorni ESATTAMENTE alla schermata precedente (mantenendo i filtri di ricerca intatti!)
            homePage.cambiaPannelloCentrale(pannelloPrecedente);
        });

        btnRiproduci.addActionListener(e -> {
            // !! ALTRO OBIETTIVO !!
            // Qui in futuro devi chiamare il controller per registrare la "Fruizione" sul Database.
            JOptionPane.showMessageDialog(this, 
                "Riproduzione di '" + elementoReale.getTitolo() + "' in corso...", 
                "Player Audio/Video", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAggiungi.addActionListener(e -> {
            // Dovrai estrarre l'ID della playlist scelta e passarlo al DB insieme all'ID dell'elemento
            JOptionPane.showMessageDialog(this, 
                "Brano aggiunto a " + comboMiePlaylist.getSelectedItem() + "!", 
                "Successo", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}