package boundaries; // Controlla che il package sia corretto per te

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.PlaylistController;
// import boundaries.HomePage; // (Scommenta questo quando unirai tutto)

public class CreaPlaylist extends JPanel {

    private PlaylistController playlistController;
    private JPanel homePage; // L'ho messo JPanel per il BancoDiProva. Mettilo HomePage!

    private JTextField txtNome;
    private JRadioButton rbPubblica;
    private JRadioButton rbPrivata;
    private JRadioButton rbCondivisa;
    private ButtonGroup gruppoTipologia;

    // Costruttore temporaneo per il BancoDiProva (Usa 'HomePage' nel progetto vero)
    public CreaPlaylist(PlaylistController playlistController, JPanel homePage) {
        this.playlistController = playlistController;
        this.homePage = homePage;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- 1. TITOLO ---
        JLabel lblTitolo = new JLabel("Crea una Nuova Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblTitolo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0)); // Spazio sotto il titolo
        add(lblTitolo, BorderLayout.NORTH);

        // --- 2. FORM CENTRATO CON GRIDBAGLAYOUT (Perfetto per i Mock-up) ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Margini tra gli elementi (Sopra, Sinistra, Sotto, Destra)
        gbc.anchor = GridBagConstraints.WEST; // Allinea tutto a sinistra della sua cella

        // Riga 0 - Colonna 0: Etichetta Nome [cite: 9]
        gbc.gridx = 0; 
        gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome della Playlist:");
        lblNome.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblNome, gbc);

        // Riga 0 - Colonna 1: Campo di testo Nome
        gbc.gridx = 1; 
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fagli occupare lo spazio in orizzontale
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        pnlForm.add(txtNome, gbc);

        // Riga 1 - Colonna 0: Etichetta Tipologia [cite: 10]
        gbc.gridx = 0; 
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblTipo = new JLabel("Seleziona la Tipologia:");
        lblTipo.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblTipo, gbc);

        // Riga 1 - Colonna 1: Bottoni Radio [cite: 11, 12, 13]
        gbc.gridx = 1; 
        gbc.gridy = 1;
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        
        rbPubblica = new JRadioButton("Pubblica");
        rbPrivata = new JRadioButton("Privata", true);
        rbCondivisa = new JRadioButton("Condivisa");
        
        rbPubblica.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rbPrivata.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rbCondivisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        gruppoTipologia = new ButtonGroup();
        gruppoTipologia.add(rbPubblica);
        gruppoTipologia.add(rbPrivata);
        gruppoTipologia.add(rbCondivisa);
        
        pnlRadio.add(rbPubblica);
        pnlRadio.add(rbPrivata);
        pnlRadio.add(rbCondivisa);
        pnlForm.add(pnlRadio, gbc);

        // Aggiungo il form al centro
        add(pnlForm, BorderLayout.CENTER);

        // --- 3. BOTTONI DI AZIONE (In basso) ---
        JPanel pnlBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        
        JButton btnIndietro = new JButton("< Indietro");
        btnIndietro.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        JButton btnCrea = new JButton("Crea Playlist");
        btnCrea.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCrea.setBackground(new Color(46, 204, 113));
        btnCrea.setForeground(Color.WHITE);

        pnlBottoni.add(btnIndietro);
        pnlBottoni.add(btnCrea);
        add(pnlBottoni, BorderLayout.SOUTH);

        // ==========================================
        // EVENTI
        // ==========================================
        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // homePage.cambiaPannelloCentrale(new JPanel()); // Scommenta nel progetto reale
                System.out.println("Torno indietro (Simulazione)");
            }
        });

        btnCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String tipologia = "Privata"; 
                if (rbPubblica.isSelected()) tipologia = "Pubblica";
                if (rbCondivisa.isSelected()) tipologia = "Condivisa";

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Errore: Inserisci un nome per la playlist!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean successo = playlistController.creaNuovaPlaylist(nome, tipologia);

                if (successo) {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Playlist creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    txtNome.setText("");
                } else {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Errore interno. Impossibile creare la playlist.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}