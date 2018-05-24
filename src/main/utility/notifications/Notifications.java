package main.utility.notifications;

/**
 * Utility class that stores useful messages, warnings and notifications for this particular program.
 *
 * @author Alessandro Polcini
 */
public class Notifications {

    private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;

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
    public static String MSG_FOLDER_CONTENTS;
    public static String MSG_MOVE_TO_LOGIN;
    public static String MSG_BORROW_SUCCESSFUL;
    public static String MSG_LOAN_LIST_ALL;
    public static String MSG_LOAN_LIST_SINGLE;
    public static String MSG_EXTEND_SUCCESSFUL;

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
    public static String PROMPT_WHICH_FOLDER;
    public static String PROMPT_REMOVE_CONFIRMATION;
    public static String PROMPT_SEARCH_FOR_MEDIA;
    public static String PROMPT_SEARCH_FOR_MEDIA_TO_BORROW;
    public static String PROMPT_BORROW_CONFIRMATION;
    public static String PROMPT_SEARCH_FOR_LOANS;
    public static String PROMPT_CHOOSE_MEDIA_TO_EXTEND;

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
    public static String ERR_CLASS_NOT_FOUND;
    public static String ERR_FILE_NOT_FOUND;
    public static String ERR_MSG_INVALID_INPUT;
    public static String ERR_USER_ALREADY_PRESENT;
    public static String ERR_CANNOT_RENEW;
    public static String ERR_FILTERED_MEDIA_LIST_EMPTY;
    public static String ERR_MEDIA_NOT_PRESENT;
    public static String ERR_MEDIA_ALREADY_PRESENT;
    public static String ERR_INVALID_PATH;
    public static String ERR_PATH_NOT_PRESENT;
    public static String ERR_BORROW_FAILED;
    public static String ERR_MEDIA_NOT_AVAILABLE;
    public static String ERR_CANNOT_BORROW;
    public static String ERR_USER_NOT_PRESENT;
    public static String ERR_WRONG_PASSWORD;
    public static String ERR_LOAN_LIST_EMPTY;
    public static String ERR_CANNOT_EXTEND;

    //generic useful messages
    public static final String SEPARATOR = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

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

    /**
     * Returns the instance of the {@code Notifications class}.
     *
     * @return The instance of the {@code Notifications class}.
     */
    public static Notifications getInstance() {
        if(notifications == null)
            notifications = new Notifications(DEFAULT_LANGUAGE);
        return notifications;
    }

    /**
     * Sets the language of the notifications..
     *
     * @param language The language to be set.
     */
    public void setLanguage(Language language) {
        this.language = language;
        language.initializeLanguages();
    }

