package main.gui.textual.screens;

import main.utility.notifications.Notifications;

/**
 * Class:
 *
 * @author Alessandro.
 */
public class SettingsScreen extends Screen {

    public SettingsScreen() {
        System.out.println(Notifications.getMessage("PROMPT_CHOOSE_LANGUAGE"));
        String lang = getScanner().nextLine();

        if(lang.equalsIgnoreCase("ita"))
            Notifications.setLanguage(Notifications.LANGUAGE_ITA);
        else if(lang.equalsIgnoreCase("eng"))
            Notifications.setLanguage(Notifications.LANGUAGE_ENG);
        else
            System.out.println(Notifications.getMessage("ERR_LANGUAGE_UNAVAILABLE"));
    }

}
