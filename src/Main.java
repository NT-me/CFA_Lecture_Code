import LexicalParser.*;
import Tools.LexicalToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Worldddddd!");

        // ArrayList<String> mots = Lexer.fileToMots("./src/source.c");
        Lexer.fileToTokens("./src/source.c");

        

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

        ArrayList<LexicalToken> tokens=Lexer.cleanArray(Lexer.tokenList);
        
        for (LexicalToken token : tokens) {

            System.out.println("token -->" +  token.getValue() + "<");
            // System.out.println(">" + mot.length() + "<");

            // System.out.println("__size mot :"+mot.length());

        }
                System.out.println("nb de tokens total:"+tokens.size());



        // for (String key : keywords) {

        //     System.out.println(">" + key + "<");

        //     // System.out.println("__size mot :"+mot.length());

        // }




        // ArrayList<String> tokens =Lexer.motsToTokens(mots);
        // for (String token : tokens) {

        //     System.out.println("token -->" + token + "<");

        //     // System.out.println("__size mot :"+mot.length());

        // }

        // System.out.println("nb de tokens total:"+tokens.size());


        // Set set = Lexer.getIndentation().entrySet();
        // Iterator i = set.iterator();
      
        // // Display elements
        // while(i.hasNext()) {
        //    Map.Entry me = (Map.Entry)i.next();
        //    System.out.print(me.getKey() + ": ");
        //    System.out.println(me.getValue());
        // }
        // System.out.println("nbLignes:"+Lexer.nbLignes);

    }
}