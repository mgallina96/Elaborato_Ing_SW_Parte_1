package main.gui.graphic.screens;

import main.SystemController;
import main.gui.graphic.components.BackgroundImagePanel;
import main.gui.graphic.screens.application.AppScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static main.gui.graphic.Resources.*;

/**
 * Finestra di login per l'applicazione.
 *
 * @author Manuel Gallina
 */
public class LoginScreen{

    private static final Dimension FRAME_SIZE = new Dimension(960, 540);

    private SystemController controller;

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField warningField;

    private AppScreen appScreen;

    private Font textFont;
    private Font warningFont;

    private MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    };
    private ActionListener enterListener = e -> checkLogin();

    /**
     * Inizializza il pannello di login.
     */
    public void init(SystemController controller) {
        this.controller = controller;
        appScreen = new AppScreen();

        textFont = bsTextFont.deriveFont(Font.PLAIN, 29);
        warningFont = bsTextFont.deriveFont(Font.PLAIN, 19);

        try {
            buildFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLogin() {
        if (controller.checkUserLogin(usernameField.getText(), String.copyValueOf(passwordField.getPassword()))) {
            frame.setVisible(false);
            appScreen.init();
        }
        else {
            warningField.setText("Username o Password errati. Riprovare.");
            passwordField.setText("");
        }
    }

    private void buildFrame() throws IOException {
        frame = new JFrame();
        frame.setLayout(null);
        frame.setIconImage(new ImageIcon(BS_ICON_PATH).getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setMinimumSize(FRAME_SIZE);
        frame.setPreferredSize(FRAME_SIZE);
        frame.setMaximumSize(FRAME_SIZE);

        frame.setResizable(false);
        frame.setUndecorated(true);

        frame.setLocationRelativeTo(null);

        BackgroundImagePanel background = new BackgroundImagePanel(BS_LOGIN_SCREEN_PATH);
        background.setBounds(0, 0, 960, 540);

        usernameField = buildUsernameField();
        passwordField = buildPasswordField();
        warningField = buildWarningField();

        frame.add(background);
        frame.add(usernameField);
        frame.add(passwordField);
        frame.add(warningField);
        frame.add(buildExitButton());
        frame.add(buildSignUpButton());
        frame.add(buildLoginButton());

        frame.setVisible(true);
    }

    private JButton buildLoginButton() {
        JButton login = buildButton(538, 389, 130, 50);
        login.addActionListener(e -> {
            checkLogin();
            System.out.println(usernameField.getText() + "\n" + String.copyValueOf(passwordField.getPassword()));
        });
        return login;
    }

    private JTextField buildWarningField() {
        JTextField warning = new JTextField(1);
        warning.setFocusable(false);
        warning.setOpaque(true);
        warning.setBorder(null);
        warning.setBounds(392, 362, 330, 26);
        warning.setBackground(new Color(255,255,255));
        warning.setFont(warningFont);
        warning.setForeground(new Color(214, 48, 40));

        return warning;
    }

    private JButton buildExitButton() {
        JButton exit = buildButton(920, 10, 30, 30);
        exit.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        return exit;
    }

    private JButton buildSignUpButton() {
        JButton signUp = buildButton(705, 389, 160, 50);
        signUp.addActionListener(e -> {
            //signUpScreen.init();
        });
        return signUp;
    }

    private JButton buildButton(int x, int y, int width, int height) {
        JButton button = new JButton();
        button.addMouseListener(mouseListener);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);

        return button;
    }

    private JTextField buildUsernameField() {
        JTextField usernameField = new JTextField(1);
        usernameField.addActionListener(enterListener);
        usernameField.setOpaque(true);
        usernameField.setBorder(null);
        usernameField.setBounds(392, 222, 465, 38);
        usernameField.setBackground(new Color(216,216,216));
        usernameField.setFont(textFont);
        usernameField.addMouseListener(mouseListener);

        return usernameField;
    }

    private JPasswordField buildPasswordField() {
        JPasswordField passwordField = new JPasswordField(1);
        passwordField.addActionListener(enterListener);
        passwordField.setOpaque(true);
        passwordField.setBorder(null);
        passwordField.setBounds(392, 323, 465, 38);
        passwordField.setBackground(new Color(216,216,216));
        passwordField.setFont(textFont);
        passwordField.addMouseListener(mouseListener);

        return passwordField;
    }
}
