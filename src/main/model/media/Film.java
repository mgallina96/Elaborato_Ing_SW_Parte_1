package main.model.media;

public class Film extends Media {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5271323384398635051L;

    private static final int EXTENSION_RESTRICTION_IN_DAYS = 5;
    private static final int DEFAULT_FILM_LICENSES = 5;
    private static final int MAX_NUMBER_OF_LENT_FILMS = 5;
    private String title;
    private String director;
    private String genre;
    private int releaseYear;
    private String producer;

    //Additional details.
    private String description;
    private String cast;
    private String language;
    private double runningTime;
    private boolean belongsToSeries;
    private int filmNumber;

    /**
     * Constructor for the Film class.
     * <p>
     * Sets the main fields to briefly describe a film.
     *
     * @param title The title of the book.
     * @param director The director.
     * @param genre The genre of the book.
     * @param releaseYear The year the book was published.
     * @param producer The name of the producer (or producer house).
     * @param licenses The number of licences for this film.
     */
    public Film(String title, String director, String genre, int releaseYear, String producer, int licenses) {
        super(title, licenses, EXTENSION_RESTRICTION_IN_DAYS, MAX_NUMBER_OF_LENT_FILMS);
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.producer = producer;

        String bareDetails = title + ", " + director + ", " + genre + ", " + releaseYear + ", "  + producer;

        super.setBareItemDetails(bareDetails);
    }

    /**
     * Constructor for the Film class.
     * <p>
     * Sets the main fields to briefly describe a film.
     * <p>
     * Sets the number of licenses to a default value of 3.
     *
     * @param title The title of the book.
     * @param director The director.
     * @param genre The genre of the book.
     * @param releaseYear The year the book was published.
     * @param producer The name of the publisher (or publishing house).
     */
    public Film(String title, String director, String genre, int releaseYear, String producer) {
        super(title, DEFAULT_FILM_LICENSES, EXTENSION_RESTRICTION_IN_DAYS, MAX_NUMBER_OF_LENT_FILMS);
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.producer = producer;

        String bareDetails = title + ", " + director + ", " + genre + ", " + releaseYear + ", "  + producer;

        super.setBareItemDetails(bareDetails);
    }

    /**
     * Setter for the optional details to describe a book in more detail.
     *
     * @param description A brief description of the book.
     * @param subtitle The cast.
     * @param language The language the book is written in.
     * @param runningTime The number of pages.
     * @param belongsToSeries Whether the book belongs to a series or not.
     * @param filmNumber The volume number (automatically set to 0 if the book doesn't belong to a series).
     */
    public void setDetails(String description, String subtitle, String language, double runningTime, boolean belongsToSeries, int filmNumber) {
        this.description = description;
        this.cast = subtitle;
        this.language = language;
        this.runningTime = runningTime;
        this.belongsToSeries = belongsToSeries;
        this.filmNumber = this.belongsToSeries ? filmNumber : 0;
    }

    @Override
    public String getBareItemDetails() {
        return super.getBareItemDetails();
    }

    /**
     * Overridden toString() method.
     *
     * @return A string containing a brief description of the book.
     */
    public String toString() {
        return String.format("Item ID: %d\t|\tTitle: %s\t|\tDirector: %s\t|\tRelease year: %d\t|\tGenre: %s\t|\tPublisher: %s\t|\tNumber of licenses: %d\n",
                getIdentifier(), title, director, releaseYear, genre, producer, getLicenses());
    }

    /**
     * Much more detailed information about the book.
     *
     * @return A string containing an accurate description of the book.
     */
    public String allDetailsToString() {
        return String.format("%s\t|\tCast: %s\t|\tLanguage: %s\t|\tRunning time: %f%s\nDescription: %s\n",
                toString(), cast, language, runningTime, (belongsToSeries ? ("|\tVolume number: " + filmNumber) : ""), description);
    }

    /**
     * Getter for the book genre.
     *
     * @return The genre.
     */
    public String getGenre() {
        return genre;
    }

    public static int getMaxNumberOfLentFilms() {
        return MAX_NUMBER_OF_LENT_FILMS;
    }
}
