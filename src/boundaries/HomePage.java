package boundaries;

import javax.swing.*;
import java.awt.*;
import controllers.*;

public class HomePage extends JFrame {
    private SessionController sessionController;
    private MediaController mediaController;
    private PlaylistController playlistController;
    private JPanel pannelloCentrale;

    public HomePage(SessionController sessionCtrl, MediaController mediaCtrl, PlaylistController playlistCtrl) {
        this.sessionController = sessionCtrl;
        this.mediaController = mediaCtrl;
        this.playlistController = playlistCtrl;
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setTitle("UninaMultiCloud - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // SIDEBAR
        JPanel sidebar = new JPanel(new GridLayout(7, 1, 10, 15));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setPreferredSize(new Dimension(220, 0));

        JButton btnCerca = new JButton("Esplora Catalogo");
        JButton btnMiePlaylist = new JButton("Le Mie Playlist");
        JButton btnCreaPlaylist = new JButton("Crea Playlist");
        JButton btnNuovoElemento = new JButton("Carica Elemento");
        JButton btnReport = new JButton("Report Statistiche");
        JButton btnLogout = new JButton("Logout");
        btnLogout.setForeground(Color.RED);

        sidebar.add(new JLabel("Menu", SwingConstants.CENTER));
        sidebar.add(btnCerca);
        sidebar.add(btnMiePlaylist);
        sidebar.add(btnCreaPlaylist);
        sidebar.add(btnNuovoElemento);
        sidebar.add(btnReport);
        sidebar.add(btnLogout);

        // CENTRO DINAMICO
        pannelloCentrale = new JPanel(new BorderLayout());
        JPanel pnlBenvenuto = new JPanel(new GridBagLayout());
        pnlBenvenuto.add(new JLabel("Benvenuto! Seleziona un'azione dal menu."));
        pannelloCentrale.add(pnlBenvenuto, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(pannelloCentrale, BorderLayout.CENTER);

        // EVENTI MENU
        btnLogout.addActionListener(e -> sessionController.eseguiLogout(this));
        
        btnNuovoElemento.addActionListener(e -> cambiaPannelloCentrale(new CaricaElemento(mediaController, this)));
        
        btnCerca.addActionListener(e -> cambiaPannelloCentrale(new PaginaFiltraggio(mediaController, playlistController, this)));
        
        btnCreaPlaylist.addActionListener(e -> cambiaPannelloCentrale(new CreaPlaylist(playlistController, this)));
        
        btnMiePlaylist.addActionListener(e -> cambiaPannelloCentrale(new CatalogoPlaylist(playlistController, mediaController, this)));
    }

    // IL METODO FONDAMENTALE PER LA NAVIGAZIONE
    public void cambiaPannelloCentrale(JPanel nuovoPannello) {
        pannelloCentrale.removeAll();
        pannelloCentrale.add(nuovoPannello, BorderLayout.CENTER);
        pannelloCentrale.revalidate();
        pannelloCentrale.repaint();
    }
}