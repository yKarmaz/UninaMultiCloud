package boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.MediaController;
import controllers.PlaylistController;
// import boundaries.HomePage; // Scommenta nel progetto vero

public class PaginaElemento extends JPanel {

    private MediaController mediaController;
    private PlaylistController playlistController;
    private JPanel homePage; 
    
    private String titoloElementoFake; 
    private JPanel pannelloPrecedente; // 👈 IL SEGRETO DELLA NAVIGAZIONE!

    private JComboBox<String> comboMiePlaylist;

    // Guarda il costruttore: ora richiede il "pannelloPrecedente"
    public PaginaElemento(MediaController mediaCtrl, PlaylistController playCtrl, JPanel home, String titoloElemento, JPanel pannelloPrecedente) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        this.titoloElementoFake = titoloElemento;
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
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS)); // Impila verticalmente
        
        JLabel lblTitolo = new JLabel(titoloElementoFake);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblAutore = new JLabel("Autore: Sconosciuto (Fake)");
        lblAutore.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAutore.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnlCentro.add(lblTitolo);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlCentro.add(lblAutore);
        pnlCentro.add(Box.createRigidArea(new Dimension(0, 40)));

        // --- 3. SEZIONE AGGIUNGI A PLAYLIST (Ora non si allarga a dismisura) ---
        JPanel pnlAggiungi = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlAggiungi.setBorder(BorderFactory.createTitledBorder("Aggiungi alle tue Playlist"));
        // Limita l'altezza del pannello per non farlo esplodere
        pnlAggiungi.setMaximumSize(new Dimension(500, 80)); 
        
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

        // ==========================================
        // EVENTI
        // ==========================================

        btnIndietro.addActionListener(e -> {
            // MAGIA: Torna esattamente alla pagina da cui sei venuto!
            homePage.add(pannelloPrecedente); // Rimuovi se usi CardLayout vero
            // homePage.cambiaPannelloCentrale(pannelloPrecedente); // Usa questo nel progetto
            
            // FAKE LOGIC PER IL TEST DI OGGI: chiudo e riapro nel BancoDiProva
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.getContentPane().add(pannelloPrecedente);
            topFrame.revalidate();
            topFrame.repaint();
        });

        btnRiproduci.addActionListener(e -> JOptionPane.showMessageDialog(this, "Riproduzione in corso...", "Player", JOptionPane.INFORMATION_MESSAGE));
        
        btnAggiungi.addActionListener(e -> JOptionPane.showMessageDialog(this, "Brano aggiunto a " + comboMiePlaylist.getSelectedItem() + "!", "Successo", JOptionPane.INFORMATION_MESSAGE));
    }
}