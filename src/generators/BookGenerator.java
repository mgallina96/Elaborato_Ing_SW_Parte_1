package generators;
import main.model.media.Book;
import main.model.media.Media;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alessandro on 12/04/18.
 */
class BookGenerator {

    private BookGenerator() {}

    private static ArrayList<String> firstNames;
    private static ArrayList<String> lastNames;
    private static ArrayList<String> dictionary;
    private static final String[] genres = new String[]{"Horror", "Romantico", "Erotica", "Giallo", "Thriller", "Fantasy", "Fantascienza", "Commedia",
            "Comico", "Noir", "Romanzo storico", "Biografia", "Tecnica", "Arte", "Fotografia", "Musica", "Per bambini", "Raccolta di fiabe",
            "Raccolta di poesie", "Raccolta di favole", "Romanzo epico", "Religioso"};
    private static final String[] connettori = new String[]{"di", "a", "da", "in", "con", "su", "per", "tra", "fra", "e",
            "poiché", "perchè", "nel", "nella", "in mezzo a", "nel mezzo di", "tra una", "tra un", "in una", "in un", "nei",
            "quando", "sui", "sul", "sulla", "sulle", "sullo", "nello", "attraverso", "attraverso la", "attraverso il", "allorché",
            "altrimenti", "o", "oppure", "ciononostante", "sebbene", "ancorché", "nonostante ciò", "ancora",
            "sopra", "sopra ai", "sopra alla", "sopra alle", "sotto", "sotto alla", "sotto ai", "sotto alle", "sotto allo", "sull'",
            "nell'", "dietro la", "l'", "dietro le", "fuori da", "dentro in", "dentro nella", "dentro nell'", "dentro nello", "per la", "dietro lo",
            "per il", "per lo", "fuori dal", "fuori dalle", "fuori dallo", "dietro le", "dietro i",
            "tutt'a un tratto", "immediatamente", "subito", "allora"};

    public static ArrayList<Media> generateBooks(int howMany, ArrayList<String> fn, ArrayList<String> ln, ArrayList<String> d) {
        Random rand = new Random();
        ArrayList<Media> media = new ArrayList<>();
        firstNames = fn;
        lastNames = ln;
        dictionary = d;
        int lenD = dictionary.size();
        int lenFN = firstNames.size();
        int lenLN = lastNames.size();
        int genreLen = genres.length;
        int connettoriLen = connettori.length;

        for(int i = 0; i < howMany; i++) {
            int year;
            if(rand.nextDouble() > 0.85)
                year = 1200 + rand.nextInt(600);
            else
                year = 1800 + rand.nextInt(219);

            media.add(new Book(titleGenerator(lenFN, lenLN, lenD, connettoriLen, rand),
                    generateName(lenFN, lenLN, rand),
                    genres[rand.nextInt(genreLen)],
                    year,
                    generatePublisher(lenFN, lenD, rand)));
        }

        return media;
    }

    private static String generateName(int lenFN, int lenLN, Random rand) {
        return firstNames.get(rand.nextInt(lenFN)) + " " + lastNames.get(rand.nextInt(lenLN));
    }

    private static String generatePublisher(int lenFN, int lenD, Random rand) {
        String s = (rand.nextDouble() > 0.6 ? firstNames.get(rand.nextInt(lenFN)) + " " : "") + dictionary.get(rand.nextInt(lenD)) + " Editore";

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static String titleGenerator(int lenFN, int lenLN, int lenD, int connettoriLen, Random rand) {
        String s = "";

        if(rand.nextDouble() > 0.9)
            s = dictionary.get(rand.nextInt(lenD));
        else
            switch(rand.nextInt(5)) {
                case 0:
                    s = connettori[rand.nextInt(connettoriLen)] + " " + dictionary.get(rand.nextInt(lenD)) +
                            " " + dictionary.get(rand.nextInt(lenD)) + " " + connettori[rand.nextInt(connettoriLen)] + " " +
                            dictionary.get(rand.nextInt(lenD)) + " " + dictionary.get(rand.nextInt(lenD));
                    break;
                case 1:
                    if(rand.nextDouble() > 0.8)
                        s = connettori[rand.nextInt(connettoriLen)] + " " + dictionary.get(rand.nextInt(lenD));
                    else
                        s = dictionary.get(rand.nextInt(lenD)) + " " + dictionary.get(rand.nextInt(lenD));
                    break;
                case 2:
                    s = connettori[rand.nextInt(connettoriLen)] + " " + dictionary.get(rand.nextInt(lenD)) +
                            " " + dictionary.get(rand.nextInt(lenD)) + " " + connettori[rand.nextInt(connettoriLen)] + " " +
                            firstNames.get(rand.nextInt(lenFN)) + " " + (rand.nextBoolean() ? "" : lastNames.get(rand.nextInt(lenLN)));
                    break;
                case 3:
                    if(rand.nextDouble() > 0.8)
                        s = firstNames.get(rand.nextInt(lenFN)) + " " + (rand.nextBoolean() ? "" : lastNames.get(rand.nextInt(lenLN))) + " " +
                                connettori[rand.nextInt(connettoriLen)] + " " + connettori[rand.nextInt(connettoriLen)] + " " +
                                dictionary.get(rand.nextInt(lenD)) + " " + connettori[rand.nextInt(connettoriLen)] + " " + dictionary.get(rand.nextInt(lenD));
                    else
                        s = firstNames.get(rand.nextInt(lenFN)) + " " + (rand.nextBoolean() ? "" : lastNames.get(rand.nextInt(lenLN)) + " ") + "e " +
                                (rand.nextBoolean() ? "il " : (rand.nextBoolean() ? "la " : "le ")) +
                                dictionary.get(rand.nextInt(lenD)) + " di " + dictionary.get(rand.nextInt(lenD));
                    break;
                case 4:
                    s = connettori[rand.nextInt(connettoriLen)] + " " + dictionary.get(rand.nextInt(lenD)) + " " +
                            connettori[rand.nextInt(connettoriLen)] + " " + dictionary.get(rand.nextInt(lenD));
                    break;
                default:
                    break;
            }

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
