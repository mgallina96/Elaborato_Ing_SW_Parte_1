package main.gui.graphic.screens.application.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static main.gui.graphic.Resources.bsTextFont;

/**
 * @author Manuel Gallina
 */
public class SideBarButton extends JButton {
    private static final Dimension MIN_PANEL_SIZE = new Dimension(50, 40);
    private static final Dimension PREF_PANEL_SIZE = new Dimension(250, 40);
    private static final Dimension MAX_PANEL_SIZE = new Dimension(250, 40);

    private static final int RADIUS = 15;

    private SideBarButton button;

    SideBarButton(String text) {
        button = this;

        Font textFont = bsTextFont.deriveFont(Font.PLAIN, 20);
        this.setFont(textFont);
        this.setText(text);

        this.setBackground(Color.white);
        this.setFocusable(true);

        this.addMouseListener(new MouseListener() {
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
                button.setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.white);
            }
        });

        this.setContentAreaFilled(false);

        this.setMinimumSize(MIN_PANEL_SIZE);
        this.setPreferredSize(PREF_PANEL_SIZE);
        this.setMaximumSize(MAX_PANEL_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.white);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, RADIUS, RADIUS);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.lightGray);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, RADIUS, RADIUS);
    }
}
