package main.model.media;
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * The {@code Media} class, which contains fields and methods that can be applied to any generic piece of media, such
 * as books, movies, magazines, tv series, video games, etc.
 *
 * @author Alessandro Polcini
 */
public class Media implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681380379098150051L;

    private String bareItemDetails;
    private int identifier;
    private String mediaName;
    private String[] path;
    private GregorianCalendar dateAdded;

    /**
     * Constructor for the {@code Media} class. Builds a new {@code Media} item with its own unique item ID.
     *
     * @param identifier the item ID to set.
     */
    public Media(int identifier) {
        this.identifier = identifier;
    }

    /**
     * Constructor for the {@code Media} class. Builds a new {@code Media} item and gives it a name (its title).
     * This method also keeps track of the date in which the media item was added and saves it in a class field.
     *
     * @param mediaName the name to set.
     */
    public Media(String mediaName) {
        this.mediaName = mediaName;
        this.dateAdded = new GregorianCalendar();
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
     * Sets the item path (symbolic folder path) in form of a {@code String} array. Every element in the array
     * represents a folder, which will (symbolically) be as deeply nested as the position it holds in the array.
     * <p>
     * Example. The following path represented in form of an array ["Book", "Horror", "1950s", "A"] is an abstraction
     * that has the same meaning as: "the 'A' folder is a sub-folder of the '1950s' folder, which in turn is a
     * sub-folder of the 'Horror' folder, which is contained in the 'Book' folder".
     *
     * @param path the path to set.
     */
    public void setPath(String[] path) {
        this.path = path;
    }

    /**
     * Getter for the item path (symbolic folder path). Every element in the array
     * represents a folder, which will (symbolically) be as deeply nested as the position it holds in the array.
     * <p>
     * Example. The following path represented in form of an array ["Book", "Horror", "1950s", "A"] is an abstraction
     * that has the same meaning as: "the 'A' folder is a sub-folder of the '1950s' folder, which in turn is a
     * sub-folder of the 'Horror' folder, which is contained in the 'Book' folder".
     *
     * @return a {@code String} array representing a folder path.
     */
    public String[] getPath() {
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


    void setBareItemDetails(String title, String author, String genre, int year, String publisher) {
        this.bareItemDetails = title + ", " + author + ", " + genre + ", " + year + ", " + publisher;
    }

    /**
     * Custom {@code toString} method for the {@code Media} class.
     *
     * @return a {@code String} with the basic details that describe this particular media item.
     */
    public String toString() {
        return String.format("Media name: %s\t|\tItem ID: %d\t|\tDate added: %s",
                mediaName, identifier, dateAdded.toZonedDateTime());
    }
}
