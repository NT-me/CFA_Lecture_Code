import LexicalParser.*;
import Tools.LexicalToken;
import SyntaxicalParser.SynParser;
import java.util.ArrayList;
import java.util.HashMap;
import Scoring.Scorer;
import Tools.Node;
import Tools.RootNode;


public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<LexicalToken> tokens=Lexer.fileToTokens("./src/source.c"); //récupere les tokens
        HashMap hm=Lexer.getHashMap_indentation(); // pour avoir la HashMap


        Node AST = SynParser.parsing(tokens);

        for (LexicalToken token : tokens) {
            System.out.println("token -->" +  token.getValue() + "<");
            System.out.println("type -->" +  token.getType() + "<");
            System.out.println("ligne -->" +  token.getLine() + "<\n");
        }

        ArrayList<String> retErrors = Scorer.checkIndentation(hm, tokens);
        retErrors.addAll(Scorer.notMore200Lines(hm));
        retErrors.addAll(Scorer.varNameConventions(AST));
        retErrors.addAll(Scorer.checkUsedDecl(AST));
        retErrors.addAll(Scorer.findOperationWithoutStore(AST));
        retErrors.addAll(Scorer.checkLineLength(Lexer.getHashMap_LineLength()));

        for(String error : retErrors){
            System.out.println(error);
        }
    }
}