import LexicalParser.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Worldddddd!");

        ArrayList<String> mots = Lexer.fileToMots("./src/source.c");


        // ArrayList<String> lines = getLignes("./src/source.c");
        // String uneLigne = lines.get(0);
        // for (String line : lines) {
        // System.out.println(line);
        // }

        // System.out.println("ma ligne 1 : " + uneLigne);
        // System.out.println("longueur ligne 1 : " + uneLigne.length());
        // System.out.println("chara pos 10 (dernier) : " + uneLigne.charAt(10));

        // ArrayList<String> mots = getMots(uneLigne);

        // int nbmots = 0;
        // for (String line : lines) {
        //     mots = getMots(line);
        //     System.out.println("\nla ligne:_>" + line);
        //     // System.out.println("length de la ligne :"+line.length());

        //     nbmots += mots.size();
        //     for (String mot : mots) {

        //         System.out.println(">" + mot + "<");
        //         // System.out.println("__size mot :"+mot.length());

        //     }
        // }
        // System.out.println("\nNB MOTS TOTAL  : " + nbmots);

        for (String mot : mots) {

            System.out.println(">" + mot + "<");
            // System.out.println("__size mot :"+mot.length());

        }
    }
}