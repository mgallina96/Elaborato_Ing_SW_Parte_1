package generators.randomwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class PoolLoader {

    private PoolLoader() {}

    public static String[] fromTXTFile(String path) {
        ArrayList<String> arrayList = new ArrayList<>();
        int counter = 0;
        File f = new File(path);

        try {
            BufferedReader s = new BufferedReader(new FileReader(f));

            String line;
            while((line = s.readLine()) != null) {
                arrayList.add(line.matches("[^a-z].*") ? line.replaceAll("\\p{C}", "") : line);
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
