package main.gui.graphic;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Manuel Gallina
 */
public class Resources {
    public static final String BS_LOGIN_SCREEN_PATH = "resources/images/BS-Login.png";
    public static final String BS_SIDE_BAR_PATH = "resources/images/BS_SideBar.png";
    public static final String BS_ICON_PATH = "resources/images/BS_Icon.png";

    private static final String BS_TEXT_FONT_PATH = "resources/fonts/SourceSansPro-Semibold.ttf";
    private static final String BS_ICON_FONT_PATH = "";

    public static Font bsTextFont;

    public static void init() {
        try {
            bsTextFont = Font.createFont(Font.TRUETYPE_FONT, new File(BS_TEXT_FONT_PATH));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
