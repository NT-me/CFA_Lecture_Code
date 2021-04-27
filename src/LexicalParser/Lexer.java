package LexicalParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lexer {

    private static int LARGESTTOKEN;

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

            // System.out.println(">" + mot + "<");
            // // System.out.println("__size mot :"+mot.length());

            // }
        }
        System.out.println("\nNB MOTS TOTAL  : " + mots.size());

        return mots;

    }

    public static String splitOneToken(String mot) {
        ArrayList<String> keywords = Lexer.getKeyWords();

        int length = mot.length();
        String substring = "";
        // System.out.println("\n"+mot+" entier\n");

        // for (int i = 0; i < length; i++) {
        // substring=mot.substring(0,i+1);
        // System.out.println("\n"+substring+"\n");

        // substring.matches("printf*)");
        for (String key : keywords) {
            // System.out.println("\n"+substring);
            // System.out.println(key+"\n");

            if (key.equals(mot)) {
                // System.out.println(mot);

                return mot;
            }

        }
        // System.out.println( substring);

        // }
        return null;
    }

    public static ArrayList<String> motsToTokens(ArrayList<String> mots) {
        ArrayList<String> tokens = new ArrayList<String>();
        String tokenPrecedent = "";
        int length = mots.size();
        for (int i = 0; i < length; i++) {

            if (i != 0) {
                tokenPrecedent = mots.get(i - 1);
            }
            tokens.addAll(splitTokenSave(mots.get(i), tokenPrecedent));
        }
        return tokens;

    }

    public static ArrayList<String> splitTokenSave(String mot, String tokenPrecedent) {
        ArrayList<String> keywords = Lexer.getKeyWords();
        ArrayList<String> tokens = new ArrayList<String>();
        // System.out.println("PREC:"+tokenPrecedent);

        int length = mot.length();
        String token = "";
        String test = "";

        // tokenPrecedent = "";

        String substring = "";
        char c;

        for (int i = 0; i < length; i++) {
            substring = mot.substring(0, i + 1);
            // if(i==length-1)System.out.println(substring.charAt(i));
            c = substring.charAt(i);

            // A FONCTIONNALISER
            if (tokenPrecedent.equals("int")) {
                if(c == '=' || c == ';'){
                    // System.out.println("XXXXX:" + mot.substring(0, i));
                    tokenPrecedent=mot.substring(0, i);
                    tokens.add(mot.substring(0, i));
                    tokens.add(mot.substring(i, i + 1));
    
                    // System.out.println("RESTE:" + mot.substring(i + 1, length));
                    mot = mot.substring(i + 1, length);
                    substring = mot;
                    length = mot.length();
                    i = -1;


                }

            } else { // A FONCTIONNALISER

                // System.out.println("\n"+substring+"\n");

                // substring.matches("printf*)");
                // for(String key : keywords){
                // System.out.println("\n"+substring);
                // System.out.println(key+"\n");

                // System.out.println(mot);
                // System.out.println("wsh:"+substring);

                token = splitOneToken(substring);
                // System.out.println("TOKEN: "+token);

                if (token != null) {

                    // System.out.println(token);
                    tokens.add(token);
                    if (i != length - 1) {

                        // System.out.println("\nmotAV: "+mot);
                        // System.out.println("altAP:"+mot.substring(i+1,length));
                        mot = mot.substring(i + 1, length);
                        // System.out.println("motAP: "+mot);

                        // test=mot.su

                        // System.out.println("\nreste: "+mot+"\n");

                        // System.out.println("\nreste du mot:"+mot+"\n");

                        length = mot.length();
                        i = -1;

                    } else {

                        // System.out.println("\nmotAV: "+mot);
                        // System.out.println("(rare)altAP:"+mot.substring(i+1,length));
                        // System.out.println("subs: "+mot.substring(i + 1, length));

                        mot = mot.substring(i + 1, length);
                        // System.out.println("motAP: "+mot);

                        // test=mot.su

                        // System.out.println("\nreste: "+mot+"\n");

                        // System.out.println("\nreste du mot:"+mot+"\n");

                        length = mot.length();
                        i = -1;

                    }

                }
            }

            // System.out.println( substring);

        }
        if (tokenPrecedent.equals("int") && !mot.isBlank())
            tokens.add(mot);

        return tokens;
    }

    public static ArrayList<String> getKeyWords() {
        ArrayList<String> keyWords = new ArrayList<String>();
        // keyWords.add("ghffghffgf");

        keyWords.add("=");
        keyWords.add("\"");

        keyWords.add(";");
        keyWords.add(",");
        keyWords.add(")");
        keyWords.add("(");
        keyWords.add("{");
        keyWords.add("}");
        keyWords.add("main");
        keyWords.add("for");
        keyWords.add("if");
        keyWords.add("=");
        keyWords.add("==");
        keyWords.add("int");
        keyWords.add("float");
        keyWords.add("char");
        keyWords.add("printf");
        keyWords.add("=");
        keyWords.add("return");

        LARGESTTOKEN = keyWords.size();
        // System.out.println("XXXXXX"+ keyWords.get(0).substring(0,1));

        return keyWords;
    }

    public static ArrayList<String> getMots(String lines) {
        int length = lines.length();
        ArrayList<String> mots = new ArrayList<String>();
        String mot = "";
        boolean firstSpace = true;
        // boolean finIndetation = false;
        // int cptIndentation = 0;
        char c;
        // char c = lines.charAt(0);

        // if (length == 1 && c == '}') {
        // mots.add("}");
        // return mots;
        // }

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
            } else {
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
