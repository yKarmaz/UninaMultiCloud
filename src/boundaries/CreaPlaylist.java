package boundaries;

import javax.swing.*;
import java.awt.*;
import controllers.PlaylistController;

public class CreaPlaylist extends JPanel {
    private PlaylistController playlistController;
    private HomePage homePage;
    private JTextField txtNome;
    private JRadioButton rbPubblica, rbPrivata, rbCondivisa;
    private JComboBox<String> comboCategoria;

    public CreaPlaylist(PlaylistController playlistController, HomePage homePage) {
        this.playlistController = playlistController;
        this.homePage = homePage;
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblTitolo = new JLabel("Crea Nuova Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(lblTitolo, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridLayout(3, 2, 10, 20));
        pnlForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        pnlForm.add(txtNome);

        pnlForm.add(new JLabel("Tipologia:"));
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbPrivata = new JRadioButton("Privata", true);
        rbPubblica = new JRadioButton("Pubblica");
        rbCondivisa = new JRadioButton("Condivisa");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbPrivata); bg.add(rbPubblica); bg.add(rbCondivisa);
        pnlRadio.add(rbPrivata); pnlRadio.add(rbPubblica); pnlRadio.add(rbCondivisa);
        pnlForm.add(pnlRadio);

        pnlForm.add(new JLabel("Categoria (solo Pubblica):"));
        comboCategoria = new JComboBox<>(new String[]{"Nessuna", "Musica", "Podcast", "Educazione"});
        comboCategoria.setEnabled(false);
        pnlForm.add(comboCategoria);

        add(pnlForm, BorderLayout.CENTER);

        JButton btnCrea = new JButton("Crea Playlist");
        add(btnCrea, BorderLayout.SOUTH);

        // Disabilita categoria se non è pubblica
        rbPubblica.addActionListener(e -> comboCategoria.setEnabled(true));
        rbPrivata.addActionListener(e -> comboCategoria.setEnabled(false));
        rbCondivisa.addActionListener(e -> comboCategoria.setEnabled(false));

        btnCrea.addActionListener(e -> {
            String tipo = rbPubblica.isSelected() ? "Pubblica" : (rbCondivisa.isSelected() ? "Condivisa" : "Privata");
            String cat = (String) comboCategoria.getSelectedItem();
            
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci un nome!");
                return;
            }

            boolean ok = playlistController.creaNuovaPlaylist(txtNome.getText().trim(), tipo, cat);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Playlist creata!");
                homePage.cambiaPannelloCentrale(new JPanel());
            } else {
                JOptionPane.showMessageDialog(this, "Errore nella creazione.");
            }
        });
    }
}