import LexicalParser.*;
import Tools.LexicalToken;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Worldddddd!");

        ArrayList<LexicalToken> tokens=Lexer.fileToTokens("./src/source.c"); //récupere les tokens
        
        for (LexicalToken token : tokens) {
            System.out.println("token -->" +  token.getValue() + "<");
            System.out.println("type -->" +  token.getType() + "<");
            System.out.println("ligne -->" +  token.getLine() + "<\n");
        }
        System.out.println("nb de tokens total:"+tokens.size()+"\n");
        HashMap hm=Lexer.getHashMap_indentation(); // pour avoir la HashMap
        Lexer.afficherHM_Indentation();
        Lexer.afficherHM_LastChar();
        Lexer.afficherHM_LineLength();


    }
}