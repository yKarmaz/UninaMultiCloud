package boundaries;

import javax.swing.*;
import controllers.SessionController;
import java.awt.*;

public class LoginPage extends JFrame {
    private SessionController sessionController;
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

        btnAccedi.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if(username.isEmpty() || password.isEmpty()) {
                mostraErrore("Compila tutti i campi!");
                return;
            }
            // Il controller deve fare la query e POI aprire la HomePage
            sessionController.eseguiLogin(username, password);
        });
    }

    public void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
    }
}