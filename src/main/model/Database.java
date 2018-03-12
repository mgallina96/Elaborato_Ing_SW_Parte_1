package main.model;

import main.model.user.User;

import java.util.HashMap;

public class Database {
    private static final User ADMIN = new User("admin", "admin");

    private static Database database;
    private HashMap<String, User> userList = new HashMap<>();

    private Database() {
        this.addUser(ADMIN);
    }

    public static Database getInstance() {
        if(database == null)
            database = new Database();

        return database;
    }

    public void addUser(User toAdd) {
        userList.put(toAdd.getUsername(), toAdd);
    }

    public void removeUser(User toRemove) {
        userList.remove(toRemove.getUsername());
    }

    public HashMap<String, User> getUserList() {
        return userList;
    }

    public boolean isPresent(User toFind) {
        return userList.containsKey(toFind.getUsername());
    }

    public User fetchUser(User toFind) {
        return userList.get(toFind.getUsername());
    }
}
