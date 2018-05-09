package main.gui.graphic.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Manuel Gallina
 */
public class BackgroundImagePanel extends JPanel {
    private Image backgroundImage;

    public BackgroundImagePanel(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, this);
    }
}