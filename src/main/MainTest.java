package main;
import main.model.user.Customer;
import main.model.Database;
import main.model.user.Operator;
import main.model.user.User;

import java.io.File;
import java.util.*;

public class MainTest {

    //istanza database
    static Database database = Database.getInstance();
    //nomi e cognomi italiani per generarli casualmente
    static ArrayList<String> nomi = setNomiECognomi("nomi_italiani.txt");
    static ArrayList<String> cognomi = setNomiECognomi("cognomi_italiani.txt");
    //quanti utenti generare
    public static final int HOW_MANY = 4000;

    public static void main(String[] args) {
        Random rand = new Random();
        int lenN = nomi.size();
        int lenC = cognomi.size();

        //ciclo for che inizializza HOW_MANY utenti
        for(int i = 0; i < HOW_MANY; i++) {
            //inizializzo nomi, cognomi, username e password casualmente, prendendo valori dagli arraylist contenenti i nomi e cognomi italiani
            String nome = nomi.get(rand.nextInt(lenN));
            String cognome = cognomi.get(rand.nextInt(lenC));
            String username = nome + "_" + cognome + "_" + rand.nextInt(Integer.MAX_VALUE);
            String password = rand.nextLong() + "";
            //data casuale
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(rand.nextInt(70) + 1935, rand.nextInt(12), rand.nextInt(30) + 1);

            //ci saranno molti più clienti che non operatori ovviamente
            User o = (rand.nextDouble() > 0.9) ?
                    new Operator(nome, cognome, username, password, gregorianCalendar) :
                    new Customer(nome, cognome, username, password, gregorianCalendar);

            //aggiungo al database
            database.addUser(o);
        }

        //uso values() perchè è un metodo che ritorna una collection "stream"abile, la hash map non lo è purtroppo
        //qui visualizzo solo clienti e controllo se sono maggiorenni
        //-------------------------------------------------------------------------------------------------------------
        //vedete che il problema è che devo fare un cast a (Customer) se voglio accedere al metodo isOfAge
        //però non è sempre detto che nel database ci siano tutti oggetti di tipo Customer (anzi non è mai così)
        //quindi se faccio sempre un cast a (Customer) e mi capita un oggetto di tipo Operator parte l'eccezione
        //-------------------------------------------------------------------------------------------------------------
        database.getUserList().values().stream()
                .filter(s -> s.isCustomer())
                .forEach(s -> System.out.println(s.toString() + "\t\tOf age? " + ((Customer)s).isOfAge() + "\n"));

    }

    //carica in un arraylist i contenuti di un file di testo, in questo caso nomi e poi cognomi italiani
    public static ArrayList<String> setNomiECognomi(String pathName) {
        ArrayList<String> a = new ArrayList<>();
        File f = new File(pathName);

        try {
            Scanner fs = new Scanner(f);

            while(fs.hasNextLine()) {
                a.add(fs.nextLine());
            }

            fs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return a;
    }

}