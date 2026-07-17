package boundaries;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import controllers.MediaController;

public class CaricaElemento extends JPanel {

    private MediaController mediaController;
    private JPanel homePage; // ⚠️ IN FUTURO: Cambia in HomePage quando unisci i file

    private JTextField txtTitolo;
    private JTextArea txtDescrizione;
    private JRadioButton rbAudio;
    private JRadioButton rbVideo;
    private ButtonGroup gruppoTipologia;
    
    private JLabel lblPercorsoFile; 
    private String percorsoFileSelezionato = ""; 

    public CaricaElemento(MediaController mediaController, JPanel homePage) {
        this.mediaController = mediaController;
        this.homePage = homePage;
        
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        JButton btnIndietro = new JButton("< Indietro");
        btnIndietro.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel lblTitoloView = new JLabel("Carica un Nuovo Elemento", SwingConstants.CENTER);
        lblTitoloView.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblTitoloView.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        pnlHeader.add(btnIndietro, BorderLayout.WEST);
        pnlHeader.add(lblTitoloView, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. FORM CENTRATO CON GRIDBAGLAYOUT ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15); 
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Riga 0: Titolo
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTitolo = new JLabel("Titolo:");
        lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblTitolo, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTitolo = new JTextField(25);
        txtTitolo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        pnlForm.add(txtTitolo, gbc);

        // Riga 1: Descrizione
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblDescrizione = new JLabel("Descrizione:");
        lblDescrizione.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblDescrizione, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtDescrizione = new JTextArea(4, 25);
        txtDescrizione.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtDescrizione.setLineWrap(true);
        txtDescrizione.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescrizione);
        pnlForm.add(scrollDesc, gbc);

        // Riga 2: Tipologia
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblTipo = new JLabel("Tipologia:");
        lblTipo.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblTipo, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        rbAudio = new JRadioButton("Audio", true);
        rbVideo = new JRadioButton("Video");
        rbAudio.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rbVideo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        gruppoTipologia = new ButtonGroup();
        gruppoTipologia.add(rbAudio);
        gruppoTipologia.add(rbVideo);
        pnlRadio.add(rbAudio);
        pnlRadio.add(rbVideo);
        pnlForm.add(pnlRadio, gbc);

        // Riga 3: File Multimediale
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblFile = new JLabel("File Multimediale:");
        lblFile.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlForm.add(lblFile, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JPanel pnlFile = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton btnScegliFile = new JButton("Sfoglia...");
        lblPercorsoFile = new JLabel("Nessun file selezionato");
        lblPercorsoFile.setFont(new Font("Tahoma", Font.ITALIC, 12));
        lblPercorsoFile.setForeground(Color.GRAY);
        pnlFile.add(btnScegliFile);
        pnlFile.add(lblPercorsoFile);
        pnlForm.add(pnlFile, gbc);

        add(pnlForm, BorderLayout.CENTER);

        // --- 3. FOOTER (Tasto Carica) ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JButton btnCarica = new JButton("Carica Contenuto");
        btnCarica.setBackground(new Color(46, 204, 113));
        btnCarica.setForeground(Color.WHITE);
        btnCarica.setFont(new Font("Tahoma", Font.BOLD, 16));
        pnlFooter.add(btnCarica);
        
        add(pnlFooter, BorderLayout.SOUTH);

        // ==========================================
        // EVENTI
        // ==========================================

        btnIndietro.addActionListener(e -> {
            System.out.println("Torno indietro...");
            // homePage.cambiaPannelloCentrale(new JPanel()); // ⚠️ Scommenta nel progetto vero
        });

        btnScegliFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtroMedia = new FileNameExtensionFilter("File Audio e Video (*.mp3, *.mp4, *.wav)", "mp3", "mp4", "wav");
            fileChooser.setFileFilter(filtroMedia);
            
            int risultato = fileChooser.showOpenDialog(CaricaElemento.this);
            
            if (risultato == JFileChooser.APPROVE_OPTION) {
                File fileSelezionato = fileChooser.getSelectedFile();
                percorsoFileSelezionato = fileSelezionato.getAbsolutePath(); 
                lblPercorsoFile.setText(fileSelezionato.getName());
                lblPercorsoFile.setForeground(Color.BLACK);
            }
        });

        btnCarica.addActionListener(e -> {
            String titolo = txtTitolo.getText().trim();
            String descrizione = txtDescrizione.getText().trim();
            String tipo = rbAudio.isSelected() ? "Audio" : "Video";

            // Controllo rigoroso sui campi
            if (titolo.isEmpty() || descrizione.isEmpty() || percorsoFileSelezionato.isEmpty()) {
                JOptionPane.showMessageDialog(CaricaElemento.this, 
                    "Compila tutti i campi e seleziona un file!", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // QUI AVVIENE LA MAGIA: Deleghi tutto al Controller.
            // Il Controller, a sua volta, userà il DAO. Tu qui non devi sapere nulla di SQL.
            boolean successo = mediaController.caricaNuovoElemento(titolo, descrizione, percorsoFileSelezionato, tipo);

            if (successo) {
                JOptionPane.showMessageDialog(CaricaElemento.this, 
                    "Elemento caricato con successo!", "Upload Completato", JOptionPane.INFORMATION_MESSAGE);
                
                // Reset della grafica per permettere un nuovo inserimento
                txtTitolo.setText("");
                txtDescrizione.setText("");
                percorsoFileSelezionato = "";
                lblPercorsoFile.setText("Nessun file selezionato");
                lblPercorsoFile.setForeground(Color.GRAY);
            } else {
                JOptionPane.showMessageDialog(CaricaElemento.this, 
                    "Errore durante l'upload del file.", "Errore Server", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}