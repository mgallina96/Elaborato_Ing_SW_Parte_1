package main.model.database;
import main.model.user.User;
import main.model.user.UserStatus;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Database concerned with saving, removing, finding, fetching and generally managing all kinds of users and details
 * about them.
 *
 * @author Manuel Gallina, Alessandro Polcini
 */
class UserDatabase implements Serializable {

    //Unique serial ID for this class. DO NOT CHANGE, otherwise the database can't be read properly.
    private static final long serialVersionUID = -5681387677098150051L;

    //A default admin user, added to the database whenever this class is instantiated.
    private static final User ADMIN = new User("admin", "admin");
    private static UserDatabase userDatabase;
    private User currentUser;
    private HashMap<String, User> userList;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    //Singleton Database constructor, private to prevent instantiation.
    private UserDatabase() {
        this.userList = new HashMap<>();

        //Adds the admin to the database, if the admin hasn't been added yet.
        if(!isPresent(ADMIN)) {
            ADMIN.setUserStatus(UserStatus.OPERATOR);
            this.addUser(ADMIN);
        }
    }

    static UserDatabase getInstance() {
        if(userDatabase == null)
            userDatabase = new UserDatabase();

        return userDatabase;
    }

    void addUser(User toAdd) {
        userList.put(toAdd.getUsername(), toAdd);
    }
/*
    void removeUser(User toRemove) {
        if(isPresent(toRemove))
            userList.remove(toRemove.getUsername());
    }*/

    boolean isPresent(User toFind) {
        return userList.containsKey(toFind.getUsername());
    }

    User fetchUser(User toFetch) {
        return isPresent(toFetch) ? userList.get(toFetch.getUsername()) : null;
    }

    void setCurrentUser(User currentUser) {
        this.currentUser = fetchUser(currentUser);
    }

    User getCurrentUser() {
        return currentUser;
    }

    void removeCurrentUser() {
        currentUser = null;
    }

    String getUserListString() {
        StringBuilder allUsers = new StringBuilder();

        for(User u : userList.values()) {
            allUsers.append("\t- ");
            allUsers.append(u.toString());
        }

        return allUsers.toString();
    }

    HashMap<String, User> getUserList() {
        return userList;
    }

    void setUserList(HashMap<String, User> userList) {
        this.userList = userList;
    }
}
