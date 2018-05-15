package generators.mediagenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;
import main.model.media.Film;
import main.model.media.Media;
import java.util.ArrayList;
import java.util.Random;
import static generators.Generator.COMMON_MEDIA_PATH;

public class FilmGenerator {

    private FilmGenerator() {}

    public static ArrayList<Media> generateFilm(int howMany) {
        Random rand = new Random();
        ArrayList<Media> media = new ArrayList<>();
        TitleGenerator titleGenerator = TitleGenerator.getInstance();

        RandomWords genres = new RandomWords(PoolLoader.fromTXTFile(COMMON_MEDIA_PATH + "generi_libro.txt"));

        for(int i = 0; i < howMany; i++) {
            int year;
            if(rand.nextDouble() > 0.85)
                year = 1800 + rand.nextInt(130);
            else
                year = 1930 + rand.nextInt(85);

            media.add(new Film(
                    titleGenerator.generateTitle(),
                    titleGenerator.firstnameLastname(),
                    genres.nextWord(),
                    year,
                    (rand.nextBoolean() ? titleGenerator.firstnameLastname() : titleGenerator.capitalize(titleGenerator.randomNoun())) + " " + randomEnding(rand)
            ));
        }

        return media;
    }

    private static String randomEnding(Random rand) {
        switch(rand.nextInt(10)) {
            case 0: return "Movies";
            case 1: return "Films";
            case 2: return "Ltd.";
            case 3: return "FilmHouse";
            case 4: return "Productions";
            case 5: return "Pictures";
            case 6: return "Studios";
            case 7: return "Entertainment";
            case 8: return "Inc.";
            case 9: return "& co";
            default: return "Brothers";
        }
    }
}
