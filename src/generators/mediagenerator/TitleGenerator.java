package generators.mediagenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import static generators.Generator.COMMON_MEDIA_PATH;

/**
 * Title generator class. Generates a random title which adapts to the inserted genre.
 *
 * @author Alessandro Polcini.
 */
class TitleGenerator {

    private String filePath;
    private String mediaType;
    private String genre;
    private RandomWords titles;

    TitleGenerator(String mediaType, String genre) {
        this.mediaType = mediaType;
        this.genre = genre.toLowerCase().replaceAll("\\s+", "");
        filePath = COMMON_MEDIA_PATH + mediaType + "\\titles\\" + this.genre + "_titles.txt";
        titles = new RandomWords(PoolLoader.fromTXTFile(filePath));
    }

    /**
     * Generates a random title.
     *
     * @return A random title in form of a {@code String}.
     */
    String getTitle() {
        return titles.nextWord();
    }
}
