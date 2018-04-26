package main.utility.notifications;

/**
 * Utility class that stores useful messages, warnings and notifications for this particular program.
 *
 * @author Alessandro Polcini
 */
public class Notifications {

    public static final Language ITALIAN = Language.ITALIAN;
    public static final Language ENGLISH = Language.ENGLISH;

    //MESSAGES
    public static final String MSG_BIBLIO_NAME = "BIBLIOTECA SMARTin4t0r 3.0";
    public static String MSG_SIGN_UP_SUCCESSFUL;
    public static String MSG_EXIT_WITHOUT_SAVING;
    public static String MSG_GOODBYE;
    public static String MSG_LOG_OUT;
    public static String MSG_USER_LIST;
    public static String MSG_MEDIA_LIST;
    public static String MSG_FILTERED_MEDIA_LIST;
    public static String MSG_REMOVE_SUCCESSFUL;
    public static String MSG_EXIT_LOGIN;
    public static String MSG_OPERATOR_MENU;
    public static String MSG_CUSTOMER_MENU;
    public static String MSG_ABORT;
    public static String MSG_ADD_SUCCESSFUL;
    public static String MSG_REMINDER_DAYS_LEFT;
    public static String MSG_DAYS;
    public static String MSG_ESCAPE_STRING_MESSAGE;

    //PROMPTS
    public static String PROMPT_FIRST_NAME;
    public static String PROMPT_LAST_NAME;
    public static String PROMPT_USERNAME;
    public static String PROMPT_PASSWORD;
    public static String PROMPT_BIRTHDAY;
    public static String PROMPT_PRESENT_USER_MULTIPLE_CHOICE;
    public static String PROMPT_BIBLIO_INITIAL_CHOICES;
    public static String PROMPT_OPERATOR_CHOICES;
    public static String PROMPT_CUSTOMER_CHOICES;
    public static String PROMPT_SIGN_UP_CONFIRMATION;
    public static String PROMPT_LOGIN_SCREEN;
    public static String PROMPT_SIGN_UP_SCREEN;
    public static String PROMPT_MODIFY_FIELDS;
    public static String PROMPT_RETRY_LOGGING_IN;
    public static String PROMPT_ADD_MEDIA;
    public static String PROMPT_TITLE;
    public static String PROMPT_AUTHOR;
    public static String PROMPT_GENRE;
    public static String PROMPT_PUBLICATION_YEAR;
    public static String PROMPT_PUBLISHER_NAME;
    public static String PROMPT_REMOVE_MEDIA;
    public static String PROMPT_REMOVE_MEDIA_ID;
    public static String PROMPT_SELECT_PATH;
    public static String PROMPT_EXPIRY_IMMINENT;

    //ERRORS
    public static String ERR_NOT_OF_AGE;
    public static String ERR_INVALID_NAME;
    public static String ERR_INVALID_DATE;
    public static String ERR_INVALID_YEAR;
    public static String ERR_LOGIN_FAILED;
    public static String ERR_SIGN_UP_FAILED;
    public static String ERR_SIGN_UP_ABORTED;
    public static String ERR_SAVING_DATABASE;
    public static String ERR_LOADING_DATABASE;
    public static String ERR_LOADING_FILESYSTEM;
    public static String ERR_DATABASE_CLASS_NOT_FOUND;
    public static String ERR_FILESYSTEM_CLASS_NOT_FOUND;
    public static String ERR_FILE_NOT_FOUND;
    public static String ERR_MSG_INVALID_INPUT;
    public static String ERR_USER_ALREADY_PRESENT;
    public static String ERR_CANNOT_RENEW;
    public static String ERR_FILTERED_MEDIA_LIST_EMPTY;
    public static String ERR_MEDIA_NOT_PRESENT;
    public static String ERR_INVALID_PATH;
    public static String ERR_PATH_NOT_PRESENT;

    //generic useful messages
    public static final String SEPARATOR = "--------------------------------------------------------------------------------------------------------------------------------------";

