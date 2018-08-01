package generators.randomwords;
import java.util.Random;

/**
 * Random word generator. In order to work properly, this class requires a pool of words (in form of a {@code String}
 * array), from which the words will be randomly picked.
 *
 * @author Alessandro Polcini
 */
public class RandomWords {

    private Random random;
    private int poolSize;
    private String[] wordPool;

    /**
     * Constructor for the RandomWords class. Initializes the pool from which the words will be chosen at random.
     *
     * @param wordPool the word pool in form of a {@code String} array.
     */
    public RandomWords(String[] wordPool) {
        random = new Random();
        this.wordPool = wordPool;
        this.poolSize = this.wordPool.length;
    }

    /**
     * Constructor for the RandomWords class. Initializes the pool from which the words will be chosen at random.
     *
     * @param wordPool the word pool in form of a {@code String} array.
     * @param seed the seed for the {@code Random} constructor.
     */
    public RandomWords(String[] wordPool, long seed) {
        random = new Random(seed);
        this.wordPool = wordPool;
        this.poolSize = this.wordPool.length;
    }

    /**
     * Returns the next random word in the pool.
     *
     * @return a random word.
     */
    public String nextWord() {
        return wordPool[random.nextInt(poolSize)];
    }

    public int getPoolSize() {
        return poolSize;
    }

    public Random getRandom() {
        return random;
    }

    public String[] getWordPool() {
        return wordPool;
    }
}
