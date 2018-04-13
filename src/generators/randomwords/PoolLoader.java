package generators.randomwords;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PoolLoader {

    private PoolLoader() {}

    public static String[] fromTXTFile(String path) {
        ArrayList<String> arrayList = new ArrayList<>();
        int counter = 0;
        File f = new File(path);

        try {
            Scanner s = new Scanner(f);

            while(s.hasNextLine()) {
                arrayList.add(s.nextLine());
                counter++;
            }

            s.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        String[] toString = new String[counter];

        for(int i = 0; i < counter; i++) {
            toString[i] = arrayList.get(i);
        }

        return toString;
    }

}