    /**
     * Getter for the language used within the application.
     *
     * @return The language.
     */
    public Language getLanguage() {
        return language;
    }

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
                MSG_FOLDER_CONTENTS = "Ecco i contenuti della cartella ";
                MSG_MOVE_TO_LOGIN = "È consigliato spostarsi alla sezione login e provare ad accedere con questi dati.";
                MSG_BORROW_SUCCESSFUL = "Il media è stato preso in prestito!";
                MSG_LOAN_LIST_ALL = "Ecco la lista di tutti i prestiti effettuati agli utenti seguenti: ";
                MSG_LOAN_LIST_SINGLE = "Ecco la lista dei tuoi prestiti: ";
                MSG_EXTEND_SUCCESSFUL = "Prestito prorogato correttamente.";
                PROMPT_FIRST_NAME = "Nome (si prega di inserire un nome valido): ";
                PROMPT_LAST_NAME = "Cognome (si prega di inserire un cognome valido): ";
                PROMPT_USERNAME = "Username: ";
                PROMPT_PASSWORD = "Password: ";
                PROMPT_BIRTHDAY = "Compleanno (formato accettato = GG/MM/AAAA): ";
                PROMPT_PRESENT_USER_MULTIPLE_CHOICE = "(1) USCIRE SENZA SALVARE\t|\t(2) CAMBIARE I CAMPI";
                PROMPT_BIBLIO_INITIAL_CHOICES = "(1) ENTRA\t|\t(2) REGISTRATI\t|\t(3) ESCI";
                PROMPT_OPERATOR_CHOICES = "(1) AGIUNGI UN MEDIA\t|\t(2) RIMUOVI UN MEDIA\t|\t(3) MOSTRA I MEDIA PER CARTELLE\t|\t(4) CERCA MEDIA\t|\t(5) MOSTRA TUTTI GLI UTENTI ISCRITTI\t|\t(6) MOSTRA TUTTI I PRESTITI EFFETTUATI\t|\t(7) ESCI";
                PROMPT_CUSTOMER_CHOICES = "(1) RINNOVA LA TUA ISCRIZIONE!\t|\t(2) RICHIEDI UN PRESTITO\t|\t(3) RICHIEDI UNA PROROGA\t|\t(4) CERCA UNA RISORSA\t|\t(5) ESCI";
                PROMPT_SIGN_UP_CONFIRMATION = "Confermi l'iscrizione? (y/n)";
                PROMPT_REMOVE_CONFIRMATION = "Confermi la rimozione? (y/n)";
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
                PROMPT_SEARCH_FOR_MEDIA = "Si prega di inserire una o più parole chiave relative al media che si desidera trovare (inserire !quit per uscire): ";
                PROMPT_SEARCH_FOR_MEDIA_TO_BORROW = "Si prega di inserire una o più parole chiave relative al media che si desidera prendere in prestito (inserire !quit per uscire): ";
                PROMPT_REMOVE_MEDIA_ID = "Si prega di selezionare l'ID del media da rimuovere.";
                PROMPT_SELECT_PATH = "Inserire il percorso in cui si vuole mettere il media selezionando volta per volta la cartella desiderata.";
                PROMPT_EXPIRY_IMMINENT = "ATTENZIONE! SCADENZA ISCRIZIONE IMMINENTE";
                PROMPT_WHICH_FOLDER = "Inserire il percorso di cui si vuole vedere il contenuto, selezionando volta per volta la cartella desiderata.";
                PROMPT_BORROW_CONFIRMATION = "Confermi il prestito? (y/n)";
                PROMPT_SEARCH_FOR_LOANS = "Ecco la lista dei media presi in prestito. Si prega di selezionare il media di cui si vuole prorogare il prestito.";
                PROMPT_CHOOSE_MEDIA_TO_EXTEND = "Scegliere l'ID del media di cui si vuole estendere il prestito.";
                ERR_NOT_OF_AGE = "Attenzione: l'utente è minorenne. Si prega di riempire i campi da capo.";
                ERR_INVALID_NAME = "Sembra che questo nome abbia un formato non valido, si prega di inserire nuovamente un nome valido.";
                ERR_INVALID_DATE = "Questa data di nascita ha un formato non valido. Si prega di inserire una data di nascita valida.";
                ERR_INVALID_YEAR = "Questo anno ha un formato non valido. Si prega di inserire un anno valido.";
                ERR_LOGIN_FAILED = "Login fallito: username o password sbagliati.";
                ERR_SIGN_UP_FAILED = "Iscrizione fallita.";
                ERR_SIGN_UP_ABORTED = "Iscrizione terminata senza successo.";
                ERR_SAVING_DATABASE = "C'è stato un errore durante il salvataggio del seguente database in un file .ser: ";
                ERR_LOADING_DATABASE = "C'è stato un errore durante il caricamento del seguente database: ";
                ERR_LOADING_FILESYSTEM = "C'è stato un errore durante il caricamento del file system.";
                ERR_CLASS_NOT_FOUND = "Errore! La classe seguente non è stata trovata: ";
                ERR_FILE_NOT_FOUND = "Errore! Il file appartenente alla classe seguente non è stato trovato: ";
                ERR_MSG_INVALID_INPUT = "Input non valido.";
                ERR_USER_ALREADY_PRESENT = "Questo nome utente è già presente nel database.";
                ERR_CANNOT_RENEW = "È troppo presto per effettuare il rinnovo dell'iscrizione.";
                ERR_FILTERED_MEDIA_LIST_EMPTY = "Nessun media trovato. Terminazione in corso...";
                ERR_MEDIA_NOT_PRESENT = "L'ID selezionato non corrisponde ad alcun media nel database.";
                ERR_MEDIA_ALREADY_PRESENT = "Il media inserito è già presente nel database.";
                ERR_INVALID_PATH = "Il percorso inserito sembra essere non valido. Si prega di inserire nuovamente un percorso valido. (cartella\\sottocartella\\sottocartella...)";
                ERR_PATH_NOT_PRESENT = "Il percorso inserito non esiste.";
                ERR_BORROW_FAILED = "Non è stato possibile prendere in prestito il media.";
                ERR_MEDIA_NOT_AVAILABLE = "Il media selezionato non è disponibile al momento.";
                ERR_CANNOT_BORROW = "Tetto massimo di prestiti raggiunto! Non è possibile prendere in prestito altri media al momento.";
                ERR_USER_NOT_PRESENT = "Il nome utente inserito non è presente. Si prega di verificare che il nome utente sia corretto, oppure di registrarsi.";
                ERR_WRONG_PASSWORD = "Password errata.";
                ERR_LOAN_LIST_EMPTY = "Non hai effettuato alcun prestito";
                ERR_CANNOT_EXTEND = "È troppo presto per estendere questo prestito.";
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
                MSG_FOLDER_CONTENTS = "Here are the contents of the folder ";
                MSG_MOVE_TO_LOGIN = "Try logging in with these credentials.";
                MSG_BORROW_SUCCESSFUL = "Media item successfully borrowed!";
                MSG_LOAN_LIST_ALL = "Here's the list of all loans that have been granted to the following users: ";
                MSG_LOAN_LIST_SINGLE = "Here's the list of your loans: ";
                MSG_EXTEND_SUCCESSFUL = "Loan extended successfully.";
                PROMPT_FIRST_NAME = "First name (please insert a valid first name): ";
                PROMPT_LAST_NAME = "Last name (please insert a valid last name): ";
                PROMPT_USERNAME = "Username: ";
                PROMPT_PASSWORD = "Password: ";
                PROMPT_BIRTHDAY = "Birthday (accepted format = DD/MM/YYYY): ";
                PROMPT_PRESENT_USER_MULTIPLE_CHOICE = "(1) EXIT WITHOUT SAVING\t|\t(2) CHANGE FIELDS";
                PROMPT_BIBLIO_INITIAL_CHOICES = "(1) LOGIN\t|\t(2) SIGN UP\t|\t(3) EXIT";
                PROMPT_OPERATOR_CHOICES = "(1) ADD A MEDIA ITEM\t|\t(2) REMOVE A MEDIA ITEM\t|\t(3) SHOW MEDIA ITEMS BY FOLDER\t|\t(4) SEARCH FOR MEDIA ITEMS\t|\t(5) SHOW ALL SUBSCRIBED USERS\t|\t(6) SHOW ALL LOANS\t|\t(7) LOGOUT";
                PROMPT_CUSTOMER_CHOICES = "(1) RENEW YOUR SUBSCRIPTION!\t|\t(2) REQUEST A LOAN\t|\t(3) EXTEND YOUR LOAN\t|\t(4) SEARCH FOR MEDIA ITEMS\t|\t(5) LOGOUT";
                PROMPT_SIGN_UP_CONFIRMATION = "Are you sure you want to submit this form? (y/n)";
                PROMPT_REMOVE_CONFIRMATION = "Are you sure you want to remove this media item? (y/n)";
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
                PROMPT_SEARCH_FOR_MEDIA = "Please insert one or more keywords for the media item you're looking for (insert !quit to quit): ";
                PROMPT_SEARCH_FOR_MEDIA_TO_BORROW = "Please insert one or more keywords for the media item you'd like to borrow (insert !quit to quit): ";
                PROMPT_REMOVE_MEDIA_ID = "Please choose the ID for the item you wish to remove.";
                PROMPT_SELECT_PATH = "Please choose the folder path you wish to put the media item in by selecting the desired folders step by step.";
                PROMPT_EXPIRY_IMMINENT = "WARNING! SUBSCRIPTION EXPIRY IMMINENT!";
                PROMPT_WHICH_FOLDER = "Please choose the path you wish to see the contents of, by selecting the desired folders step by step.";
                PROMPT_BORROW_CONFIRMATION = "Are you sure you want to borrow this media item? (y/n)";
                PROMPT_SEARCH_FOR_LOANS = "Here's the list of the media items you borrowed. Please select the media item whose loan you wish to extend.";
                PROMPT_CHOOSE_MEDIA_TO_EXTEND = "Please select the ID of the media whose loan you wish to extend.";
                ERR_NOT_OF_AGE = "Warning: user is underage. Please re-fill this form.";
                ERR_INVALID_NAME = "Looks like this name has an invalid format, please re-insert a valid name.";
                ERR_INVALID_DATE = "This birth date has an invalid format. Please re-insert a valid date.";
                ERR_INVALID_YEAR = "This year has an invalid format. Please re-insert a valid year.";
                ERR_LOGIN_FAILED = "Login failed: wrong username or password.";
                ERR_SIGN_UP_FAILED = "Sign up failed.";
                ERR_SIGN_UP_ABORTED = "Sign up aborted.";
                ERR_SAVING_DATABASE = "An error occurred while saving the following database to a .ser file: ";
                ERR_LOADING_DATABASE = "An error occurred while loading the following database: ";
                ERR_LOADING_FILESYSTEM = "An error occurred while loading the file system.";
                ERR_CLASS_NOT_FOUND = "Error! The following class was not found: ";
                ERR_FILE_NOT_FOUND = "Error! The file belonging to the following class was not found: ";
                ERR_MSG_INVALID_INPUT = "Invalid input.";
                ERR_USER_ALREADY_PRESENT = "This username is already present in our database.";
                ERR_CANNOT_RENEW = "It's too early to renew your subscription.";
                ERR_FILTERED_MEDIA_LIST_EMPTY = "No media items match your search. Aborting...";
                ERR_MEDIA_NOT_PRESENT = "The chosen ID doesn't match any media in the database.";
                ERR_MEDIA_ALREADY_PRESENT = "The media you're trying to add is already in our database.";
                ERR_INVALID_PATH = "The path you inserted seems to be invalid. Please re-insert a valid path. (accepted format: folder\\subfolder\\subfolder...)";
                ERR_PATH_NOT_PRESENT = "The path you inserted doesn't exist.";
                ERR_BORROW_FAILED = "There has been an error while borrowing this media item.";
                ERR_MEDIA_NOT_AVAILABLE = "The selected media is not available at the moment.";
                ERR_CANNOT_BORROW = "Upper loan limit reached! It's not possible to borrow any more media items at the moment.";
                ERR_USER_NOT_PRESENT = "The requested username couldn't be found. Please check the spelling or sign up.";
                ERR_WRONG_PASSWORD = "Wrong password.";
                ERR_LOAN_LIST_EMPTY = "There are no loans associated to this user.";
                ERR_CANNOT_EXTEND = "It's too early to extend this loan.";
            }
        }
    }
}

