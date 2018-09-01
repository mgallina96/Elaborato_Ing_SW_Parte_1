package main.utility.notifications;
import main.utility.notifications.languages.Languages;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.utility.notifications.languages.LanguageIO.*;

/**
 * Utility class that contains useful textual messages, warnings and notifications for this particular program.
 * <p>
 * All messages are loaded from .txt files that have to follow a specific set of rules in order to be read properly.
 * These messages then get loaded into a HashMap which binds every message to its identifier (e.g.: "Welcome!" gets
 * bound to "MSG_WELCOME"). This identifier can be used to fetch the actual message which will then be printed.
 * <p>
 * All languages are potentially supported.
 *
 * @author Alessandro Polcini
 */
public class Notifications {

    private static HashMap<String, String> messages;
    private static final String DELIMITER = " -@ ";

    private static Languages currentLanguage;

    private Notifications() {
        messages = new HashMap<>();
        currentLanguage = loadLastUsedLanguage();
    }

    /**
     * Language initializer. Ideally, it should be called in the "main" method.
     */
    public static void init() {
        setLanguage(loadLastUsedLanguage());
    }

    /**
     * Sets the language in which the notifications will be shown.
     * @param language The language to be set.
     */
    public static void setLanguage(Languages language) {
        new Notifications();

        if(!language.getLanguageName().equals(currentLanguage.getLanguageName())) {
            currentLanguage = language;
        }

        updateLanguage();
        saveLastUsedLanguage(currentLanguage);
    }

    /**
     * Getter for the desired message.
     *
     * @param messageName The message-specific ID.
     * @return The actual message bound to that {@code messageName}.
     */
    public static String getMessage(String messageName) {
        return messages.get(messageName);
    }

    //changes the language
    private static void updateLanguage() {
        File file = new File("resources\\languages\\" + currentLanguage.getLanguageTxtFileName());

        try (
            Scanner scanner = new Scanner(file)
        ) {
            while(scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(DELIMITER);

                messages.put(split[0], split[1].replaceAll("\\\\t", "\t").replaceAll("\\\\n", "\n"));
            }
        }
        catch(Exception e) {
            Logger.getLogger("Notifications").log(Level.SEVERE, "ERROR");
        }
    }

}

