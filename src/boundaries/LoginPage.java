package boundaries;

import javax.swing.*;
import controllers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    
    // Riferimento al Controller
    private SessionController sessionController;
    
    // Componenti della GUI
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnAccedi;

    public LoginPage(SessionController sessionController) {
        this.sessionController = sessionController;
        inizializzaComponenti();
    }

    private void inizializzaComponenti() {
        setTitle("UninaMultiCloud - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(); 
        formPanel.add(txtPassword);

        JPanel buttonPanel = new JPanel();
        btnAccedi = new JButton("Accedi");
        buttonPanel.add(btnAccedi);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- GESTIONE EVENTI ---
        btnAccedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                
                // Validazione locale (responsabilità della Boundary)
                if(username.isEmpty() || password.isEmpty()) {
                    mostraErrore("Compila tutti i campi!");
                    return;
                }
                
                // Delega l'elaborazione di business al Controller
                sessionController.eseguiLogin(username, password);
            }
        });
    }

    public void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
    }
}