package main.controller;
import main.utility.exceptions.ExtensionDateOutOfBoundsException;
import main.utility.exceptions.ExtensionLimitReachedException;
import main.utility.exceptions.LoanNotFoundException;

/**
 * Interface for the Loan controller, which provides methods that are specific to loan management.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public interface LoanController extends Controller {

    /**
     * Checks whether the currently logged-in user is allowed to borrow another media item.
     *
     * @param mediaID The media item to be checked.
     * @return {@code true} if that media item can be borrowed, {@code false} otherwise.
     */
    boolean canBorrow(int mediaID);

    /**
     * Adds a new {@code Loan} object to the database whenever the current user borrows a media item.
     *
     * @param mediaID The media to be borrowed.
     * @return {@code true} if the loan has been added successfully, {@code false} otherwise.
     */
    boolean addLoanToDatabase(int mediaID);

    /**
     * Extends the loan due date. The loan must be extended in a very specific time window and only one extension is
     * allowed. If these requirements are not met, exceptions will be thrown.
     *
     * @param mediaID The ID of the media whose loan is to be extended.
     * @throws LoanNotFoundException If that loan can't be found.
     * @throws ExtensionDateOutOfBoundsException If it's too early to extend that loan.
     * @throws ExtensionLimitReachedException If the loan has already been extended once.
     */
    void extendLoan(int mediaID) throws LoanNotFoundException, ExtensionDateOutOfBoundsException, ExtensionLimitReachedException;

    /**
     * Creates a {@code String} that contains all the loans that have been granted.
     * @return The list of all loans as a {@code String}.
     */
    String allLoansToString();

    /**
     * Creates a list of all the media items the current user has borrowed.
     * @return The list of the user's loans as a {@code String}.
     */
    String currentUserLoansToString();

    /**
     * Calculates the general number of loans that have been granted in a year, for all years in range {@code from - to}.
     *
     * @param from The starting year.
     * @param to The ending year.
     * @return A {@code String} containing the number of loans by year.
     */
    String getLoanNumberByYear(int from, int to);

    /**
     * Calculates the specific number of loans that have been granted to each user, for all years in range
     * {@code from - to}.
     *
     * @param from The starting year.
     * @param to The ending year.
     * @return A {@code String} containing the number of loans by year and user.
     */
    String getUserLoanNumberByYear(int from, int to);

    /**
     * Calculates the specific number of loan extensions that occurred in a year, for all years in range
     * {@code from - to}.
     *
     * @param from The starting year.
     * @param to The ending year.
     * @return A {@code String} containing the number of loan extensions by year.
     */
    String getExtensionNumberByYear(int from, int to);

    /**
     * Finds the most borrowed media item in a year, for all years in range {@code from - to}.
     *
     * @param from The starting year.
     * @param to The ending year.
     * @return A {@code String} containing the most lent media item by year.
     */
    String getMostLentMediaByYear(int from, int to);
}
