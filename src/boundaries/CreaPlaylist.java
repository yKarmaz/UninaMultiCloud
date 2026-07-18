package boundaries; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.PlaylistController;
// import boundaries.HomePage; // (Scommenta questo quando unirai tutto)

public class CreaPlaylist extends JPanel {

    private PlaylistController playlistController;
    private HomePage homePage; // ⚠️ IN FUTURO: Cambiare in HomePage

    private JTextField txtNome;
    private JRadioButton rbPubblica;
    private JRadioButton rbPrivata;
    private JRadioButton rbCondivisa;
    private ButtonGroup gruppoTipologia;
    private JComboBox<String> comboCategoria; // AGGIUNTO: Necessario per le pubbliche

    public CreaPlaylist(PlaylistController playlistController, HomePage homePage) {
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
        lblTitolo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0)); 
        add(lblTitolo, BorderLayout.NORTH);

        // --- 2. FORM CENTRATO CON GRIDBAGLAYOUT ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); 
        gbc.anchor = GridBagConstraints.WEST; 

        // Riga 0: Nome
        gbc.gridx = 0; 
        gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome della Playlist:");
        lblNome.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblNome, gbc);

        gbc.gridx = 1; 
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        pnlForm.add(txtNome, gbc);

        // Riga 1: Tipologia
        gbc.gridx = 0; 
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblTipo = new JLabel("Seleziona la Tipologia:");
        lblTipo.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblTipo, gbc);

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

        // Riga 2: Categoria (AGGIUNTA FONDAMENTALE)
        gbc.gridx = 0; 
        gbc.gridy = 2;
        JLabel lblCategoria = new JLabel("Categoria (solo Pubblica):");
        lblCategoria.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblCategoria, gbc);

        gbc.gridx = 1; 
        gbc.gridy = 2;
        String[] categorie = {"Nessuna", "Musica", "Podcast", "Educazione", "Intrattenimento"};
        comboCategoria = new JComboBox<>(categorie);
        comboCategoria.setFont(new Font("Tahoma", Font.PLAIN, 14));
        pnlForm.add(comboCategoria, gbc);

        add(pnlForm, BorderLayout.CENTER);

        // --- 3. BOTTONI DI AZIONE ---
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
                System.out.println("Torno indietro (Simulazione)");
                // ⚠️ IN FUTURO: homePage.cambiaPannelloCentrale(new JPanel()); 
            }
        });

        // Evento: Abilita/Disabilita Categoria in base alla tipologia selezionata
        ActionListener radioListener = e -> {
            comboCategoria.setEnabled(rbPubblica.isSelected());
        };
        rbPubblica.addActionListener(radioListener);
        rbPrivata.addActionListener(radioListener);
        rbCondivisa.addActionListener(radioListener);
        
        // Impostazione iniziale: siccome "Privata" è di default, disabilito la tendina categoria
        comboCategoria.setEnabled(false);

        // Evento Bottone Crea
        btnCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                
                String tipologia = "Privata"; 
                if (rbPubblica.isSelected()) tipologia = "Pubblica";
                if (rbCondivisa.isSelected()) tipologia = "Condivisa";
                
                String categoria = (String) comboCategoria.getSelectedItem();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Errore: Inserisci un nome per la playlist!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean successo = playlistController.creaNuovaPlaylist(nome, tipologia, categoria);

                if (successo) {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Playlist creata con successo nel Database!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    // Reset grafica
                    txtNome.setText("");
                    rbPrivata.setSelected(true);
                    comboCategoria.setEnabled(false);
                    comboCategoria.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Errore interno. Impossibile creare la playlist.", "Errore DB", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}