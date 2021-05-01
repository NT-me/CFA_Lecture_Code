package LexicalParser;

import Tools.LexicalToken;
import Tools.lexicalType;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Lexer {

    public static LexicalToken tokenPrecedent = new LexicalToken(null, "", -1);

    public static ArrayList<LexicalToken> mots = new ArrayList<LexicalToken>();

    public static HashMap hm_indentation = new HashMap();
    public static HashMap hm_LineLength = new HashMap();
    public static HashMap hm_LastChar = new HashMap();


    public static ArrayList<LexicalToken> tokenList = new ArrayList<LexicalToken>();
    public static int numLigne = 0;
    public static int nbLignes;
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static void typage(ArrayList<LexicalToken> tokens) {
        LexicalToken token;
        LexicalToken prec = null;
        LexicalToken suiv = null;

        int length = tokens.size();
        for (int i = 0; i < length; i++) {
            token = tokens.get(i);
            if (i > 0) {
                prec = tokens.get(i - 1);
            } else
                prec = null;

            if (i < length - 2) {
                suiv = tokens.get(i + 1);
            } else
                suiv = null;

            switch (token.getValue()) {
            case "(":
                token.setType(lexicalType.Lp);
                break;

            case ")":
                token.setType(lexicalType.Rp);
                break;
            case "int":
                token.setType(lexicalType.BasicType);
                break;
            case "main":
                token.setType(lexicalType.Function);
                break;
            case "float":
                token.setType(lexicalType.BasicType);
                break;
            case "for":
                token.setType(lexicalType.Keyword);
                break;
            case "if":
                token.setType(lexicalType.Keyword);
                break;

            case "+":
                token.setType(lexicalType.Ope);
                break;
            case "\"":
                token.setType(lexicalType.Quote);
                break;
            case "{":
                token.setType(lexicalType.Lb);
                break;
            case "}":
                token.setType(lexicalType.Rb);
                break;
            case "=":
                token.setType(lexicalType.Ope);
                break;
            case ";":
                token.setType(lexicalType.EoI);
                break;
            case ",":
                token.setType(lexicalType.Coma);
                break;
            case "return":
                token.setType(lexicalType.Keyword);
                break;
            default:
                token.setType(lexicalType.Word);
            }

            if (prec != null) {
                switch (prec.getValue()) {
                case "int":
                    token.setType(lexicalType.Word);
                    break;
                case "float":
                    token.setType(lexicalType.Word);
                    break;
                }
            }

            if (suiv != null) {
                switch (suiv.getValue()) {
                case "(":
                    token.setType(lexicalType.Function);
                    break;
                }

            }

            if (isNumeric(token.getValue())) {
                token.setType(lexicalType.Number);
            }

        }

    }

    public static LexicalToken checkIfKeyword(String mot, int line) {
        ArrayList<String> keywords = Lexer.getKeyWords();

        for (String key : keywords) {
            if (key.equals(mot)) {
                return new LexicalToken(null, mot, line);
            }
        }
        return null;
    }

    public static void quoteChecker() {

        String text = "";
        int length = tokenList.size();
        boolean debutQuote = false;

        ArrayList<LexicalToken> copie = new ArrayList<LexicalToken>();

        for (int i = 0; i < length; i++) {

            if (debutQuote) {
                text += tokenList.get(i).getValue();
                // System.out.println("concat: " + text);

            }else copie.add(tokenList.get(i));
            if (tokenList.get(i).getType().equals(lexicalType.Quote)) {
                if (debutQuote){
                    copie.add(new LexicalToken(tokenList.get(i-1).getType(), text.substring(0,text.length()-1), tokenList.get(i).getLine()) );
                    copie.add(new LexicalToken(lexicalType.Quote,"\"" , tokenList.get(i).getLine()) );

                    debutQuote = false;
                }
                else {
                    debutQuote = true;
                    text = "";

                }
                // System.out.println("QUOTE");

            }
            

        }

        tokenList = copie;

    }

    public static ArrayList<LexicalToken> fileToTokens(String file) throws IOException {
        ArrayList<String> lines = readLines("./src/source.c");
        int cpt=1;

        for (String line : lines) {
            mots.addAll(cutLines(line,cpt));
            cpt++;

        }
        nbLignes = hm_indentation.size();

        cpt=1;
        for (LexicalToken mot : mots) {
            analyzeSubstrings(mot);

        }

        cleanArray();   
        typage(tokenList);
        quoteChecker();
        return tokenList;

    }

    public static HashMap getHashMap_indentation() {
        return hm_indentation;
    }

    public static HashMap getHashMap_LineLength() {
        return hm_LineLength;
    }

    public static HashMap getHashMap_LastChar() {
        return hm_LastChar;
    }

    public static void analyzeSubstrings(LexicalToken lexicalToken) {

        boolean cutted = false;
        int length = lexicalToken.getValue().length();

        String substring = "";
        char c;

        for (int i = 0; i < length; i++) {

            substring = lexicalToken.getValue().substring(0, i + 1);
            c = substring.charAt(i);

            cutted = cutIntoTokens(substring, i, c, lexicalToken.getLine());

            if (cutted) {

                lexicalToken = new LexicalToken(null, lexicalToken.getValue().substring(i + 1, length),
                        lexicalToken.getLine());
                length = lexicalToken.getValue().length();
                i = -1;
                cutted = false;

            }
        }

        if (!lexicalToken.getValue().isBlank()) {
            tokenList.add(lexicalToken);
            tokenPrecedent = new LexicalToken(null, lexicalToken.getValue(), -1);
        }
    }

    private static void addToken(lexicalType type, String value, int line) {
        tokenPrecedent = new LexicalToken(null, value, line);
        tokenList.add(new LexicalToken(null, value, line));
    }

    private static boolean cutIntoTokens(String mot, int i, char c, int line) {
        LexicalToken token = new LexicalToken(null, "", -1);
        String chara = String.valueOf(c);
        boolean cutted = false;

        if (checkIfKeyword(chara, line) != null) {
            addToken(null, mot.substring(0, i), line);
            addToken(null, mot.substring(i, i + 1), line);

            cutted = true;
        }

        else if (tokenPrecedent.getValue().equals("int")) {
            if (checkIfKeyword(chara, line) != null) {
                addToken(null, mot.substring(0, i), line);
                addToken(null, mot.substring(i, i + 1), line);

                cutted = true;
            }
        } else if (tokenPrecedent.getValue().equals("\"")) {
            if (chara.equals("\"")) {
                addToken(null, mot.substring(0, i), line);
                addToken(null, mot.substring(i, i + 1), line);

                cutted = true;
            }
        }

        else {

            token = checkIfKeyword(mot, line);

            if (token != null) {
                tokenPrecedent = new LexicalToken(null, token.getValue(), line);
                tokenList.add(token);
                cutted = true;
            }
        }

        if (cutted)
            return true;

        return false;
    }

    public static void cleanArray() {
        ArrayList<LexicalToken> copie = new ArrayList<LexicalToken>();

        for (LexicalToken token : tokenList) {
            if (!token.getValue().isBlank()) {
                copie.add(token);
            }
        }
        tokenList = copie;

    }

    public static void afficherHM_Indentation() {
        Set set = hm_indentation.entrySet();
        Iterator i = set.iterator();

        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print("ligne: " + me.getKey() + ": ");
            System.out.println("    /indentation :" + me.getValue());
        }
    }


    public static void afficherHM_LineLength() {
        Set set = hm_LineLength.entrySet();
        Iterator i = set.iterator();

        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print("ligne: " + me.getKey() + ": ");
            System.out.println("    /longueur ligne :" + me.getValue());
        }
    }

    public static void afficherHM_LastChar() {
        Set set = hm_LastChar.entrySet();
        Iterator i = set.iterator();

        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print("ligne: " + me.getKey() + ": ");
            System.out.println("    /dernier caractère :" + me.getValue());
        }
    }

    public static ArrayList<String> getKeyWords() {
        ArrayList<String> keyWords = new ArrayList<String>();

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

        return keyWords;
    }

    public static ArrayList<LexicalToken> cutLines(String lines, int cpt) {
        int length = lines.length();
        ArrayList<LexicalToken> mots = new ArrayList<LexicalToken>();
        LexicalToken mot = new LexicalToken(null, "", cpt);
        boolean firstSpace = true;
        int cptIndentation = 0;
        char c;

        if(lines.isBlank())hm_indentation.put(cpt, 0);


        for (int i = 0; i < length - 1; i++) {

            c = lines.charAt(i);

            if (i == 0) {

                while (c == ' ') {
                    i++;
                    cptIndentation++;

                    c = lines.charAt(i);
                }
                hm_indentation.put(cpt, cptIndentation);


            }
            if (c == ' ') {
                if (firstSpace) {
                    if (!mot.getValue().isBlank()) {
                        mots.add(mot);
                        mot = new LexicalToken(null, "", cpt);
                        firstSpace = false;
                    }

                }

            } else {
                firstSpace = true;
                mot.setValue(mot.getValue() + c);
            }

        }
    



        if (!mot.getValue().isBlank()) {
            c = lines.charAt(length-1);
            mot.setValue(mot.getValue() + c);
            mots.add(mot);
            mot = new LexicalToken(null, "", cpt);
        }

        if(length==1){
            hm_indentation.put(cpt, 0);

            c= lines.charAt(0);
            mot.setValue(mot.getValue() + c);

            if (!mot.getValue().isBlank()){
                mots.add(mot);
                mot = new LexicalToken(null, "", cpt);


            }
        }

        // if (!mots.isEmpty())
        //     numLigne++;


        return mots;
    }

    public static ArrayList<String> readLines(String file) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        char prec=' ';

        try (FileReader f = new FileReader(file)) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();

                if (c != '\n') {
                    prec = c;

                    sb.append(c);

                } else {
                    lines.add(sb.toString());
                    hm_LineLength.put(numLigne+1, sb.toString().length());
                    if(sb.toString().length()>1 )hm_LastChar.put(numLigne+1, sb.toString().charAt(sb.toString().length()-2));
                    else hm_LastChar.put(numLigne+1,'\n');
                    numLigne++;
                    sb = new StringBuffer();
                }
            }
            lines.add(sb.toString());
            hm_LineLength.put(numLigne+1, sb.toString().length());
            if(sb.toString().length() > 0)hm_LastChar.put(numLigne+1,sb.toString().charAt(sb.toString().length()-1));
            else hm_LastChar.put(numLigne+1,null);
        }
        
        numLigne=0;
        return lines;
    }
}
