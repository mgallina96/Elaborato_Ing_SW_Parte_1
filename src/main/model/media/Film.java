package main.model.media;
import java.io.Serializable;

/**
 * The {@code Film} class, subclass of the {@link Media} class.
 * <p>
 * This class represents a film through parameters like its title, its release year, the name of its director, the name
 * of its publisher, etc.
 *
 * @author Alessandro Polcini
 */
public class Film extends Media implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5271323384398735051L;

    private static final String TYPE = "FILM";
    private static final int EXTENSION_RESTRICTION_IN_DAYS = 5;
    private static final int DEFAULT_FILM_LICENSES = 5;
    private static final int FILM_LOAN_LIMIT = 5;
    private static final int FILM_LOAN_VALIDITY_PERIOD_IN_DAYS = 20;
    private String title;
    private String director;
    private String genre;
    private int releaseYear;
    private String producer;

    //Additional details.
    private String description;
    private String cast;
    private String language;
    private int runningTime;
    private boolean belongsToSeries;
    private int filmNumber;

    /**
     * Constructor for the Film class.
     * <p>
     * Sets the main fields to briefly describe a film.
     *
     * @param title The title of the film.
     * @param director The director.
     * @param genre The genre of the film.
     * @param releaseYear The year the film was released.
     * @param producer The name of the producer (or producer house).
     * @param licenses The number of licences for this film.
     */
    public Film(String title, String director, String genre, int releaseYear, String producer, int licenses) {
        super(title, licenses, EXTENSION_RESTRICTION_IN_DAYS, FILM_LOAN_LIMIT, FILM_LOAN_VALIDITY_PERIOD_IN_DAYS);
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.producer = producer;

        String bareDetails = title + ", " + director + ", " + genre + ", " + releaseYear + ", "  + producer;

        super.setType(TYPE);
        super.setBareItemDetails(bareDetails);
    }

    /**
     * Constructor for the Film class.
     * <p>
     * Sets the main fields to briefly describe a film.
     * <p>
     * Sets the number of licenses to a default value of 3.
     *
     * @param title The title of the film.
     * @param director The director.
     * @param genre The genre of the film.
     * @param releaseYear The year the film was released.
     * @param producer The name of the publisher (or publishing house).
     */
    public Film(String title, String director, String genre, int releaseYear, String producer) {
        this(title, director, genre, releaseYear, producer, DEFAULT_FILM_LICENSES);
    }

    /**
     * Setter for the optional details to describe a film in more detail.
     *
     * @param description A brief description of the film.
     * @param cast The cast.
     * @param language The language of the film.
     * @param runningTime The running time.
     * @param belongsToSeries Whether the film belongs to a series or not.
     * @param filmNumber The volume number (automatically set to 0 if the film doesn't belong to a series).
     */
    public void setDetails(String description, String cast, String language, int runningTime, boolean belongsToSeries, int filmNumber) {
        this.description = description;
        this.cast = cast;
        this.language = language;
        this.runningTime = runningTime;
        this.belongsToSeries = belongsToSeries;
        this.filmNumber = this.belongsToSeries ? filmNumber : 0;
    }

    /**
     * Overridden toString() method.
     *
     * @return A string containing a brief description of the film.
     */
    @Override
    public String toString() {
        return String.format("Media type: %s, Item ID: %d\t|\tTitle: %s\t|\tDirector: %s\t|\tRelease year: %d\t|\tGenre: %s\t|\tPublisher: %s\t|\tNumber of licenses: %d%n",
                TYPE, getIdentifier(), title, director, releaseYear, genre, producer, getLicenses());
    }

    /**
     * Much more detailed information about the film.
     *
     * @return A string containing an accurate description of the film.
     */
    public String allDetailsToString() {
        return String.format("%s\t|\tCast: %s\t|\tLanguage: %s\t|\tRunning time: %d%s%nDescription: %s%n",
                toString(), cast, language, runningTime, (belongsToSeries ? ("|\tVolume number: " + filmNumber) : ""), description);
    }

    /**
     * Getter for the film genre.
     *
     * @return The genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Getter for the maximum number of allowed loans for films.
     * @return The maximum number of allowed loans for films.
     */
    public static int getFilmLoanLimit() {
        return FILM_LOAN_LIMIT;
    }
}
