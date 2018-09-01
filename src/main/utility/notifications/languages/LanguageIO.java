package main.utility.notifications.languages;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for saving and loading .txt files related to the last used language.
 * @author Alessandro Polcini
 */
public class LanguageIO {

    private static final String LAST_USED_LANGUAGE = "last_used_language.txt";

    /**
     * Loads the last used language into the program.
     *
     * @return The last used language, as a {@code Languages} enum object.
     * @throws IllegalArgumentException If the content of the .txt file has an illegal format.
     */
    public static Languages loadLastUsedLanguage() throws IllegalArgumentException {
        File file = new File("resources\\languages\\" + LAST_USED_LANGUAGE);

        try (
            Scanner scanner = new Scanner(file)
        ) {
            return Languages.fromTxtFileName(scanner.nextLine());
        }
        catch(Exception e) {
            Logger.getLogger("Notifications").log(Level.SEVERE, "ERROR");
        }

        throw new IllegalArgumentException();
    }

    /**
     * Saves the current language to a .txt file.
     * @param currentLanguage The current language.
     */
    public static void saveLastUsedLanguage(Languages currentLanguage) {
        File file = new File("resources\\languages\\" + LAST_USED_LANGUAGE);

        try (
            BufferedWriter bw = new BufferedWriter(new FileWriter(file))
        ) {
            bw.write(currentLanguage.getLanguageTxtFileName());
        }
        catch(Exception e) {
            Logger.getLogger("Notifications").log(Level.SEVERE, "ERROR");
        }
    }

}
