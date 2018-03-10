package main;
import java.io.File;
import java.util.*;

public class MainTest {

    static Database database = Database.getInstance();
    static ArrayList<String> nomi = setNomiECognomi("nomi_italiani.txt");
    static ArrayList<String> cognomi = setNomiECognomi("cognomi_italiani.txt");
    public static final int HOW_MANY = 4000;

    public static void main(String[] args) {
        Random rand = new Random();
        int lenN = nomi.size();
        int lenC = cognomi.size();

        for(int i = 0; i < HOW_MANY; i++) {
            String nome = nomi.get(rand.nextInt(lenN));
            String cognome = cognomi.get(rand.nextInt(lenC));
            String username = nome + "_" + cognome + "_" + rand.nextInt(Integer.MAX_VALUE);
            String password = rand.nextLong() + "";
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(rand.nextInt(65) + 1935, rand.nextInt(12), rand.nextInt(30) + 1);

            //ci saranno molti più clienti che non operatori ovviamente
            User o = (rand.nextDouble() > 0.9) ?
                    new Operator(nome, cognome, username, password, gregorianCalendar) :
                    new Customer(nome, cognome, username, password, gregorianCalendar);

            database.addUser(o);
        }

        //uso values() perchè è un metodo che ritorna una collection "stream"abile, la hash map non lo è purtroppo
        database.getUserList().values().stream()
                .filter(s -> s.isCustomer())
                .forEach(s -> System.out.println(s.toString() + "\t\tExpiry date: " + 0 + "\n"));

    }

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