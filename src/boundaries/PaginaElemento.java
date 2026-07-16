package boundaries;

import javax.swing.*;

import controllers.MediaController;
import controllers.PlaylistController;

import java.awt.*;

public class PaginaElemento extends JPanel {

    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage;
    
    // ⚠️ IN FUTURO: Al posto della Stringa, riceverai l'oggetto "ElementoMultimediale elemento"
    private String titoloElementoFake; 

    private JComboBox<String> comboMiePlaylist;

    public PaginaElemento(MediaController mediaCtrl, PlaylistController playCtrl, HomePage home, String titoloElemento) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        this.titoloElementoFake = titoloElemento;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Torna al Catalogo");
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. INFO ELEMENTO ---
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));
        
        // ⚠️ IN FUTURO: Usa i getter del tuo oggetto, es: elemento.getTitolo()
        JLabel lblTitolo = new JLabel("Titolo: " + titoloElementoFake);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 26));
        
        JLabel lblAutore = new JLabel("Autore: Sconosciuto (Fake)");
        lblAutore.setFont(new Font("Tahoma", Font.PLAIN, 18));
        
        pnlCentro.add(lblTitolo);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlCentro.add(lblAutore);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 3. SEZIONE AGGIUNGI A PLAYLIST ---
        JPanel pnlAggiungi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAggiungi.setBorder(BorderFactory.createTitledBorder("Aggiungi alle tue Playlist"));
        
        // ⚠️ IN FUTURO: Chiamerai playlistController.getMiePlaylist(utenteLoggato) 
        // per riempire dinamicamente questa tendina.
        String[] playlistFittizie = {"Studio Notturno", "Rock Classico", "Palestra"};
        comboMiePlaylist = new JComboBox<>(playlistFittizie);
        
        JButton btnAggiungi = new JButton("Aggiungi");
        pnlAggiungi.add(new JLabel("Seleziona Playlist: "));
        pnlAggiungi.add(comboMiePlaylist);
        pnlAggiungi.add(btnAggiungi);
        
        pnlCentro.add(pnlAggiungi);
        add(pnlCentro, BorderLayout.CENTER);

        // --- 4. FOOTER (Riproduci) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRiproduci = new JButton("▶ RIPRODUCI ORA");
        btnRiproduci.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnRiproduci.setBackground(new Color(46, 204, 113));
        btnRiproduci.setForeground(Color.WHITE);
        pnlFooter.add(btnRiproduci);
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // EVENTI
        // ==========================================

        btnIndietro.addActionListener(e -> {
            // Ricarica il catalogo pulito
            homePage.cambiaPannelloCentrale(new CatalogoElementi(mediaController, playlistController, homePage));
        });

        btnRiproduci.addActionListener(e -> {
            // ⚠️ IN FUTURO: mediaController.registraFruizione(elementoReale);
            JOptionPane.showMessageDialog(this, "Riproduzione avviata per: " + titoloElementoFake, "Player", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAggiungi.addActionListener(e -> {
            String playlistSelezionata = (String) comboMiePlaylist.getSelectedItem();
            
            if(playlistSelezionata == null) return;
            
            // ⚠️ IN FUTURO: DELEGA AL CONTROLLER (EBC)
            // boolean successo = playlistController.aggiungiElemento(elementoReale, playlistReale);
            
            JOptionPane.showMessageDialog(this, "Brano aggiunto a " + playlistSelezionata + "!", "Successo", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
