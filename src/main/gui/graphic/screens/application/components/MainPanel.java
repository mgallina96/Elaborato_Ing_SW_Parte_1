package main.gui.graphic.screens.application.components;

import main.gui.graphic.screens.application.components.anagrafica.Anagrafica;
import main.gui.graphic.screens.application.components.gestioneFil.GestioneFil;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manuel Gallina
 */
public class MainPanel extends JPanel {
    private static final Dimension MIN_PANEL_SIZE = new Dimension(900, 600);
    private static final Dimension PREF_PANEL_SIZE = new Dimension(1300, 900);
    private static final Dimension MAX_PANEL_SIZE = new Dimension(1620, 1080);

    private Anagrafica anagrafica;
    private GestioneFil gestioneFil;

    public MainPanel() {
        anagrafica = new Anagrafica();
        gestioneFil = new GestioneFil();

        this.setMinimumSize(MIN_PANEL_SIZE);
        this.setPreferredSize(PREF_PANEL_SIZE);
        this.setMaximumSize(MAX_PANEL_SIZE);

        this.setLayout(new BorderLayout());

        this.add(anagrafica, BorderLayout.CENTER);
        anagrafica.setVisible(false);

        this.add(gestioneFil, BorderLayout.CENTER);
        gestioneFil.setVisible(false);

        this.setBackground(new Color(206, 206, 206));
    }

    public void initAnagrafica() {
        anagrafica.init();
    }

    public void initGestioneFil() {
        gestioneFil.init();
    }
}
