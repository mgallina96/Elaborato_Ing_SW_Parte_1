package main.gui.graphic.screens.start;

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
 * @author Manuel Gallina
 */
public class SignUpScreen {
    private static final Dimension FRAME_SIZE = new Dimension(960, 540);

    private SystemController controller;

    private JFrame frame;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JTextField birthdayField;
    private JPasswordField confirmPasswordField;
    private JPasswordField passwordField;
    private JTextField warningField;

    private LoginScreen loginScreen;

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

    private void checkLogin() {

    }

    /** Inizializza il pannello di login. */
    public void init(SystemController controller) {
        this.controller = controller;

        loginScreen = new LoginScreen();

        textFont = bsTextFont.deriveFont(Font.PLAIN, 29);
        warningFont = bsTextFont.deriveFont(Font.PLAIN, 19);

        try {
            buildFrame();
        } catch (IOException e) {
            e.printStackTrace();
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

        BackgroundImagePanel background = new BackgroundImagePanel(BS_SIGNUP_SCREEN_PATH);
        background.setBounds(0, 0, 960, 540);

        firstNameField = buildFirstNameField();
        lastNameField = buildLastNameField();
        usernameField = buildUsernameField();
        birthdayField = buildBirthdayField();
        passwordField = buildPasswordField();
        confirmPasswordField = buildPasswordConfirmField();
        warningField = buildWarningField();

        frame.add(background);
        frame.add(usernameField);
        frame.add(passwordField);
        frame.add(warningField);
        frame.add(birthdayField);
        frame.add(firstNameField);
        frame.add(lastNameField);
        frame.add(confirmPasswordField);

        frame.add(buildExitButton());
        frame.add(buildSignUpButton());
        frame.add(buildBackButton());

        frame.setVisible(true);
    }

    private JButton buildBackButton() {
        return new JButton();
    }

    private JButton buildSignUpButton() {
        return new JButton();
    }

    private JTextField buildWarningField() {
        return null;
    }

    private JPasswordField buildPasswordConfirmField() {
        return null;
    }

    private JPasswordField buildPasswordField() {
        return null;
    }

    private JTextField buildBirthdayField() {
        return null;
    }

    private JTextField buildLastNameField() {
        return null;
    }

    private JTextField buildFirstNameField() {
        return null;
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

    private JButton buildExitButton() {
        JButton exit = buildButton(920, 10, 30, 30);
        exit.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        return exit;
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
}
