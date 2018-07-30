package main.controller;

import main.model.user.User;

import java.util.ArrayList;

/**
 * Interface for the Loan controller, which provides methods specific to loan management.
 *
 * @author Manuel Gallina, Giosuè Filippini, Alessandro Polcini
 */
public interface LoanController extends Controller {

    /** Checks whether the currently logged-in user is allowed to borrow another media item. */
    boolean canBorrow(int mediaID);

    boolean canBeExtended(int mediaID);
    /**
     * Adds a new {@code Loan} object to the database whenever the current user borrows a media item.
     *
     * @param mediaID The media to be borrowed.
     * @return A boolean value: {@code true} if the loan has been added successfully, {@code false} otherwise.
     */
    boolean addLoanToDatabase(int mediaID);

    /**
     * Returns a {@code String} that contains all the loans that have been granted.
     *
     * @return the list of all loans as a {@code String}.
     */
    String allLoansToString();

    /**
     *
     * @return
     */
    String currentUserLoansToString();

    /**
     *
     * @param mediaID
     */
    void extendLoan(int mediaID);

    /**
     *
     * @param from
     * @param to
     * @return
     */
    String getLoanNumberByYear(int from, int to);

    /**
     *
     * @param from
     * @param to
     * @return
     */
    String getUserLoanNumberByYear(int from, int to);

    String getExtensionNumberByYear(int from, int to);

    String getMostLentMediaByYear(int from, int to);
}
