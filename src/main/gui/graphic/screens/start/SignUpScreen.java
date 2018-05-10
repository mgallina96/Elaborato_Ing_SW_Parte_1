package main.gui.graphic.screens.start;

import main.SystemController;
import main.gui.graphic.components.BackgroundImagePanel;
import main.utility.InputParserUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;

import static main.gui.graphic.Resources.*;
import static main.utility.notifications.Notifications.ERR_INVALID_NAME;

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
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

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

        frame.add(firstNameField);
        frame.add(lastNameField);
        frame.add(usernameField);
        frame.add(birthdayField);
        frame.add(passwordField);
        frame.add(confirmPasswordField);

        frame.add(warningField);

        frame.add(buildExitButton());
        frame.add(buildSignUpButton());
        frame.add(buildBackButton());

        frame.setVisible(true);
    }

    private JTextField buildFirstNameField() {
        JTextField firstNameField = new JTextField(1);
        firstNameField.setOpaque(true);
        firstNameField.setBorder(null);
        firstNameField.setBounds(356, 160, 255, 38);
        firstNameField.setBackground(new Color(216,216,216));
        firstNameField.setFont(textFont);
        firstNameField.addMouseListener(mouseListener);

        return firstNameField;
    }

    private JTextField buildLastNameField() {
        JTextField lastNameField = new JTextField(1);
        lastNameField.setOpaque(true);
        lastNameField.setBorder(null);
        lastNameField.setBounds(638, 160, 255, 38);
        lastNameField.setBackground(new Color(216,216,216));
        lastNameField.setFont(textFont);
        lastNameField.addMouseListener(mouseListener);

        return lastNameField;
    }

    private JTextField buildUsernameField() {
        JTextField usernameField = new JTextField(1);
        usernameField.setOpaque(true);
        usernameField.setBorder(null);
        usernameField.setBounds(356, 262, 255, 38);
        usernameField.setBackground(new Color(216,216,216));
        usernameField.setFont(textFont);
        usernameField.addMouseListener(mouseListener);

        return usernameField;
    }

    private JTextField buildBirthdayField() {
        JTextField birthdayField = new JTextField(1);
        birthdayField.setOpaque(true);
        birthdayField.setBorder(null);
        birthdayField.setBounds(638, 262, 255, 38);
        birthdayField.setBackground(new Color(216,216,216));
        birthdayField.setFont(textFont);
        birthdayField.addMouseListener(mouseListener);

        return birthdayField;
    }

    private JPasswordField getPasswordField(int i) {
        JPasswordField passwordField = new JPasswordField(1);
        passwordField.setOpaque(true);
        passwordField.setBorder(null);
        passwordField.setBounds(i, 360, 255, 38);
        passwordField.setBackground(new Color(216,216,216));
        passwordField.setFont(textFont);
        passwordField.addMouseListener(mouseListener);

        return passwordField;
    }

    private JPasswordField buildPasswordField() {
        return getPasswordField(356);
    }

    private JPasswordField buildPasswordConfirmField() {
        return getPasswordField(638);
    }

    private JTextField buildWarningField() {
        return new JTextField();
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

    private JButton buildBackButton() {
        JButton back = buildButton(351, 442, 132, 50);
        back.addActionListener(e -> loginScreen.init(controller));
        return back;
    }

    private JButton buildSignUpButton() {
        JButton signUp = buildButton(736, 442, 160, 50);
        signUp.addActionListener(e -> {
            if(!InputParserUtility.isValidName(firstNameField.getText())) {
                firstNameField.setText("");
                warningField.setText(ERR_INVALID_NAME);
            }
            if(!InputParserUtility.isValidName(lastNameField.getText())) {
                lastNameField.setText("");
                warningField.setText(ERR_INVALID_NAME);
            }
            if(!Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                passwordField.setText("");
                confirmPasswordField.setText("");
                warningField.setText("Controlla la password");
            }
            /*if(controller.addUserToDatabase(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    usernameField.getText(),
                    passwordField.getPassword(),
                    InputParserUtility.toGregorianDate(birthdayField.getText())))*/
        });
        return signUp;
    }

    private JButton buildExitButton() {
        JButton exit = buildButton(920, 10, 30, 30);
        exit.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        return exit;
    }
}
