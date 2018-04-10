package main.model.database;
import main.model.media.Media;
import main.model.user.User;

/**
 * Interface whose primary task is to control and manage interaction between different kinds of databases
 * (user database and media database).
 *
 * @author Manuel Gallina
 */
public interface Database {

    void add(User toAdd);
    void add(Media toAdd);
    void remove(User toRemove);
    void remove(Media toRemove);
    User fetch(User toFetch);
    Media fetch(Media toFetch);
    String getUserListString();
    String getMediaListString();
    void setCurrentUser(User currentUser);
    void removeCurrentUser();
    User getCurrentUser();
    boolean isPresent(User toFind);
    boolean isPresent(Media toFind);
    void saveDatabase();

}
