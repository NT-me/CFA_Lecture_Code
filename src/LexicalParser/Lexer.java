package LexicalParser;

import Tools.LexicalToken;
import Tools.LexicalToken.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Lexer {

    private static int LARGESTTOKEN;
    public static String  tokenPrecedent="";
    public static LexicalToken  tokenPrecedentCopy=new LexicalToken(null, "", -1);

    public static HashMap hm = new HashMap();
    public static ArrayList<LexicalToken> tokenList= new ArrayList<LexicalToken>() ;
    public static  int numLigne=0;
    public static  int cptLigne=0;

    public static  int nbLignes;


    



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
        nbLignes=hm.size();


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


    public static LexicalToken splitOneTokenCopy(String mot) {
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


                return new LexicalToken(null, mot, -1);
            }

        }
        // System.out.println( substring);

        // }
        return null;
    }


    public static  void  fileToTokens(String file) throws IOException {
        ArrayList<String> lines = getLignes("./src/source.c");

        ArrayList<LexicalToken> mots = new ArrayList<LexicalToken>();

        // int nbmots = 0;
        for (String line : lines) {
            mots.addAll(getMotsCopy(line ));

        }
        System.out.println("\nNB MOTS TOTAL  : " + mots.size());
        nbLignes=hm.size();


        // return mots;

        
        for (LexicalToken mot : mots) {

            System.out.println("mot -->" +  mot.getValue() + "<");
            // System.out.println(">" + mot.length() + "<");

            // System.out.println("__size mot :"+mot.length());

        }


        // ArrayList<LexicalToken> tokens = new ArrayList<LexicalToken>();
        int length = mots.size();
        for (int i = 0; i < length; i++) {

            // if (!tokens.isEmpty()) {
            //     tokenPrecedentCopy = tokens.get(tokens.size()-1);
            // }
            splitTokenCopy(mots.get(i));
        }


        // tokens=cleanArray(tokens);





    }



    public static ArrayList<String> motsToTokens(ArrayList<String> mots) {
        ArrayList<String> tokens = new ArrayList<String>();
        int length = mots.size();
        for (int i = 0; i < length; i++) {

            if (!tokens.isEmpty()) {
                tokenPrecedent = tokens.get(tokens.size()-1);
            }
            tokens.addAll(splitTokenSave2(mots.get(i)));
        }


        // tokens=cleanArray(tokens);
        return tokens;

    }


    public static HashMap getIndentation(){
        return hm;
    }




    public static ArrayList<LexicalToken> splitTokenCopy(LexicalToken lexicalToken) {
        ArrayList<LexicalToken> tokens = new ArrayList<LexicalToken>();
        int fullWordSize = lexicalToken.getValue().length();
        boolean cutted=false;
        int length = lexicalToken.getValue().length();
        ArrayList<LexicalToken> token = new ArrayList<LexicalToken>();

        String substring = "";
        char c;

        for (int i = 0; i < length; i++) {
            substring = lexicalToken.getValue().substring(0, i + 1);
            c = substring.charAt(i);

            cutted = analyzeSubstringCopy(substring, i, c, fullWordSize);

            if (cutted) {

                // tokens.addAll(token);
                // tokenPrecedentCopy = token.get(token.size() - 1);
                lexicalToken =new LexicalToken(null, lexicalToken.getValue().substring(i + 1, length), -1) ;
                length = lexicalToken.getValue().length();
                i = -1;
                cutted=false;

            }
        }

        // if (tokenPrecedent.equals("int") && !mot.isBlank())
        if (!lexicalToken.getValue().isBlank()){

            tokenList.add(lexicalToken);
            tokenPrecedentCopy=new LexicalToken(null, lexicalToken.getValue(), -1);
            // System.out.println(tokenPrecedent +"<----- tok prec");
        }
        return tokens;
    }

    public static ArrayList<String> splitTokenSave2(String mot) {
        ArrayList<String> tokens = new ArrayList<String>();
        int fullWordSize = mot.length();

        int length = mot.length();
        ArrayList<String> token = new ArrayList<String>();

        String substring = "";
        char c;

        for (int i = 0; i < length; i++) {
            substring = mot.substring(0, i + 1);
            c = substring.charAt(i);

            token = analyzeSubstring(substring, i, c, fullWordSize);

            if (!token.isEmpty()) {

                tokens.addAll(token);
                tokenPrecedent = token.get(token.size() - 1);
                mot = mot.substring(i + 1, length);
                length = mot.length();
                i = -1;

            }
        }

        // if (tokenPrecedent.equals("int") && !mot.isBlank())
        if (!mot.isBlank()){

            tokens.add(mot);
            tokenPrecedent=mot;
        }
            // System.out.println(tokenPrecedent +"<----- tok prec");

        return tokens;
    }

    private static ArrayList<String> analyzeSubstring(String mot, int i, char c, int fullWordSize) {
        ArrayList<String> tokens = new ArrayList<String>();
        String token = "";
        String chara=String.valueOf(c);
        // String test2=c.toString();
        


        // System.out.println(mot+": SUBS  et C:"+c +"  preced:"+tokenPrecedent);

        
        if(splitOneToken(chara)!=null){
            tokenPrecedent = mot.substring(0, i);
            tokens.add(mot.substring(0, i));
            tokenPrecedent = mot.substring(i, i + 1);
            tokens.add(mot.substring(i, i + 1));
        }

        else if (tokenPrecedent.equals("int") ) {
            if (c == '=' || c == ';') {
                // System.out.println("XXXXX:" + mot.substring(0, i));
                tokenPrecedent = mot.substring(0, i);
                // System.out.println("chara:"+c+"     subs:"+mot);

                // System.out.println(tokenPrecedent+"-1");
                tokens.add(mot.substring(0, i));

                tokenPrecedent = mot.substring(i, i + 1);
                // System.out.println(tokenPrecedent+"-2");
                tokens.add(mot.substring(i, i + 1));

                // System.out.println("RESTE:" + mot.substring(i + 1, length));
                // mot = mot.substring(i + 1, length);
                // substring = mot;
                // length = mot.length();
                // i = -1;

            }

        }
        // else if (tokenPrecedent.equals("=")) {
        //     if(c == ';'){
        //         // System.out.println("XXXXX:" + mot.substring(0, i));
        //         tokenPrecedent = mot.substring(0, i);
        //         System.out.println("chara:"+c+"     subs:"+mot);
        //         System.out.println(tokenPrecedent+"-3");
        //         tokens.add(mot.substring(0, i));

        //         tokenPrecedent = mot.substring(i, i + 1);
        //         System.out.println(tokenPrecedent+"-4");
        //         tokens.add(mot.substring(i, i + 1));

        //         // System.out.println("RESTE:" + mot.substring(i + 1, length));
        //         // mot = mot.substring(i + 1, length);
        //         // substring = mot;
        //         // length = mot.length();
        //         // i = -1;


        //     }
        // }
        else if (tokenPrecedent.equals("return")) {
            if(c == ';'){
                // System.out.println("XXXXX:" + mot.substring(0, i));
                tokenPrecedent = mot.substring(0, i);
                // System.out.println("chara:"+c+"     subs:"+mot);
                // System.out.println(tokenPrecedent+"-3");
                tokens.add(mot.substring(0, i));

                tokenPrecedent = mot.substring(i, i + 1);
                // System.out.println(tokenPrecedent+"-4");
                tokens.add(mot.substring(i, i + 1));

                // System.out.println("RESTE:" + mot.substring(i + 1, length));
                // mot = mot.substring(i + 1, length);
                // substring = mot;
                // length = mot.length();
                // i = -1;


            }
        }
        else {

            token = splitOneToken(mot);

            if (token != null) {
                tokenPrecedent = token;
                // System.out.println("chara:"+c+"     subs:"+mot);

                // System.out.println(tokenPrecedent+"-5");

                tokens.add(token);
   

                


                // mot = mot.substring(i + 1, length);

                // length = mot.length();

            }
        }
        // if(fullWordSize-1== i)



        return tokens;
    }







    private static boolean analyzeSubstringCopy(String mot, int i, char c, int fullWordSize) {
        ArrayList<LexicalToken> tokens = new ArrayList<LexicalToken>();
        LexicalToken token = new LexicalToken(null, "", -1);
        String chara=String.valueOf(c);
        boolean cutted=false;
        // String test2=c.toString();
        


        // System.out.println(mot+": SUBS  et C:"+c +"  preced:"+tokenPrecedent);

        
        if(splitOneTokenCopy(chara)!=null){
            tokenPrecedentCopy = new LexicalToken(null, mot.substring(0, i), -1);
            tokenList.add(new LexicalToken(null, mot.substring(0, i), -1) );
            tokenPrecedentCopy = new LexicalToken(null,mot.substring(i, i + 1), -1);
            tokenList.add(new LexicalToken(null, mot.substring(i, i + 1), -1) );
            cutted=true;
        }

        else if (tokenPrecedentCopy.getValue().equals("int") ) {
            if (c == '=' || c == ';') {
                // System.out.println("XXXXX:" + mot.substring(0, i));
                tokenPrecedentCopy = new LexicalToken(null, mot.substring(0, i), -1);
                // System.out.println("chara:"+c+"     subs:"+mot);

                // System.out.println(tokenPrecedent+"-1");
                tokenList.add(new LexicalToken(null, mot.substring(0, i), -1) );

                tokenPrecedentCopy = new LexicalToken(null,mot.substring(i, i + 1), -1);
                // System.out.println(tokenPrecedent+"-2");
                tokenList.add(new LexicalToken(null, mot.substring(i, i + 1), -1) );
                cutted=true;
                // System.out.println("RESTE:" + mot.substring(i + 1, length));
                // mot = mot.substring(i + 1, length);
                // substring = mot;
                // length = mot.length();
                // i = -1;

            }

        }
        // else if (tokenPrecedent.equals("=")) {
        //     if(c == ';'){
        //         // System.out.println("XXXXX:" + mot.substring(0, i));
        //         tokenPrecedent = mot.substring(0, i);
        //         System.out.println("chara:"+c+"     subs:"+mot);
        //         System.out.println(tokenPrecedent+"-3");
        //         tokens.add(mot.substring(0, i));

        //         tokenPrecedent = mot.substring(i, i + 1);
        //         System.out.println(tokenPrecedent+"-4");
        //         tokens.add(mot.substring(i, i + 1));

        //         // System.out.println("RESTE:" + mot.substring(i + 1, length));
        //         // mot = mot.substring(i + 1, length);
        //         // substring = mot;
        //         // length = mot.length();
        //         // i = -1;


        //     }
        // }
        else if (tokenPrecedentCopy.getValue().equals("return")) {
            if(c == ';'){
                // System.out.println("XXXXX:" + mot.substring(0, i));
                tokenPrecedentCopy = new LexicalToken(null, mot.substring(0, i), -1);
                // System.out.println("chara:"+c+"     subs:"+mot);
                // System.out.println(tokenPrecedent+"-3");
                tokenList.add(new LexicalToken(null, mot.substring(0, i), -1) );

                tokenPrecedentCopy = new LexicalToken(null,mot.substring(i, i + 1), -1);
                // System.out.println(tokenPrecedent+"-4");
                tokenList.add(new LexicalToken(null, mot.substring(i, i + 1), -1) );
                cutted=true;
                // System.out.println("RESTE:" + mot.substring(i + 1, length));
                // mot = mot.substring(i + 1, length);
                // substring = mot;
                // length = mot.length();
                // i = -1;


            }
        }
        else {

            token = splitOneTokenCopy(mot);

            if (token != null) {
                tokenPrecedentCopy = new LexicalToken(null, token.getValue(), -1) ;
                // System.out.println("chara:"+c+"     subs:"+mot);

                // System.out.println(tokenPrecedent+"-5");
                cutted=true;
                tokenList.add(token);
   

                


                // mot = mot.substring(i + 1, length);

                // length = mot.length();

            }
        }
        // if(fullWordSize-1== i)

        if( cutted) return true;

        return false;
    }






    public static ArrayList<LexicalToken> cleanArray(ArrayList<LexicalToken> Tokens) {
        ArrayList<LexicalToken> cleanArray = new ArrayList<LexicalToken>();

        for(LexicalToken token : Tokens){
            if (!token.getValue().isBlank()){
                cleanArray.add(token);
            }
        }
        return cleanArray;





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
                if (c == '=' || c == ';') {
                    // System.out.println("XXXXX:" + mot.substring(0, i));
                    tokenPrecedent = mot.substring(0, i);
                    tokens.add(mot.substring(0, i));
                    tokens.add(mot.substring(i, i + 1));

                    // System.out.println("RESTE:" + mot.substring(i + 1, length));
                    mot = mot.substring(i + 1, length);
                    // substring = mot;
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
                    // if (i != length - 1) {

                    // System.out.println("\nmotAV: "+mot);
                    // System.out.println("altAP:"+mot.substring(i+1,length));
                    mot = mot.substring(i + 1, length);
                    // System.out.println("motAP: "+mot);

                    // test=mot.su

                    // System.out.println("\nreste: "+mot+"\n");

                    // System.out.println("\nreste du mot:"+mot+"\n");

                    length = mot.length();
                    i = -1;

                    // }
                    // else {

                    // // System.out.println("\nmotAV: "+mot);
                    // // System.out.println("(rare)altAP:"+mot.substring(i+1,length));
                    // // System.out.println("subs: "+mot.substring(i + 1, length));

                    // mot = mot.substring(i + 1, length);
                    // // System.out.println("motAP: "+mot);

                    // // test=mot.su

                    // System.out.println("\nreste: "+mot+"\n");

                    // // System.out.println("\nreste du mot:"+mot+"\n");

                    // length = mot.length();
                    // i = -1;

                    // }

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
        keyWords.add(">");
        keyWords.add("<");
        keyWords.add("-");
        keyWords.add("+");
        keyWords.add("*");

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
        keyWords.add("return");

        LARGESTTOKEN = keyWords.size();
        // System.out.println("XXXXXX"+ keyWords.get(0).substring(0,1));

        return keyWords;
    }




    public static ArrayList<LexicalToken> getMotsCopy(String lines) {
        int length = lines.length();
        ArrayList<LexicalToken> mots = new ArrayList<LexicalToken>();
        LexicalToken mot = new LexicalToken(null, "",-1);
        boolean firstSpace = true;


        // boolean finIndetation = false;
        int cptIndentation = 0;
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
                    cptIndentation++;

                    c = lines.charAt(i);
                }
                hm.put(numLigne, cptIndentation);
                numLigne++;


            }
            if (c == ' ' || c == '\n') {
                // System.out.println(mot);
                if (firstSpace) {
                    if (!mot.getValue().isBlank()) {
                        mots.add(mot);
                        mot=new LexicalToken(null, "",-1);
                        firstSpace = false;
                    }
                }
            } else {
                // finIndetation=true;
                firstSpace = true;
                mot.setValue(mot.getValue()+c);
            }
        }
        // System.out.println(mot);
        if (!mot.getValue().isBlank()) {
            mots.add(mot);
            mot=new LexicalToken(null, "",-1);
        }
        return mots;
    }

    public static ArrayList<String> getMots(String lines) {
        int length = lines.length();
        ArrayList<String> mots = new ArrayList<String>();
        String mot = "";
        boolean firstSpace = true;


        // boolean finIndetation = false;
        int cptIndentation = 0;
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
                    cptIndentation++;

                    c = lines.charAt(i);
                }
                hm.put(numLigne, cptIndentation);
                numLigne++;


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
