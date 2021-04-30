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

        Scorer.addErrors(Scorer.checkIndentation(hm, tokens));

        Node AST = SynParser.parsing(tokens);

        Scorer.addErrors(Scorer.notMore200Lines(hm));
        Scorer.addErrors(Scorer.varNameConventions(AST));
        Scorer.addErrors(Scorer.checkUsedDecl(AST));
        Scorer.addErrors(Scorer.findOperationWithoutStore(AST));
        Scorer.addErrors(Scorer.checkLineLength(Lexer.getHashMap_LineLength()));

        for(String error : Scorer.getAllErrors()){
            System.out.println(error);
        }

        System.out.println(Scorer.getNote());
    }
}