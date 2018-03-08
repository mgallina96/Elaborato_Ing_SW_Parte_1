package main;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alessandro Polcini
 * Created on 08/03/18
 * Utility class that contains useful methods for parsing strings and checking whether a given input is valid or not.
 */
public class InputParserUtility {

    private static Logger logger = Logger.getLogger("InputParserUtility");
    private static final String ERROR_MSG_INVALID_INPUT = "Invalid input.";

    /**
     * Constructor has been made private to prevent instantiation.
     */
    private InputParserUtility() {}

    /**
     * Checks whether the input is an integer or not.
     *
     * @param input the input to be parsed and checked.
     * @return a boolean value: {@code true} if the input is an integer,
     *                          {@code false} otherwise.
     */
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
        }
        catch(NumberFormatException NFEx) {
            logger.log(Level.SEVERE, ERROR_MSG_INVALID_INPUT);
            return false;
        }

        return true;
    }

    /**
     * Checks whether the input is an integer. If so, the integer must have a value between {@code lowerBound}
     * (inclusive) and {@code upperBound} (exclusive), otherwise it's considered invalid.
     * @param input the input to be parsed and checked.
     * @param lowerBound the lower bound for the input, inclusive.
     * @param upperBound the upper bound for the input, exclusive.
     * @return a boolean value: {@code true} if the input is both an integer and a value between {@code lowerBound}
     *                                       and {@code upperBound},
     *                          {@code false} otherwise.
     */
    public static boolean isValidInteger(String input, int lowerBound, int upperBound) {
        if(isValidInteger(input)) {
            int intInput = Integer.parseInt(input);
            return (intInput >= lowerBound && intInput < upperBound);
        }

        return false;
    }

    /**
     * Checks whether the input is a valid first name/last name/city name: a {@code String} composed of letters,
     * spaces, apostrophes, and dashes only.
     * <p>
     * Examples: "De Loria", "New York City", "O'Sullivan", "Stratford-upon-Avon", etc.
     * @param input the {@code String} to be checked.
     * @return a boolean value: {@code true} if the input only contains letters, spaces, apostrophes and dashes.
     *                          {@code false} otherwise.
     */
    public static boolean isValidName(String input) {
        return input.matches("[a-zA-Z -']+");
    }

    /**
     * Checks whether the input is a {@code double} or not.
     *
     * @param input the input to be parsed and checked.
     * @return a boolean value: {@code true} if the input is a {@code double},
     *                          {@code false} otherwise.
     */
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
        }
        catch(Exception e) {
            logger.log(Level.SEVERE, ERROR_MSG_INVALID_INPUT);
            return false;
        }

        return true;
    }

    /**
     * Checks whether the input is a {@code double}. If so, the floating-point number must have a value between
     * {@code lowerBound} (inclusive) and {@code upperBound} (exclusive), otherwise it's considered invalid.
     * @param input the input to be parsed and checked.
     * @param lowerBound the lower bound for the input, inclusive.
     * @param upperBound the upper bound for the input, exclusive.
     * @return a boolean value: {@code true} if the input is both a {@code double} and a value between
     *                                       {@code lowerBound} and {@code upperBound},
     *                          {@code false} otherwise.
     */
    public static boolean isValidDouble(String input, double lowerBound, double upperBound) {
        if(isValidDouble(input)) {
            double doubleInput = Double.parseDouble(input);
            return (doubleInput >= lowerBound && doubleInput < upperBound);
        }

        return false;
    }

    /**
     * Checks whether the input is a {@code long} or not.
     *
     * @param input the input to be parsed and checked.
     * @return a boolean value: {@code true} if the input is a {@code long},
     *                          {@code false} otherwise.
     */
    public static boolean isValidLong(String input) {
        try {
            Long.parseLong(input);
        }
        catch(NumberFormatException NFEx) {
            logger.log(Level.SEVERE, ERROR_MSG_INVALID_INPUT);
            return false;
        }

        return true;
    }

    /**
     * Checks whether the input is a {@code long}. If so, the long integer must have a value between {@code lowerBound}
     * (inclusive) and {@code upperBound} (exclusive), otherwise it's considered invalid.
     * @param input the input to be parsed and checked.
     * @param lowerBound the lower bound for the input, inclusive.
     * @param upperBound the upper bound for the input, exclusive.
     * @return a boolean value: {@code true} if the input is both a {@code long} and a value between {@code lowerBound}
     *                                       and {@code upperBound},
     *                          {@code false} otherwise.
     */
    public static boolean isValidLong(String input, long lowerBound, long upperBound) {
        if(isValidLong(input)) {
            long longInput = Long.parseLong(input);
            return (longInput >= lowerBound && longInput < upperBound);
        }

        return false;
    }

}
