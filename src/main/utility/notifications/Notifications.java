package main.utility.notifications;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class that stores useful messages, warnings and notifications for this particular program.
 *
 * @author Alessandro Polcini
 */
public class Notifications {

    public static final String LANGUAGE_ITA = "lang_ita.txt";
    public static final String LANGUAGE_ENG = "lang_eng.txt";

    private static final String DELIMITER = " -@ ";
    private static HashMap<String, String> messages;
    private static String currentLanguage;

    private Notifications() {
        //sets english as the default language
        messages = new HashMap<>();
        currentLanguage = LANGUAGE_ENG;
    }

    /**
     * Sets the language of the notifications..
     *
     * @param language The language to be set.
     */
    public static void setLanguage(String language) {
        new Notifications();

        if(!language.equals(currentLanguage)) {
            currentLanguage = language;
        }

        updateLanguage();
    }

    private static void updateLanguage() {
        messages.clear();

        File file = new File("resources\\languages\\" + currentLanguage);

        try (
            Scanner scanner = new Scanner(file)
        ) {
            while(scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(DELIMITER);

                messages.put(split[0], split[1].replaceAll("\\\\t", "\t"));
            }
        }
        catch(Exception e) {
            Logger.getLogger("Notifications").log(Level.SEVERE, "ERROR");
        }
    }

    public static String getMessage(String messageName) {
        return messages.get(messageName);
    }
}

