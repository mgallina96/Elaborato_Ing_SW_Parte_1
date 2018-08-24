package main.model.media;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The {@code Media} class, which contains fields and methods that can be applied to any generic piece of media, such
 * as books, movies, magazines, tv series, video games, etc.
 *
 * @author Alessandro Polcini, Manuel Gallina
 */
public class Media implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681380379098150051L;

    private static final int DEFAULT_MEDIA_IDENTIFIER = 0;
    private static final String DEFAULT_MEDIA_NAME = "Default";
    private static final String DEFAULT_MEDIA_PATH = "null";
    private static final GregorianCalendar DEFAULT_DATE_ADDED = new GregorianCalendar(1970, Calendar.JANUARY, 1);
    private static final int DEFAULT_MEDIA_LICENSES = 1;
    private static final int DEFAULT_EXTENSION_RESTRICTION = 1;
    private static final int DEFAULT_MEDIA_LOAN_LIMIT = 1;
    private static final int DEFAULT_MEDIA_LOAN_VALIDITY_PERIOD_IN_DAYS = 1;

    private String bareItemDetails;

    private int identifier;
    private String mediaName;
    private String path;
    private GregorianCalendar dateAdded;
    private int licenses;
    private int extensionRestriction;
    private int loanLimit;
    private int mediaLoanValidityPeriod;
    private String type;
    private boolean available;

    /**
     * Constructor for the {@code Media} class.
     *
     * @param identifier The media ID number.
     * @param mediaName The media name.
     * @param path The media folder path.
     * @param dateAdded The date the media was added.
     * @param licenses The number of licenses available for this media.
     * @param extensionRestriction The time range in which the user may extend the loan period for this media.
     * @param loanLimit The maximum number of loans a user can request for this category of media.
     * @param mediaLoanValidityPeriod The number of days after which the loan of this media item expires.
     */
    public Media(int identifier, String mediaName, String path, GregorianCalendar dateAdded, int licenses,
                 int extensionRestriction, int loanLimit, int mediaLoanValidityPeriod) {
        this.identifier = identifier;
        this.mediaName = mediaName;
        this.path = path;
        this.dateAdded = dateAdded;
        this.licenses = licenses;
        this.extensionRestriction = extensionRestriction;
        this.loanLimit = loanLimit;
        this.mediaLoanValidityPeriod = mediaLoanValidityPeriod;
        this.type = "MEDIA";
        this.available = true;
    }

    /**
     * Constructor for the {@code Media} class. Builds a new {@code Media} item and gives it a name (its title).
     * This method also keeps track of the date in which the media item was added and saves it in a class field.
     *
     * @param mediaName the name to set.
     * @param licenses The number of licenses available for this media.
     * @param extensionRestriction The time range in which the user may extend the loan period for this media.
     * @param loanLimit The maximum number of loans a user can request for this category of media.
     * @param mediaLoanValidityPeriod The number of days after which the loan of this media item expires.
     */
    public Media(String mediaName, int licenses, int extensionRestriction, int loanLimit, int mediaLoanValidityPeriod) {
        this(DEFAULT_MEDIA_IDENTIFIER, mediaName, DEFAULT_MEDIA_PATH, DEFAULT_DATE_ADDED,
                licenses, extensionRestriction, loanLimit, mediaLoanValidityPeriod);
    }

    /**
     * Constructor for the {@code Media} class. Builds a new {@code Media} item with its own unique item ID.
     *
     * @param identifier The item ID to set.
     */
    public Media(int identifier) {
        this(identifier, DEFAULT_MEDIA_NAME, DEFAULT_MEDIA_PATH, DEFAULT_DATE_ADDED,
                DEFAULT_MEDIA_LICENSES, DEFAULT_EXTENSION_RESTRICTION, DEFAULT_MEDIA_LOAN_LIMIT, DEFAULT_MEDIA_LOAN_VALIDITY_PERIOD_IN_DAYS);
    }


    /**
     * Getter for the identifier parameter.
     *
     * @return the unique item ID in form of an {@code int}.
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Setter for the identifier parameter.
     *
     * @param identifier the item ID to set.
     */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    /**
     * Setter for the licenses (how many copies of this media item are available to borrowers).
     *
     * @param licenses The number of licenses to be set.
     */
    public void setLicenses(int licenses) {
        this.licenses = licenses;
    }

    /**
     * Getter for the licenses (how many copies of this media item are available to borrowers).
     *
     * @return The number of licenses to get.
     */
    int getLicenses() {
        return licenses;
    }

    /**
     * Sets the item path (symbolic folder path) in form of a {@code String}.
     *
     * @param path the path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter for the item path (symbolic folder path).
     *
     * @return a {@code String} representing a folder path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter for the "bare details" of this media item. These "bare details" are nothing but a simple concatenation
     * of the item's main parameters in form of a {@code String}. This string comes in handy whenever a filtering
     * or searching method is called, as it merges all information in one single string, allowing the program to look
     * for a particular sub-string in more than one parameter at a time.
     *
     * @return the bare details describing this media item.
     */
    public String getBareItemDetails() {
        return bareItemDetails;
    }

    /**
     * Setter for the "bare details" of this media item. These "bare details" are nothing but a simple concatenation
     * of the item's main parameters in form of a {@code String}. This string comes in handy whenever a filtering
     * or searching method is called, as it merges all information in one single string, allowing the program to look
     * for a particular sub-string in more than one parameter at a time.
     *
     * @param bareItemDetails The bare details describing this media item.
     */
    void setBareItemDetails(String bareItemDetails) {
        this.bareItemDetails = bareItemDetails;
    }

    /**
     * A customer can extend the term of their loan, but this operation may only be carried out in a very specific time
     * range that goes from {@code x} days before the expiry date to the expiry date itself.
     * <p>
     * {@code x} is the value of the extension restriction (in days). Every media category has its own extension
     * restriction.
     *
     * @return The extension restriction.
     */
    public int getExtensionRestriction() {
        return extensionRestriction;
    }

    /**
     * Getter for the maximum number of media items that can be borrowed.
     *
     * @return The loan limit.
     */
    public int getLoanLimit() {
        return loanLimit;
    }

    /**
     * Checks whether this media item is available or not. To be available, a media item has to have more than 0
     * licenses.
     *
     * @return A boolean value, {@code true} if this media item is available, {@code false} otherwise.
     */
    public boolean isAvailable() {
        return licenses > 0 && available;
    }

    /**
     * Decrements the counter for the licences. It's the logical equivalent of lending a media item.
     */
    public void lend(){
        if(isAvailable())
            licenses--;
    }

    /**
     * Increments the counter for the borrowed media items. It's the logical equivalent of returning a media item.
     */
    public void giveBack() {
        if(licenses < 3)
            licenses++;
    }

    /**
     * Custom {@code toString} method for the {@code Media} class.
     *
     * @return a {@code String} with the basic details that describe this particular media item.
     */
    public String toString() {
        if(available)
            return String.format("Media name: %s\t|\tItem ID: %d\t|\tDate added: %s\t|\tNumber of licences: %d%n", mediaName, identifier, dateAdded.toZonedDateTime(), licenses);
        else
            return "";
    }

    /**
     * Setter for the type of this media.
     * @param type The type: BOOK or FILM.
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the media type.
     * @return The media type: BOOK or FILM.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets a media item as "unavailable" (all copies are currently being borrowed).
     */
    public void setUnavailable() {
        this.available = false;
    }

    /**
     * Getter for the expiry time (in days) for this media item.
     * @return The expiry time in days.
     */
    public int getMediaLoanValidityPeriod() {
        return mediaLoanValidityPeriod;
    }
}
