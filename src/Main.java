import LexicalParser.*;
import Tools.LexicalToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Worldddddd!");

        Lexer.fileToTokens("./src/source.c");
        ArrayList<LexicalToken> tokens=Lexer.tokenList;
        
        for (LexicalToken token : tokens) {
            System.out.println("token -->" +  token.getValue() + "<");
            System.out.println("type -->" +  token.getType() + "<");
            System.out.println("ligne -->" +  token.getLine() + "<\n");
        }
        System.out.println("nb de tokens total:"+tokens.size());
        HashMap hm=Lexer.getIndentation(); // poiur avoir la HashMap
        Lexer.afficherHM();




    }
}