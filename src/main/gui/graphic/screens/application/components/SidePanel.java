package main.gui.graphic.screens.application.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.gui.graphic.Resources.BS_SIDE_BAR_PATH;

/**
 * @author Manuel Gallina
 */
public class SidePanel extends JPanel {
    private static final Dimension MIN_PANEL_SIZE = new Dimension(60, 600);
    private static final Dimension PREF_PANEL_SIZE = new Dimension(300, 900);
    private static final Dimension MAX_PANEL_SIZE = new Dimension(300, 1080);

    public SidePanel(MainPanel mainPanel) {
        this.setMinimumSize(MIN_PANEL_SIZE);
        this.setPreferredSize(PREF_PANEL_SIZE);
        this.setMaximumSize(MAX_PANEL_SIZE);

        this.setLayout(null);

        SideBarButton anagrafica = new SideBarButton("anagrafica");
        anagrafica.setBounds(25,350,250,40);
        anagrafica.addActionListener(e -> mainPanel.initAnagrafica());

        SideBarButton gestioneFil = new SideBarButton("Gestione FIL");
        gestioneFil.setBounds(25,400,250,40);
        gestioneFil.addActionListener(e -> mainPanel.initGestioneFil());

        SideBarButton temp1 = new SideBarButton("Temp1");
        temp1.setBounds(25,450,250,40);

        SideBarButton temp2 = new SideBarButton("Temp2");
        temp2.setBounds(25,500,250,40);

        this.add(anagrafica);
        this.add(gestioneFil);
        this.add(temp1);
        this.add(temp2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File(BS_SIDE_BAR_PATH)), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