    private enum Language implements Languages {
        ITALIAN {
            @Override
            public void initializeLanguages() {
                MSG_SIGN_UP_SUCCESSFUL = "Registrazione avvenuta con successo. Benvenuto/a!";
                MSG_EXIT_WITHOUT_SAVING = "Uscendo senza salvare...";
                MSG_GOODBYE = "Uscita da " + MSG_BIBLIO_NAME + " in corso... Arrivederci!";
                MSG_LOG_OUT = "Uscita in corso...";
                MSG_USER_LIST = "Ecco la lista di tutti gli utenti iscritti:";
                MSG_MEDIA_LIST = "Ecco la lista di tutti i media:";
                MSG_FILTERED_MEDIA_LIST = "Risultati:";
                MSG_REMOVE_SUCCESSFUL = "Media rimosso con successo.";
                MSG_EXIT_LOGIN = "Uscendo dalla sezione di login...";
                MSG_OPERATOR_MENU = "Benvenuto, operatore!";
                MSG_CUSTOMER_MENU = "Benvenuto, cliente!";
                MSG_ABORT = "Annullamento in corso...";
                MSG_ADD_SUCCESSFUL = "Media aggiunto con successo al percorso: ";
                MSG_REMINDER_DAYS_LEFT = "Giorni che rimangono per rinnovare l'iscrizione:";
                MSG_DAYS = "giorni";
                MSG_ESCAPE_STRING_MESSAGE = "per uscire da questa sezione";
                PROMPT_FIRST_NAME = "Nome (si prega di inserire un nome valido): ";
                PROMPT_LAST_NAME = "Cognome (si prega di inserire un cognome valido): ";
                PROMPT_USERNAME = "Username: ";
                PROMPT_PASSWORD = "Password: ";
                PROMPT_BIRTHDAY = "Compleanno (formato accettato = GG/MM/AAAA): ";
                PROMPT_PRESENT_USER_MULTIPLE_CHOICE = "(1) USCIRE SENZA SALVARE\t|\t(2) CAMBIARE I CAMPI\t|\t(3) RINNOVARE L'ISCRIZIONE";
                PROMPT_BIBLIO_INITIAL_CHOICES = "(1) ENTRA\t|\t(2) REGISTRATI\t|\t(3) ESCI";
                PROMPT_OPERATOR_CHOICES = "(1) AGIUNGI UN MEDIA\t|\t(2) RIMUOVI UN MEDIA\t|\t(3) MOSTRA TUTTI I MEDIA\t|\t(4) MOSTRA TUTTI GLI UTENTI ISCRITTI\t|\t(5) ESCI";
                PROMPT_CUSTOMER_CHOICES = "(1) RINNOVA LA TUA ISCRIZIONE!\t|\t(2) ESCI";
                PROMPT_SIGN_UP_CONFIRMATION = "Sei sicuro/a di voler confermare? (y/n)";
                PROMPT_LOGIN_SCREEN = "Bentornato/a! Entra nella " + MSG_BIBLIO_NAME;
                PROMPT_SIGN_UP_SCREEN = "Ciao! Registrati alla " + MSG_BIBLIO_NAME;
                PROMPT_MODIFY_FIELDS = "Modificare i campi errati per favore.";
                PROMPT_RETRY_LOGGING_IN = "Si prega di riprovare ad entrare usando le credenziali corrette.";
                PROMPT_ADD_MEDIA = "Aggiungere un media per favore.";
                PROMPT_TITLE = "Titolo: ";
                PROMPT_AUTHOR = "Autore: ";
                PROMPT_GENRE = "Genere: ";
                PROMPT_PUBLICATION_YEAR = "Anno di pubblicazione: ";
                PROMPT_PUBLISHER_NAME = "Casa editrice: ";
                PROMPT_REMOVE_MEDIA = "Si prega di inserire una o più parole chiave per trovare il media da rimuovere (inserire !quit per uscire): ";
                PROMPT_REMOVE_MEDIA_ID = "Si prega di selezionare l'ID del media da rimuovere.";
                PROMPT_SELECT_PATH = "Inserire la cartella in cui si vuole mettere il media.\nSi prega di inserire un formato di cartella valido (cartella\\sottocartella\\sottocartella...)";
                PROMPT_EXPIRY_IMMINENT = "ATTENZIONE! SCADENZA ISCRIZIONE IMMINENTE";
                ERR_NOT_OF_AGE = "Attenzione: l'utente è minorenne. Si prega di riempire i campi da capo.";
                ERR_INVALID_NAME = "Sembra che questo nome abbia un formato non valido, si prega di inserire nuovamente un nome valido.";
                ERR_INVALID_DATE = "Questa data di nascita ha un formato non valido. Si prega di inserire una data di nascita valida.";
                ERR_INVALID_YEAR = "Questo anno ha un formato non valido. Si prega di inserire un anno valido.";
                ERR_LOGIN_FAILED = "Login fallito: username o password sbagliati.";
                ERR_SIGN_UP_FAILED = "Iscrizione fallita.";
                ERR_SIGN_UP_ABORTED = "Iscrizione terminata senza successo.";
                ERR_SAVING_DATABASE = "C'è stato un errore durante il salvataggio del database in un file .ser.";
                ERR_LOADING_DATABASE = "C'è stato un errore durante il caricamento del database.";
                ERR_LOADING_FILESYSTEM = "C'è stato un errore durante il caricamento del file system.";
                ERR_DATABASE_CLASS_NOT_FOUND = "Classe Database non trovata.";
                ERR_FILESYSTEM_CLASS_NOT_FOUND = "Classe FileSystem non trovata.";
                ERR_FILE_NOT_FOUND = "Errore: file non trovato.";
                ERR_MSG_INVALID_INPUT = "Input non valido.";
                ERR_USER_ALREADY_PRESENT = "Questo utente è già presente nel database.";
                ERR_CANNOT_RENEW = "È troppo presto per effettuare il rinnovo dell'iscrizione.";
                ERR_FILTERED_MEDIA_LIST_EMPTY = "Nessun media trovato. Terminazione in corso...";
                ERR_MEDIA_NOT_PRESENT = "L'ID selezionato non corrisponde ad alcun media nel database.";
                ERR_INVALID_PATH = "Il percorso inserito sembra essere non valido. Si prega di inserire nuovamente un percorso valido. (cartella\\sottocartella\\sottocartella...)";
                ERR_PATH_NOT_PRESENT = "Il percorso inserito non esiste.";
            }
        },
        ENGLISH {
            @Override
            public void initializeLanguages() {
                MSG_SIGN_UP_SUCCESSFUL = "Sign up successful. Welcome!";
                MSG_EXIT_WITHOUT_SAVING = "Exiting without saving...";
                MSG_GOODBYE = "Exiting " + MSG_BIBLIO_NAME + "... Goodbye!";
                MSG_LOG_OUT = "Logging out...";
                MSG_USER_LIST = "Here is the list of all subscribed users:";
                MSG_MEDIA_LIST = "Here is the list of all media items:";
                MSG_FILTERED_MEDIA_LIST = "Results:";
                MSG_REMOVE_SUCCESSFUL = "Media item removed successfully.";
                MSG_EXIT_LOGIN = "Exiting login section...";
                MSG_OPERATOR_MENU = "Welcome, operator!";
                MSG_CUSTOMER_MENU = "Welcome, customer!";
                MSG_ABORT = "Aborting...";
                MSG_ADD_SUCCESSFUL = "Media item added successfully to: ";
                MSG_REMINDER_DAYS_LEFT = "Days you have left to renew your subscription:";
                MSG_DAYS = "days";
                MSG_ESCAPE_STRING_MESSAGE = "to exit this section";
                PROMPT_FIRST_NAME = "First name (please insert a valid first name): ";
                PROMPT_LAST_NAME = "Last name (please insert a valid last name): ";
                PROMPT_USERNAME = "Username: ";
                PROMPT_PASSWORD = "Password: ";
                PROMPT_BIRTHDAY = "Birthday (accepted format = DD/MM/YYYY): ";
                PROMPT_PRESENT_USER_MULTIPLE_CHOICE = "(1) EXIT WITHOUT SAVING\t|\t(2) CHANGE FIELDS\t|\t(3) RENEW SUBSCRIPTION";
                PROMPT_BIBLIO_INITIAL_CHOICES = "(1) LOGIN\t|\t(2) SIGN UP\t|\t(3) EXIT";
                PROMPT_OPERATOR_CHOICES = "(1) ADD A MEDIA ITEM\t|\t(2) REMOVE A MEDIA ITEM\t|\t(3) SHOW ALL MEDIA ITEMS\t|\t(4) SHOW ALL SUBSCRIBED USERS\t|\t(5) LOGOUT";
                PROMPT_CUSTOMER_CHOICES = "(1) RENEW YOUR SUBSCRIPTION!\t|\t(2) LOGOUT";
                PROMPT_SIGN_UP_CONFIRMATION = "Are you sure you want to submit this form? (y/n)";
                PROMPT_LOGIN_SCREEN = "Welcome back! Log in to " + MSG_BIBLIO_NAME;
                PROMPT_SIGN_UP_SCREEN = "Hi there! Sign up for " + MSG_BIBLIO_NAME;
                PROMPT_MODIFY_FIELDS = "Please modify the wrong fields.";
                PROMPT_RETRY_LOGGING_IN = "Please retry logging in with correct credentials.";
                PROMPT_ADD_MEDIA = "Please add a media item.";
                PROMPT_TITLE = "Title: ";
                PROMPT_AUTHOR = "Author: ";
                PROMPT_GENRE = "Genre: ";
                PROMPT_PUBLICATION_YEAR = "Publication year: ";
                PROMPT_PUBLISHER_NAME = "Publisher: ";
                PROMPT_REMOVE_MEDIA = "Please insert one or more keywords for the media item you wish to remove (insert !quit to quit): ";
                PROMPT_REMOVE_MEDIA_ID = "Please choose the ID for the item you wish to remove.";
                PROMPT_SELECT_PATH = "Insert the folder you wish to put the media item in.\nPlease insert a valid path format (folder\\subfolder\\subfolder...).";
                PROMPT_EXPIRY_IMMINENT = "WARNING! SUBSCRIPTION EXPIRY IMMINENT!";
                ERR_NOT_OF_AGE = "Warning: user is underage. Please re-fill this form.";
                ERR_INVALID_NAME = "Looks like this name has an invalid format, please re-insert a valid name.";
                ERR_INVALID_DATE = "This birth date has an invalid format. Please re-insert a valid date.";
                ERR_INVALID_YEAR = "This year has an invalid format. Please re-insert a valid year.";
                ERR_LOGIN_FAILED = "Login failed: wrong username or password.";
                ERR_SIGN_UP_FAILED = "Sign up failed.";
                ERR_SIGN_UP_ABORTED = "Sign up aborted.";
                ERR_SAVING_DATABASE = "An error occurred while saving the database to a .ser file.";
                ERR_LOADING_DATABASE = "An error occurred while loading the database.";
                ERR_LOADING_FILESYSTEM = "An error occurred while loading the file system.";
                ERR_DATABASE_CLASS_NOT_FOUND = "Database class not found.";
                ERR_FILESYSTEM_CLASS_NOT_FOUND = "FileSystem class not found.";
                ERR_FILE_NOT_FOUND = "Error: file not found.";
                ERR_MSG_INVALID_INPUT = "Invalid input.";
                ERR_USER_ALREADY_PRESENT = "This user is already present in our database.";
                ERR_CANNOT_RENEW = "It's too early to renew your subscription.";
                ERR_FILTERED_MEDIA_LIST_EMPTY = "No media items match your search. Aborting...";
                ERR_MEDIA_NOT_PRESENT = "The chosen ID doesn't match any media in the database.";
                ERR_INVALID_PATH = "The path you inserted seems to be invalid. Please re-insert a valid path. (accepted format: folder\\subfolder\\subfolder...)";
                ERR_PATH_NOT_PRESENT = "The path you inserted doesn't exist.";
            }
        }
    }

    private Language language;
    private static Notifications notifications;

    private Notifications() {
        //sets english as the default language
        this(ENGLISH);
    }

    private Notifications(Language language) {
        this.language = language;
        this.language.initializeLanguages();
    }

    public static Notifications setLanguage(Language language) {
        if(notifications == null)
            notifications = new Notifications(language);

        return notifications;
    }

    public Language getLanguage() {
        return language;
    }
}
