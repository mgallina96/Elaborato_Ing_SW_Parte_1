package main.model.media;
import java.io.Serializable;

/**
 * The {@code Book} class, subclass of the {@link Media} class.
 * <p>
 * This class represents a book through parameters like its title, its publication year, the name of its author, the
 * name of its publisher, etc.
 *
 * @author Alessandro Polcini
 */
public class Book extends Media implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -1281383374398165051L;

    private static final String TYPE = "BOOK";
    private static final int EXTENSION_RESTRICTION_IN_DAYS = 3;
    private static final int DEFAULT_BOOK_LICENSES = 3;
    private static final int BOOK_LOAN_LIMIT = 3;
    private static final int BOOK_LOAN_VALIDITY_PERIOD_IN_DAYS = 30;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private String publisherName;

    //Additional details.
    private String description;
    private String subtitle;
    private String language;
    private int pageCount;
    private boolean belongsToSeries;
    private int volumeNumber;

    /**
     * Constructor for the Book class.
     * <p>
     * Sets the main fields to briefly describe a book.
     *
     * @param title The title of the book.
     * @param author The author.
     * @param genre The genre of the book.
     * @param publicationYear The year the book was published.
     * @param publisherName The name of the publisher (or publishing house).
     * @param licenses The number of licences for this book.
     */
    public Book(String title, String author, String genre, int publicationYear, String publisherName, int licenses) {
        super(title, licenses, EXTENSION_RESTRICTION_IN_DAYS, BOOK_LOAN_LIMIT, BOOK_LOAN_VALIDITY_PERIOD_IN_DAYS);
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.publisherName = publisherName;

        String bareDetails = title + ", " + author + ", " + genre + ", " + publicationYear + ", "  + publisherName;

        super.setType(TYPE);
        super.setBareItemDetails(bareDetails);
    }

    /**
     * Constructor for the Book class.
     * <p>
     * Sets the main fields to briefly describe a book.
     * <p>
     * Sets the number of licenses to a default value of 3.
     *
     * @param title The title of the book.
     * @param author The author.
     * @param genre The genre of the book.
     * @param publicationYear The year the book was published.
     * @param publisherName The name of the publisher (or publishing house).
     */
    public Book(String title, String author, String genre, int publicationYear, String publisherName) {
        this(title, author, genre, publicationYear, publisherName, DEFAULT_BOOK_LICENSES);
    }

    /**
     * Setter for the optional details to describe a book in more detail.
     *
     * @param description A brief description of the book.
     * @param subtitle The subtitle.
     * @param language The language the book is written in.
     * @param pageCount The number of pages.
     * @param belongsToSeries Whether the book belongs to a series or not.
     * @param volumeNumber The volume number (automatically set to 0 if the book doesn't belong to a series).
     */
    public void setDetails(String description, String subtitle, String language, int pageCount, boolean belongsToSeries, int volumeNumber) {
        this.description = description;
        this.subtitle = subtitle;
        this.language = language;
        this.pageCount = pageCount;
        this.belongsToSeries = belongsToSeries;
        this.volumeNumber = this.belongsToSeries ? volumeNumber : 0;
    }

    /**
     * Overridden toString() method.
     *
     * @return A string containing a brief description of the book.
     */
    @Override
    public String toString() {
        if(isAvailable())
            return String.format("Media type: %s, Item ID: %d\t|\tTitle: %s\t|\tAuthor: %s\t|\tPublication year: %d\t|\tGenre: %s\t|\tPublisher name: %s\t|\tNumber of licenses: %d%n",
                    TYPE, getIdentifier(), title, author, publicationYear, genre, publisherName, getLicenses());
        else
            return "";
    }

    /**
     * Much more detailed information about the book.
     *
     * @return A string containing an accurate description of the book.
     */
    public String allDetailsToString() {
        return String.format("%s\t|\tSubtitle: %s\t|\tLanguage: %s\t|\tPage count: %d%s%nDescription: %s%n",
                toString(), subtitle, language, pageCount, (belongsToSeries ? ("|\tVolume number: " + volumeNumber) : ""), description);
    }

    /**
     * Getter for the book genre.
     *
     * @return The genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Getter for the maximum number of allowed loans for books.
     * @return The maximum number of allowed loans for books.
     */
    public static int getBookLoanLimit() {
        return BOOK_LOAN_LIMIT;
    }
}
