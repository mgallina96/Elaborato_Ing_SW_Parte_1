package main.controller;
import main.model.database.UserDatabase;
import main.model.user.Customer;
import main.model.user.User;
import main.utility.InputParserUtility;
import main.utility.exceptions.IllegalDateFormatException;
import main.utility.exceptions.UserNotFoundException;
import main.utility.exceptions.WrongPasswordException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static main.model.database.DatabaseIO.*;
import static main.utility.GlobalParameters.LEGAL_AGE_IN_YEARS;
import static main.utility.GlobalParameters.RENEWAL_BOUNDARY_IN_DAYS;
import static main.utility.GlobalParameters.USER_DATABASE_FILE_PATH;

public class SystemUserController implements UserController {

    private static SystemUserController instance;
    private UserDatabase userDatabase;

    private SystemUserController() {
        userDatabase = UserDatabase.getInstance();
    }

    //returns the instance of this controller.
    public static SystemUserController getInstance() {
        if(instance == null)
            instance = new SystemUserController();

        return instance;
    }

    @Override
    public boolean checkUserLogin(String username, String password) throws UserNotFoundException, WrongPasswordException {
        User toCheck = userDatabase.fetchUser(new User(username));

        if(toCheck == null)
            throw new UserNotFoundException();

        if(toCheck.getPassword().equals(password)){
            userDatabase.setCurrentUser(toCheck);
            return true;
        }
        else
            throw new WrongPasswordException();

    }

    @Override
    public boolean isOfAge(String date) throws IllegalDateFormatException {
        return InputParserUtility.isOfAge(date, LEGAL_AGE_IN_YEARS);
    }

    @Override
    public boolean userIsPresent(String username) {
        return userDatabase.isPresent(new User(username));
    }

    @Override
    public boolean addUserToDatabase(String firstName, String lastName, String username, String password, GregorianCalendar birthday) {
        Customer c = new Customer(firstName, lastName, username, password, birthday);

        if(!userDatabase.isPresent(c)) {
            userDatabase.addUser(c);
            saveDatabase(USER_DATABASE_FILE_PATH, userDatabase);
            return true;
        }

        return false;
    }

    @Override
    public int getUserStatus(String username) {
        if(username == null)
            return -1;

        switch(userDatabase.fetchUser(new User(username)).getUserStatus()) {
            case CUSTOMER:
                return 0;
            case OPERATOR:
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int daysLeftToRenew(String username) throws UserNotFoundException {
        try {
            User user = userDatabase.fetchUser(new User(username));

            int days = (int)Math.abs(ChronoUnit.DAYS.between(
                    new GregorianCalendar().toInstant(),
                    ((Customer)user).getExpiryDateGregorian().toInstant()));

            if(days <= RENEWAL_BOUNDARY_IN_DAYS)
                return days;
        }
        catch(ClassCastException ccEx) {
            return 0;
        }
        catch(NullPointerException npEx) {
            throw new UserNotFoundException();
        }

        return 0;
    }

    @Override
    public boolean renewSubscription() {
        try {
            Customer customer = (Customer)userDatabase.getCurrentUser();

            GregorianCalendar correctedExpiryDate = (GregorianCalendar) (customer.getExpiryDateGregorian().clone());
            correctedExpiryDate.add(Calendar.DATE, -RENEWAL_BOUNDARY_IN_DAYS);

            if(new GregorianCalendar().after(correctedExpiryDate)) {
                customer.renewSubscription();
                return true;
            }
        }
        catch(ClassCastException ccEx) {
            throw new IllegalArgumentException();
        }

        return false;
    }

    @Override
    public String[] dateDetails() {
        try {
            User u = userDatabase.getCurrentUser();
            String[] dates;
            int renewalDates = ((Customer)u).getRenewalDate().size();

            if(renewalDates == 0)
                dates = new String[]{((Customer)u).getSubscriptionDate(), "/", ((Customer)u).getExpiryDate()};
            else
                dates = new String[]{((Customer)u).getSubscriptionDate(), ((Customer)u).getLastRenewalDate(), ((Customer)u).getExpiryDate()};

            return dates;
        }
        catch(ClassCastException ccEx) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void logout() {
        userDatabase.removeCurrentUser();
    }

    @Override
    public String allUsersToString() {
        return userDatabase.getUserListString();
    }

    @Override
    public String getCurrentUserName() {
        return userDatabase.getCurrentUser().getUsername();
    }
}
