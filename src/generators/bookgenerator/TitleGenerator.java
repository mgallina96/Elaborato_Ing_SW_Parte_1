package generators.bookgenerator;
import generators.randomwords.PoolLoader;
import generators.randomwords.RandomWords;

import java.util.Random;

public class TitleGenerator {

    private static TitleGenerator instance;
    private Random random = new Random();

    private RandomWords firstNames = new RandomWords(PoolLoader.fromTXTFile("test_resources\\users\\nomi_italiani.txt"));
    private RandomWords lastNames = new RandomWords(PoolLoader.fromTXTFile("test_resources\\users\\cognomi_italiani.txt"));

    private RandomWords nounsMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_maschili_singolari.txt"));
    private RandomWords nounsMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_maschili_plurali.txt"));
    private RandomWords nounsFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_femminili_singolari.txt"));
    private RandomWords nounsFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\sostantivi_femminili_plurali.txt"));

    private RandomWords adjectivesMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_maschili_singolari.txt"));
    private RandomWords adjectivesMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_maschili_plurali.txt"));
    private RandomWords adjectivesFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_femminili_singolari.txt"));
    private RandomWords adjectivesFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\aggettivi_femminili_plurali.txt"));

    private RandomWords articlesMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_maschili_singolari.txt"));
    private RandomWords articlesMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_maschili_plurali.txt"));
    private RandomWords articlesFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_femminili_singolari.txt"));
    private RandomWords articlesFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\articoli_femminili_plurali.txt"));

    private RandomWords conjunctions = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\congiunzioni_italiane.txt"));
    private RandomWords adverbs = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\avverbi.txt"));
    private RandomWords prepositions = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_italiane.txt"));

    private RandomWords prepsMS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_maschili_singolari.txt"));
    private RandomWords prepsMP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_maschili_plurali.txt"));
    private RandomWords prepsFS = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_femminili_singolari.txt"));
    private RandomWords prepsFP = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\preposizioni_articolate_femminili_plurali.txt"));

    private RandomWords verbs = new RandomWords(PoolLoader.fromTXTFile("test_resources\\media\\verbi_italiani.txt"));

    public TitleGenerator() {}

    public static TitleGenerator getInstance() {
        if(instance == null)
            instance = new TitleGenerator();

        return instance;
    }

    public String generateTitle() {
        if(random.nextDouble() > 0.85) {
            if(random.nextBoolean()) {
                return capitalize(randomNoun());
            }
            else {
                return capitalize(random.nextBoolean() ? (adverbs.nextWord()) : (verbs.nextWord()));
            }
        }
        else {
            switch(random.nextInt(10)) {
                case 0: //la casa / nel buio
                    return articleNoun();
                case 1: //la casa incantata / il cavallo pazzo
                    return articleNounAdjective(false);
                case 2: //le avventure di harry potter
                    return articleNoun() + " " + capitalize(prepositions.nextWord()) + " " + firstnameLastname();
                case 3: //harry potter e la pietra filosofale
                    return firstnameLastname() + " " + capitalize(conjunctions.nextWord()) + " " + articleNounAdjective(false);
                case 4: //molto forte, incredibilmente vicino
                    return capitalize(adverbs.nextWord()) + " " + capitalize(randomAdjective()) + ", " + capitalize(adverbs.nextWord()) + " " + capitalize(randomAdjective());
                case 5: //l'estremo orizzonte
                    return articleNounAdjective(false);
                case 6: //giancarlo e maria
                    return firstNames.nextWord() + " " + capitalize(conjunctions.nextWord()) + " " + firstNames.nextWord();
                case 7: //la tragica storia di giancarlo e maria
                    return articleNounAdjective(true) + " " + capitalize(prepositions.nextWord()) + " " + firstNames.nextWord() + " " + capitalize(conjunctions.nextWord()) + " " + firstNames.nextWord();
                case 8: //la principessa sul pisello
                    return articleNoun() + " " + location();
                case 9: //uno strano caso
                    return articleNounAdjective(true);
            }
        }

        return "";
    }

    private String articleNoun() {
        switch(random.nextInt(4)) {
            case 0:
                return articlesMS.nextWord() + " " + nounsMS.nextWord();
            case 1:
                return articlesMP.nextWord() + " " + nounsMP.nextWord();
            case 2:
                return articlesFS.nextWord() + " " + nounsFS.nextWord();
            case 3:
                return articlesFP.nextWord() + " " + nounsFP.nextWord();
            default:
                return "";
        }
    }

    private String firstnameLastname() {
        return firstNames.nextWord() + " " + lastNames.nextWord();
    }

    private String articleNounAdjective(boolean flipNounAdjective) {
        switch(random.nextInt(4)) {
            case 0:
                return articlesMS.nextWord() + " " + (flipNounAdjective ? (adjectivesMS.nextWord() + " " + nounsMS.nextWord()) : (nounsMS.nextWord() + " " + adjectivesMS.nextWord()));
            case 1:
                return articlesMP.nextWord() + " " + (flipNounAdjective ? (adjectivesMP.nextWord() + " " + nounsMP.nextWord()) : (nounsMP.nextWord() + " " + adjectivesMP.nextWord()));
            case 2:
                return articlesFS.nextWord() + " " + (flipNounAdjective ? (adjectivesFS.nextWord() + " " + nounsFS.nextWord()) : (nounsFS.nextWord() + " " + adjectivesFS.nextWord()));
            case 3:
                return articlesFP.nextWord() + " " + (flipNounAdjective ? (adjectivesFP.nextWord() + " " + nounsFP.nextWord()) : (nounsFP.nextWord() + " " + adjectivesFP.nextWord()));
            default:
                return "";
        }
    }

    private String location() {
        switch(random.nextInt(4)) {
            case 0:
                return prepsMS.nextWord() + " " + nounsMS.nextWord();
            case 1:
                return prepsMP.nextWord() + " " + nounsMP.nextWord();
            case 2:
                return prepsFS.nextWord() + " " + nounsFS.nextWord();
            case 3:
                return prepsFP.nextWord() + " " + nounsFP.nextWord();
            default:
                return "";
        }
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String randomNoun() {
        switch(random.nextInt(4)) {
            case 0:
                return nounsMS.nextWord();
            case 1:
                return nounsMP.nextWord();
            case 2:
                return nounsFS.nextWord();
            case 3:
                return nounsFP.nextWord();
            default:
                return "";
        }
    }

    private String randomAdjective() {
        switch(random.nextInt(4)) {
            case 0:
                return adjectivesMS.nextWord();
            case 1:
                return adjectivesMP.nextWord();
            case 2:
                return adjectivesFS.nextWord();
            case 3:
                return adjectivesFP.nextWord();
            default:
                return "";
        }
    }
}
