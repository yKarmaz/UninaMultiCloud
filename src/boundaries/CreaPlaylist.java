package boundaries;

import javax.swing.*;

import controllers.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaPlaylist extends JPanel {

    // Riferimenti indispensabili per non violare l'EBC
    private PlaylistController playlistController;
    private HomePage homePage; // Ci serve per il tasto "Indietro"

    // Componenti visivi
    private JTextField txtNome;
    private JRadioButton rbPubblica;
    private JRadioButton rbPrivata;
    private JRadioButton rbCondivisa;
    private ButtonGroup gruppoTipologia;

    public CreaPlaylist(PlaylistController playlistController, HomePage homePage) {
        this.playlistController = playlistController;
        this.homePage = homePage;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        // Layout pulito e "WindowBuilder Friendly"
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // --- 1. TITOLO (In alto) ---
        JLabel lblTitolo = new JLabel("Crea una Nuova Playlist", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(lblTitolo, BorderLayout.NORTH);

        // --- 2. FORM DI INSERIMENTO (Al centro) ---
        JPanel pnlForm = new JPanel(new GridLayout(4, 1, 10, 10));
        
        pnlForm.add(new JLabel("Nome della Playlist:"));
        txtNome = new JTextField();
        pnlForm.add(txtNome);

        pnlForm.add(new JLabel("Seleziona la Tipologia:"));
        
        // Pannello per i radio button affiancati
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbPubblica = new JRadioButton("Pubblica");
        rbPrivata = new JRadioButton("Privata", true); // Selezionato di default per evitare errori
        rbCondivisa = new JRadioButton("Condivisa");
        
        // Il ButtonGroup impedisce all'utente di selezionarne due contemporaneamente
        gruppoTipologia = new ButtonGroup();
        gruppoTipologia.add(rbPubblica);
        gruppoTipologia.add(rbPrivata);
        gruppoTipologia.add(rbCondivisa);
        
        pnlRadio.add(rbPubblica);
        pnlRadio.add(rbPrivata);
        pnlRadio.add(rbCondivisa);
        
        pnlForm.add(pnlRadio);
        add(pnlForm, BorderLayout.CENTER);

        // --- 3. BOTTONI DI AZIONE (In basso) ---
        JPanel pnlBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        JButton btnIndietro = new JButton("< Indietro");
        JButton btnCrea = new JButton("Crea Playlist");
        btnCrea.setBackground(new Color(46, 204, 113)); // Verde per far capire che è un'azione positiva
        btnCrea.setForeground(Color.WHITE);

        pnlBottoni.add(btnIndietro);
        pnlBottoni.add(btnCrea);
        add(pnlBottoni, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI (Il collegamento tra Vista e Logica)
        // ==========================================

        // Evento Tasto Indietro
        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Svuotiamo il centro della HomePage mettendo un pannello vuoto
                homePage.cambiaPannelloCentrale(new JPanel()); 
            }
        });

        // Evento Tasto Crea
        btnCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // A. La Boundary raccoglie i dati digitati (lavoro da Boundary)
                String nome = txtNome.getText().trim();
                String tipologia = "Privata"; // Default
                if (rbPubblica.isSelected()) tipologia = "Pubblica";
                if (rbCondivisa.isSelected()) tipologia = "Condivisa";

                // B. Validazione visiva di base (NON chiamare il DB se il campo è vuoto)
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Errore: Inserisci un nome per la playlist!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return; // Blocca l'esecuzione
                }

                // C. DELEGA AL CONTROLLER (EBC Puro)
                boolean successo = playlistController.creaNuovaPlaylist(nome, tipologia);

                // D. Reazione della GUI in base alla risposta del Controller
                if (successo) {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Playlist creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    txtNome.setText(""); // Pulisce il campo
                    homePage.cambiaPannelloCentrale(new JPanel()); // Torna alla home
                } else {
                    JOptionPane.showMessageDialog(CreaPlaylist.this, 
                        "Errore interno. Impossibile creare la playlist.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}