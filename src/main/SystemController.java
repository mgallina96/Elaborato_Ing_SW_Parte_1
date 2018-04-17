package main;

import java.util.GregorianCalendar;

/**
 * Interface for the system controller.
 *
 * @author Manuel Gallina
 */
public interface SystemController {

    void renewSubscription();
    boolean canRenew();
    Object dateDetails();
    void logout();
    boolean legalAge(GregorianCalendar gregorianCalendar);
    boolean checkUserLogin(String username, String password);
    void addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar gregorianCalendar);
    void addMediaToDatabase(String title, String author, String genre, int publicationYear, String publisherName);
    void removeMediaFromDatabase(int id);
    void saveDatabase();
    String allUsersToString();
    String allMediaToString();
    String allFilteredMediaList(String filter);
}
