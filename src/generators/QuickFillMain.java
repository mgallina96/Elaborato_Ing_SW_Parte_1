package generators;
import main.model.database.DatabaseManager;
import main.model.database.filesystem.FileSystem;
import main.model.media.Book;
import main.model.media.Media;
import main.model.user.User;
import main.utility.notifications.Notifications;

/**
 * Created by Alessandro on 12/04/18.
 */
public class QuickFillMain {

    //di quanto volete riempire il database? Viene riempito con (2^FILLING_LEVEL)^2 utenti e libri.
    private static final byte FILLING_LEVEL = 6;

    public static void main(String[] args) {
        /*Notifications.setLanguage(Notifications.ENGLISH);

        DatabaseManager d = DatabaseManager.getInstance();
        Generator generator = new Generator(FILLING_LEVEL);

        for(User u : generator.getUsers())
            d.add(u);
        for(Media m : generator.getBooks()) {
            if(m instanceof Book)
                d.add(m, "root\\Libri\\" + ((Book)m).getGenre() + "\\");
        }

        //d.saveDatabase();*/

        FileSystem fs = FileSystem.getInstance();

        //fs.saveFileSystem();

        System.out.println(fs.tree(fs.getFileSystem().get(fs.getRootID()), 0));

        //System.out.println(fs.getAllPaths());

        //fs.getFileSystem().values().forEach(s -> System.out.println(s.getName() + "\t" + s.getChildren().size()));


        /*

        Da mettere nel costruttore del FileSystem. Utile per riempire il filesystem (per ora)

        Folder libri = new Folder("Libri", ROOT);
        Folder film = new Folder("Film", ROOT);

        Folder d = new Folder("Animali", libri);
        Folder e = new Folder("Fumetto", libri);
        Folder f = new Folder("Scienza", libri);
        Folder g = new Folder("Geografia", libri);
        Folder h = new Folder("Avventura", libri);
        Folder i = new Folder("Viaggi", libri);
        Folder j = new Folder("Didattica", libri);
        Folder k = new Folder("Fisica", libri);
        Folder l = new Folder("Automobili", libri);
        Folder m = new Folder("Arte", libri);
        Folder n = new Folder("Tecnica", libri);
        Folder o = new Folder("Romanzo epico", libri);
        Folder p = new Folder("Raccolta di favole", libri);
        Folder q = new Folder("Raccolta di poesie", libri);
        Folder r = new Folder("Religioso", libri);
        Folder s = new Folder("Salute e benessere", libri);
        Folder t = new Folder("Giardinaggio", libri);
        Folder u = new Folder("Spirituale", libri);
        Folder v = new Folder("Matematica", libri);
        Folder w = new Folder("Horror", libri);
        Folder x = new Folder("Romantico", libri);
        Folder y = new Folder("Giallo", libri);
        Folder z = new Folder("Fantasy", libri);
        Folder a0 = new Folder("Noir", libri);
        Folder a1 = new Folder("Thriller", libri);
        Folder a2 = new Folder("Fantascienza", libri);
        Folder a3 = new Folder("Commedia", libri);
        Folder a4 = new Folder("Erotica", libri);
        Folder a5 = new Folder("Musica", libri);
        Folder a6 = new Folder("Raccolta di fiabe", libri);
        Folder a7 = new Folder("Per bambini", libri);
        Folder a8 = new Folder("Fotografia", libri);
        Folder a9 = new Folder("Biografia", libri);
        Folder b0 = new Folder("Romanzo storico", libri);
        Folder b2 = new Folder("Comico", libri);

        Folder b3 = new Folder("Documentario", film);
        Folder b4 = new Folder("Avventura", film);
        Folder b5 = new Folder("Azione", film);
        Folder b6 = new Folder("Supereroi", film);
        Folder b7 = new Folder("Horror", film);
        Folder b8 = new Folder("Romantico", film);
        Folder b9 = new Folder("Giallo", film);
        Folder c0 = new Folder("Fantasy", film);
        Folder c1 = new Folder("Noir", film);
        Folder c2 = new Folder("Thriller", film);
        Folder c3 = new Folder("Fantascienza", film);
        Folder c4 = new Folder("Erotica", film);
        Folder c5 = new Folder("Romanzo storico", film);
        Folder c6 = new Folder("Comico", film);


        fileSystem.put(libri.getFolderId(), libri);
        fileSystem.put(film.getFolderId(), film);
        fileSystem.put(d.getFolderId(), d);
        fileSystem.put(e.getFolderId(), e);
        fileSystem.put(f.getFolderId(), f);
        fileSystem.put(g.getFolderId(), g);
        fileSystem.put(h.getFolderId(), h);
        fileSystem.put(i.getFolderId(), i);
        fileSystem.put(j.getFolderId(), j);
        fileSystem.put(k.getFolderId(), k);
        fileSystem.put(l.getFolderId(), l);
        fileSystem.put(m.getFolderId(), m);
        fileSystem.put(n.getFolderId(), n);
        fileSystem.put(o.getFolderId(), o);
        fileSystem.put(p.getFolderId(), p);
        fileSystem.put(q.getFolderId(), q);
        fileSystem.put(r.getFolderId(), r);
        fileSystem.put(s.getFolderId(), s);
        fileSystem.put(t.getFolderId(), t);
        fileSystem.put(u.getFolderId(), u);
        fileSystem.put(v.getFolderId(), v);
        fileSystem.put(w.getFolderId(), w);
        fileSystem.put(x.getFolderId(), x);
        fileSystem.put(y.getFolderId(), y);
        fileSystem.put(z.getFolderId(), z);
        fileSystem.put(a0.getFolderId(), a0);
        fileSystem.put(a1.getFolderId(), a1);
        fileSystem.put(a2.getFolderId(), a2);
        fileSystem.put(a3.getFolderId(), a3);
        fileSystem.put(a4.getFolderId(), a4);
        fileSystem.put(a5.getFolderId(), a5);
        fileSystem.put(a6.getFolderId(), a6);
        fileSystem.put(a7.getFolderId(), a7);
        fileSystem.put(a8.getFolderId(), a8);
        fileSystem.put(a9.getFolderId(), a9);
        fileSystem.put(b0.getFolderId(), b0);
        fileSystem.put(b2.getFolderId(), b2);
        fileSystem.put(b3.getFolderId(), b3);
        fileSystem.put(b4.getFolderId(), b4);
        fileSystem.put(b5.getFolderId(), b5);
        fileSystem.put(b6.getFolderId(), b6);
        fileSystem.put(b7.getFolderId(), b7);
        fileSystem.put(b8.getFolderId(), b8);
        fileSystem.put(b9.getFolderId(), b9);
        fileSystem.put(c0.getFolderId(), c0);
        fileSystem.put(c1.getFolderId(), c1);
        fileSystem.put(c2.getFolderId(), c2);
        fileSystem.put(c3.getFolderId(), c3);
        fileSystem.put(c4.getFolderId(), c4);
        fileSystem.put(c5.getFolderId(), c5);
        fileSystem.put(c6.getFolderId(), c6);

         */


    }
}
