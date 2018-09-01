package main.utility.notifications.languages;

/**
 * Enumeration of all available languages with useful methods for getting information about them.
 * @author Alessandro Polcini
 */
public enum Languages {

    ITALIAN("ITA"),
    ENGLISH("ENG");

    private String languageName;
    private String languageTxtFileName;

    Languages(String languageName) {
        this.languageName = languageName;
        this.languageTxtFileName = "lang_" + languageName.toLowerCase() + ".txt";
    }

    /**
     * Getter for the name of the currently selected language.
     * @return The current language name.
     */
    public String getLanguageName() {
        return this.languageName;
    }

    /**
     * Getter for the file name associated with the currently selected language.
     * <p>
     * Example: ITA -> lang_ita.txt
     *
     * @return The .txt file name for the current language.
     */
    public String getLanguageTxtFileName() {
        return languageTxtFileName;
    }

    /**
     * Returns the enum constant of this type with the specified .txt file name.
     *
     * @param txtFileName The {@code String} file name associated with the enum constant.
     * @return The enum constant with the specified name.
     * @throws IllegalArgumentException If the {@code String} doesn't match any entry in the enum.
     */
    public static Languages fromTxtFileName(String txtFileName) {
        for(Languages l : Languages.values()) {
            if(l.languageTxtFileName.equalsIgnoreCase(txtFileName)) {
                return l;
            }
        }

        throw new IllegalArgumentException();
    }

    /**
     * Returns the enum constant of this type with the specified language name.
     *
     * @param languageName The {@code String} file name associated with the enum constant.
     * @return The enum constant with the specified language name.
     * @throws IllegalArgumentException If the {@code String} doesn't match any entry in the enum.
     */
    public static Languages fromString(String languageName) {
        for(Languages l : Languages.values()) {
            if(l.languageName.equalsIgnoreCase(languageName)) {
                return l;
            }
        }

        throw new IllegalArgumentException();
    }

    /**
     * Iterates over the values of the {@link Languages} enum, building a {@code String} with them.
     * @return A String containing all available languages.
     */
    public static String getLanguageList() {
        Languages[] languageList = Languages.values();
        StringBuilder list = new StringBuilder();
        int len = languageList.length - 1;
        int i;

        for(i = 0; i < len; i++)
            list.append(languageList[i].getLanguageName()).append(", ");

        list.append(languageList[i].getLanguageName()).append(".");

        return list.toString();
    }
}
