package main.gui.graphic.screens.application;

import main.gui.graphic.screens.application.components.MainPanel;
import main.gui.graphic.screens.application.components.SidePanel;

import javax.swing.*;
import java.awt.*;

import static main.gui.graphic.Resources.BS_ICON_PATH;

/**
 * @author Manuel Gallina
 */
public class AppScreen {
    private static final Dimension MIN_WINDOW_SIZE = new Dimension(960, 600);
    private static final Dimension PREF_WINDOW_SIZE = new Dimension(1600, 900);
    private static final Dimension MAX_WINDOW_SIZE = new Dimension(1920, 1080);

    private BorderLayout layout;

    private JFrame frame;
    private JPanel sidePanel;
    private MainPanel mainPanel;

    public void init() {
        frame = buildFrame();
    }

    private JFrame buildFrame() {
        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        layout = new BorderLayout();
        frame.setLayout(layout);

        frame.setMinimumSize(MIN_WINDOW_SIZE);
        frame.setPreferredSize(PREF_WINDOW_SIZE);
        frame.setMaximumSize(MAX_WINDOW_SIZE);

        frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);

        frame.setIconImage(new ImageIcon(BS_ICON_PATH).getImage());
        frame.setTitle("Gestionale Banco di Comunità");

        mainPanel = new MainPanel();
        sidePanel = new SidePanel(mainPanel);
        frame.add(sidePanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        return frame;
    }
}
