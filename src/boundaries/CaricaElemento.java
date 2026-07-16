package boundaries;
import javax.swing.*;

import controllers.MediaController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CaricaElemento extends JPanel {

    private MediaController mediaController;
    private HomePage homePage;

    // Componenti della form
    private JTextField txtTitolo;
    private JTextArea txtDescrizione;
    private JRadioButton rbAudio;
    private JRadioButton rbVideo;
    private ButtonGroup gruppoTipologia;
    
    private JLabel lblPercorsoFile; // Mostra il file selezionato
    private String percorsoFileSelezionato = ""; // Memorizza il path reale

    public CaricaElemento(MediaController mediaController, HomePage homePage) {
        this.mediaController = mediaController;
        this.homePage = homePage;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        JLabel lblTitoloView = new JLabel("Carica un Nuovo Elemento", SwingConstants.CENTER);
        lblTitoloView.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        pnlHeader.add(lblTitoloView, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. FORM CENTRALE ---
        // Usiamo un BoxLayout verticale per impilare gli elementi in modo pulito
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));

        // Titolo
        pnlForm.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField();
        txtTitolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        pnlForm.add(txtTitolo);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 15))); // Spazio vuoto

        // Descrizione
        pnlForm.add(new JLabel("Descrizione:"));
        txtDescrizione = new JTextArea(3, 20);
        txtDescrizione.setLineWrap(true);
        txtDescrizione.setWrapStyleWord(true);
        JScrollPane scrollDescrizione = new JScrollPane(txtDescrizione);
        scrollDescrizione.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        pnlForm.add(scrollDescrizione);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 15)));

        // Tipologia (Audio/Video)
        pnlForm.add(new JLabel("Tipologia:"));
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbAudio = new JRadioButton("Audio", true);
        rbVideo = new JRadioButton("Video");
        gruppoTipologia = new ButtonGroup();
        gruppoTipologia.add(rbAudio);
        gruppoTipologia.add(rbVideo);
        pnlRadio.add(rbAudio);
        pnlRadio.add(rbVideo);
        pnlRadio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        pnlForm.add(pnlRadio);
        pnlForm.add(Box.createRigidArea(new Dimension(0, 15)));

        // Seleziona File (Media / Copertina)
        pnlForm.add(new JLabel("File Multimediale:"));
        JPanel pnlFile = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton btnScegliFile = new JButton("Sfoglia...");
        lblPercorsoFile = new JLabel(" Nessun file selezionato");
        lblPercorsoFile.setForeground(Color.GRAY);
        pnlFile.add(btnScegliFile);
        pnlFile.add(lblPercorsoFile);
        pnlFile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pnlForm.add(pnlFile);

        add(pnlForm, BorderLayout.CENTER);

        // --- 3. FOOTER (Tasto Carica) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCarica = new JButton("Carica Contenuto");
        btnCarica.setBackground(new Color(46, 204, 113));
        btnCarica.setForeground(Color.WHITE);
        btnCarica.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlFooter.add(btnCarica);
        
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // 4. EVENTI
        // ==========================================

        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homePage.cambiaPannelloCentrale(new JPanel());
            }
        });

        // Evento Sfoglia File
        btnScegliFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Apre la finestra di sistema per scegliere un file
                JFileChooser fileChooser = new JFileChooser();
                int risultato = fileChooser.showOpenDialog(CaricaElemento.this);
                
                if (risultato == JFileChooser.APPROVE_OPTION) {
                    File fileSelezionato = fileChooser.getSelectedFile();
                    percorsoFileSelezionato = fileSelezionato.getAbsolutePath(); // Es: C:\Musica\brano.mp3
                    lblPercorsoFile.setText(" " + fileSelezionato.getName());
                    lblPercorsoFile.setForeground(Color.BLACK);
                }
            }
        });

        btnCarica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo = txtTitolo.getText().trim();
                String descrizione = txtDescrizione.getText().trim();
                String tipo = rbAudio.isSelected() ? "Audio" : "Video";

                // Validazione Boundary
                if (titolo.isEmpty() || descrizione.isEmpty() || percorsoFileSelezionato.isEmpty()) {
                    JOptionPane.showMessageDialog(CaricaElemento.this, 
                        "Compila tutti i campi e seleziona un file!", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // ⚠️ IN FUTURO: Quando avrai l'Utente loggato nel SessionController, potrai passarlo
                // al MediaController per associarlo come creatore/uploader del file (se richiesto dal DB).
                
                // DELEGA AL CONTROLLER (Nota: ho aggiunto 'descrizione' per matchare il tuo EBC)
                boolean successo = mediaController.caricaNuovoElemento(titolo, descrizione, percorsoFileSelezionato, tipo);

                if (successo) {
                    JOptionPane.showMessageDialog(CaricaElemento.this, 
                        "Elemento caricato con successo!", "Upload Completato", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Reset dei campi dopo il caricamento
                    txtTitolo.setText("");
                    txtDescrizione.setText("");
                    percorsoFileSelezionato = "";
                    lblPercorsoFile.setText(" Nessun file selezionato");
                    
                    homePage.cambiaPannelloCentrale(new JPanel());
                } else {
                    JOptionPane.showMessageDialog(CaricaElemento.this, 
                        "Errore durante l'upload del file.", "Errore Server", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}