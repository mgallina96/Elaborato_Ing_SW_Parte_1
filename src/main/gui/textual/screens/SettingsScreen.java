package main.gui.textual.screens;
import main.utility.notifications.Notifications;
import main.utility.notifications.languages.Languages;

/**
 * Class:
 *
 * @author Alessandro.
 */
public class SettingsScreen extends Screen {

    public SettingsScreen() {
        System.out.printf("%s %s%s%n",
                Notifications.getMessage("PROMPT_CHOOSE_LANGUAGE"),
                Notifications.getMessage("MSG_AVAILABLE_LANGUAGES"),
                Languages.getLanguageList());
        String lang = getScanner().nextLine();

        try {
            Notifications.setLanguage(Languages.fromString(lang.toUpperCase()));
        }
        catch(IllegalArgumentException iaEx) {
            System.out.println(Notifications.getMessage("ERR_LANGUAGE_UNAVAILABLE"));
        }
    }

}
