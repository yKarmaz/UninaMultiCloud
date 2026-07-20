package boundaries;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import controllers.MediaController;

public class CaricaElemento extends JPanel {
    private MediaController mediaController;
    private HomePage homePage;
    private JTextField txtTitolo;
    private JTextArea txtDescrizione;
    private JRadioButton rbAudio, rbVideo;
    private String percorsoFileSelezionato = "";
    private JLabel lblPercorsoFile;

    public CaricaElemento(MediaController mediaController, HomePage homePage) {
        this.mediaController = mediaController;
        this.homePage = homePage;
        inizializzaInterfaccia();
    }

    private void inizializzaInterfaccia() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitoloView = new JLabel("Carica Nuovo Elemento", SwingConstants.CENTER);
        lblTitoloView.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(lblTitoloView, BorderLayout.NORTH);

        //Creiamo il form e lo confiniamo in alto (NORTH)
        JPanel pnlFormWrapper = new JPanel(new BorderLayout());
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 20));
        
        pnlForm.add(new JLabel("Titolo:"));
        txtTitolo = new JTextField();
        pnlForm.add(txtTitolo);

        pnlForm.add(new JLabel("Descrizione:"));
        txtDescrizione = new JTextArea(3, 20);
        txtDescrizione.setLineWrap(true);
        pnlForm.add(new JScrollPane(txtDescrizione));

        pnlForm.add(new JLabel("Tipologia:"));
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbAudio = new JRadioButton("Audio", true);
        rbVideo = new JRadioButton("Video");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAudio); bg.add(rbVideo);
        pnlRadio.add(rbAudio); pnlRadio.add(rbVideo);
        pnlForm.add(pnlRadio);

        pnlForm.add(new JLabel("File:"));
        JPanel pnlFile = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnScegliFile = new JButton("Sfoglia...");
        lblPercorsoFile = new JLabel("Nessun file");
        pnlFile.add(btnScegliFile);
        pnlFile.add(lblPercorsoFile);
        pnlForm.add(pnlFile);

        
        pnlFormWrapper.add(pnlForm, BorderLayout.NORTH);
        add(pnlFormWrapper, BorderLayout.CENTER);

        JButton btnCarica = new JButton("Carica Contenuto");
        btnCarica.setBackground(new Color(46, 204, 113));
        btnCarica.setForeground(Color.WHITE);
        add(btnCarica, BorderLayout.SOUTH);

        btnScegliFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                percorsoFileSelezionato = f.getAbsolutePath();
                lblPercorsoFile.setText(f.getName());
            }
        });

        btnCarica.addActionListener(e -> {
            String tipo = rbAudio.isSelected() ? "Audio" : "Video";
            if(txtTitolo.getText().trim().isEmpty() || percorsoFileSelezionato.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Campi obbligatori mancanti!");
                return;
            }
            
            boolean ok = mediaController.caricaNuovoElemento(txtTitolo.getText().trim(), txtDescrizione.getText(), percorsoFileSelezionato, tipo);
            if(ok) {
                JOptionPane.showMessageDialog(this, "Caricamento completato!");
                homePage.cambiaPannelloCentrale(new JPanel()); 
            } else {
                JOptionPane.showMessageDialog(this, "Errore nel caricamento.");
            }
        });
    }
}