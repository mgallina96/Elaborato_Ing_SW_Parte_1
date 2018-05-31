package generators;

import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility class not used for the project.
 * @author Manuel Gallina
 */
public class RandomNounGenerator {
    private static final int MIN_WORD_LENGTH = 15;
    private static final int MAX_WORD_LENGTH = 100;
    private static final int WORDS_PER_LETTER = 10;

    private static Scanner in;
    private static RandomWords nouns;
    private static PrintStream out;

    private enum Mode {SUPERVISED, UNSUPERVISED}

    public static void main(String[] args) {
        Mode mode = Mode.UNSUPERVISED;

        nouns = new RandomWords(PoolLoader.fromTXTFile("resources/tmp/words.txt"));
        in = new Scanner(System.in);
        ArrayList<String> letters = new ArrayList<>();
        try {
            out = new PrintStream("C:\\Users\\Manuel Gallina\\Desktop\\parole.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("> Insert letter(s) ['!' to stop]: ");
        String input;
        do {
            input = in.next();
            if(!input.equals("!"))
                letters.add(input.substring(0,1));
        } while(!input.equals("!"));

        for(String l : letters) {
            try {
                generate(l, mode);
            } catch (Exception e) {
                System.out.printf("No more words for letter %s %n%n", l);
            }
        }
    }

    private static void generate(String letter, Mode mode) throws Exception {
        ArrayList<String> words = new ArrayList<>();
        for(int i = 0; i < WORDS_PER_LETTER; i++) {
            boolean done = false;
            do {
                String word = nouns.nextWord();
                int notFoundCounter = 0;
                while (!word.substring(0, 1).equalsIgnoreCase(letter.substring(0, 1))
                        || words.contains(word)
                        || (word.length() < MIN_WORD_LENGTH || word.length() > MAX_WORD_LENGTH))
                {
                    word = nouns.nextWord();
                    notFoundCounter++;
                    if(notFoundCounter > nouns.getPoolSize())
                        throw new Exception();
                }
                words.add(word);

                System.out.println(word);
                if (mode == Mode.SUPERVISED) {
                    if (in.next().equalsIgnoreCase("y")) {
                        out.println(word);
                        done = true;
                    }
                } else if (mode == Mode.UNSUPERVISED) {
                    out.println(word);
                    done = true;
                }
            } while(!done);
        }
        out.println();
    }
}
