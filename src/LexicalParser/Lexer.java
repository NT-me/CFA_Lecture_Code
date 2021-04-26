package LexicalParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lexer {


    
    public static ArrayList<String> fileToMots(String file) throws IOException {
        ArrayList<String> lines = getLignes("./src/source.c");
        ArrayList<String> mots = new ArrayList<String>();

        // int nbmots = 0;

        for (String line : lines) {
            mots.addAll(getMots(line));
            // System.out.println("\nla ligne:_>" + line);
            // System.out.println("length de la ligne :"+line.length());

            // nbmots += mots.size();

            // for (String mot : mots) {

            //     System.out.println(">" + mot + "<");
            //     // System.out.println("__size mot :"+mot.length());

            // }
        }
        System.out.println("\nNB MOTS TOTAL  : " + mots.size());

        return mots;

    }





    public static ArrayList<String> getMots(String lines) {
        int length = lines.length();
        ArrayList<String> mots = new ArrayList<String>();
        String mot = "";
        boolean firstSpace = true;
        // boolean finIndetation = false;
        // int cptIndentation = 0;
        char c = lines.charAt(0);

        if (length == 1 && c == '}') {
            mots.add("}");
            return mots;
        }

        for (int i = 0; i < length - 1; i++) {
            c = lines.charAt(i);

            if (i == 0) {

                while (c == ' ') {
                    i++;
                    // cptIndentation++;
                    c = lines.charAt(i);
                }
            }
            if (c == ' ' || c == '\n') {
                // System.out.println(mot);
                if (firstSpace) {
                    if (!mot.isBlank()) {
                        mots.add(mot);
                        mot = "";
                        firstSpace = false;
                    }
                }
            }
            else {
                // finIndetation=true;
                firstSpace = true;
                mot += c;
            }
        }
        // System.out.println(mot);
        if (!mot.isBlank()) {
            mots.add(mot);
            mot = "";
        }
        return mots;
    }






    public static ArrayList<String> getLignes(String file) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();

        try (FileReader f = new FileReader(file)) {

            StringBuffer sb = new StringBuffer();

            while (f.ready()) {
                char c = (char) f.read();
                if (c != '\n') {
                    // if(c==' '){
                    // System.out.println(sb.toString());
                    // sb = new StringBuffer();
                    // }
                    sb.append(c);

                } else {
                    lines.add(sb.toString());
                    sb = new StringBuffer();
                }
            }
            lines.add(sb.toString());
        }
        return lines;
    }
}

