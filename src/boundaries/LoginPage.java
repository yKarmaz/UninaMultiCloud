package boundaries;

import javax.swing.*;
import controllers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import databaseConnection.*; 

public class LoginPage extends JFrame {
    
    // Riferimento al Controller (IL CERVELLO)
    private SessionController sessionController;
    
    // Componenti della GUI
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnAccedi;

    /**
     * Costruttore: NON accetta parametri vuoti. 
     * Pretende che gli venga passato il Controller.
     */
    public LoginPage(SessionController sessionController) {
        this.sessionController = sessionController;
        inizializzaComponenti();
    }

    private void inizializzaComponenti() {
        // Impostazioni base della finestra
        setTitle("UninaMultiCloud - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra lo schermo
        setLayout(new BorderLayout());

        // Pannello centrale con i campi
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(); // Nasconde i caratteri digitati
        formPanel.add(txtPassword);

        // Pannello per il bottone
        JPanel buttonPanel = new JPanel();
        btnAccedi = new JButton("Accedi");
        buttonPanel.add(btnAccedi);

        // Assemblaggio della finestra
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- IL CUORE DELL'EBC ---
        btnAccedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. La Boundary legge i dati (stupida)
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                
                // 2. La Boundary controlla solo che non siano vuoti (validazione base)
                if(username.isEmpty() || password.isEmpty()) {
                    mostraErrore("Compila tutti i campi!");
                    return;
                }
                
                // 3. La Boundary passa la palla al Controller (NON CHIAMA IL DB)
                SessionController.eseguiLogin(username, password, LoginPage.this);
            }
        });
    }

    // Metodo di servizio per far parlare il Controller con l'utente in caso di errore
    public void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
    }
}
