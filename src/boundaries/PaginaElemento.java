package boundaries;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controllers.*;
import entities.*;

public class PaginaElemento extends JPanel {
    private MediaController mediaController;
    private PlaylistController playlistController;
    private HomePage homePage;
    
    private ElementoMultimediale elementoReale;
    private JPanel pannelloPrecedente;
    private JComboBox<PlaylistBoxItem> comboMiePlaylist; 

    public PaginaElemento(MediaController mediaCtrl, PlaylistController playCtrl, HomePage home, ElementoMultimediale el, JPanel prev) {
        this.mediaController = mediaCtrl;
        this.playlistController = playCtrl;
        this.homePage = home;
        this.elementoReale = el;
        this.pannelloPrecedente = prev;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnIndietro = new JButton("< Indietro");
        pnlHeader.add(btnIndietro);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));

        JLabel lblTitolo = new JLabel(elementoReale.getTitolo());
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 28));
        
        String autore = elementoReale.getProprietario() != null ? elementoReale.getProprietario().getUsername() : "N/A";
        JLabel lblAutore = new JLabel("Autore: " + autore);
        
        String tipo = (elementoReale instanceof Audio) ? "Audio" : "Video";
        JLabel lblDettagli = new JLabel("Tipologia: " + tipo + " | Durata: " + elementoReale.getDurata() + "s");

        pnlCentro.add(lblTitolo);
        pnlCentro.add(lblAutore);
        pnlCentro.add(lblDettagli);

        // AGGIUNGI A PLAYLIST
        JPanel pnlAggiungi = new JPanel(new FlowLayout());
        pnlAggiungi.setBorder(BorderFactory.createTitledBorder("Aggiungi a tua Playlist"));
        
        comboMiePlaylist = new JComboBox<>();
        popolaTendinaPlaylist(); // Carica dal DB
        
        JButton btnAggiungi = new JButton("Aggiungi");
        pnlAggiungi.add(comboMiePlaylist);
        pnlAggiungi.add(btnAggiungi);
        pnlCentro.add(pnlAggiungi);

        add(pnlCentro, BorderLayout.CENTER);

        JButton btnRiproduci = new JButton("▶ RIPRODUCI ORA");
        btnRiproduci.setBackground(new Color(46, 204, 113));
        add(btnRiproduci, BorderLayout.SOUTH);

        btnIndietro.addActionListener(e -> homePage.cambiaPannelloCentrale(pannelloPrecedente));

        btnRiproduci.addActionListener(e -> {
            // Qui devi registrare la fruizione
            mediaController.registraFruizione(elementoReale);
            JOptionPane.showMessageDialog(this, "Riproduzione avviata!");
        });

        btnAggiungi.addActionListener(e -> {
            PlaylistBoxItem scelta = (PlaylistBoxItem) comboMiePlaylist.getSelectedItem();
            if(scelta != null) {
                boolean ok = playlistController.aggiungiElementoAPlaylist(scelta.playlist, elementoReale);
                if(ok) JOptionPane.showMessageDialog(this, "Aggiunto!");
                else JOptionPane.showMessageDialog(this, "Errore (forse è già presente?)");
            }
        });
    }

    private void popolaTendinaPlaylist() {
        List<Playlist> mie = playlistController.getMiePlaylistPrivate();
        mie.addAll(playlistController.getMiePlaylistCondivise());
        mie.addAll(playlistController.getMiePlaylistPubbliche());
        for(Playlist p : mie) {
            comboMiePlaylist.addItem(new PlaylistBoxItem(p));
        }
    }

    // Classe di supporto per stampare solo il nome nella tendina, ma mantenere l'oggetto intero dietro le quinte
    private class PlaylistBoxItem {
        Playlist playlist;
        public PlaylistBoxItem(Playlist p) { this.playlist = p; }
        @Override public String toString() { return playlist.getNome(); }
    }
}