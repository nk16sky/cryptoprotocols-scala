package gui;

import algorithms.Gamma;
import algorithms.gamma.GammaJava;
import entity.ChoiceProtocol;
import entity.User;
import network.Client;
import network.ProtocolWideMouthFrog;

import javax.swing.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.VK_ENTER;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel settings;
    private JPanel authorize;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JTextField loginField;
    private JTextField passwordField;
    private JButton authorizeButton;

    private JPanel connection;
    private JLabel addressLabel;
    private JLabel portLabel;
    private JTextField addressField;
    private JTextField portField;
    private JButton connectionButton;

    private JPanel messages;
    private JTextPane messagesPane;
    private JTextField inputMessageField;
    private JButton inputMessageButton;
    private User user;
    MainFrame mainFrame;

    Client client;

    public MainFrame() {
        setContentPane(mainPanel);
        setVisible(true);
        int widthForm = 500;
        int heightForm = 400;
        setSize(widthForm, heightForm);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addMessage(Object message) {
        messagesPane.setText(messagesPane.getText() + "\n" + message);
    }

    public class authorizeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (user != null)
                addMessage("Goodbye, " + user.login() + "!!!");

            user = new User(loginField.getText(), passwordField.getText(), false);
            addMessage("Welcome, " + user.login() + "!!!");
            if (user.login().equalsIgnoreCase("trent")) {
                inputMessageButton.setEnabled(false);
            }
            authorizeButton.setEnabled(false);
        }
    }

    public class connectionActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (user == null) {
                messagesPane.setText("Please first sign in!");
                return;
            }

            connectionButton.setEnabled(false);
            addMessage(user.login() + " connection...");

            if (user.login().equalsIgnoreCase("trent")) {
                inputMessageField.setEnabled(false);
                ProtocolWideMouthFrog.startServer(user, Integer.parseInt(portField.getText()), 3, addressField.getText(), messagesPane, mainFrame);
            } else {
                try {
                    client = ProtocolWideMouthFrog.startClient(user, Integer.parseInt(portField.getText()), 3, addressField.getText(), messagesPane, mainFrame);
                } catch (NullPointerException excp) {
                    connectionButton.setEnabled(true);
                    addMessage("Error, can't connection! \nPlease, try connection again.");
                }
            }
        }
    }

    private void sendMessage() {
        if (user != null && user.sessionKey() != 0) {
            addMessage(inputMessageField.getText());
            byte[] message = new GammaJava().encrypt(inputMessageField.getText(), user.sessionKey());
            addMessage(message);
            if (client != null)
                client.sendMessage(inputMessageField.getText());
            else
                addMessage("Error, Client don't connected!!!");
        }
    }

    public class enterMessageActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
    }

    public class closedWindowListener implements WindowListener {
        public void windowClosing(WindowEvent arg0) {
            //Protocol_2Relesed.close();
            dispose();
        }

        public void windowOpened(WindowEvent arg0) {
        }

        public void windowClosed(WindowEvent arg0) {
        }

        public void windowIconified(WindowEvent arg0) {
        }

        public void windowDeiconified(WindowEvent arg0) {
        }

        public void windowActivated(WindowEvent arg0) {
        }

        public void windowDeactivated(WindowEvent arg0) {
        }
    }

    public void addListeners() {
        mainFrame = this;
        authorizeButton.addActionListener(new authorizeActionListener());
        connectionButton.addActionListener(new connectionActionListener());
        inputMessageButton.addActionListener(new enterMessageActionListener());
        this.addWindowListener(new closedWindowListener());

        inputMessageField.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == VK_ENTER) {
                    sendMessage();
                }
            }

        });
    }

    public static void main(String[] args) {
        new MainFrame().addListeners();
    }
}
