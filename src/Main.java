import Scoring.Scorer;
import Tools.LexicalToken;

import java.util.ArrayList;
import java.util.List;

import static Tools.lexicalType.*;
import SyntaxicalParser.SynParser;
import Tools.Node;
import Tools.LexicalToken.*;
import Scoring.Scorer.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<LexicalToken> phrase = new ArrayList<>();
        LexicalToken token = new LexicalToken(BasicType,"int");
        LexicalToken token2 = new LexicalToken(variable,"AAh");
        LexicalToken token3 = new LexicalToken(endInstruction,";");

        LexicalToken token4 = new LexicalToken(BasicType,"char");
        LexicalToken token5 = new LexicalToken(variable,"b");
        LexicalToken token6 = new LexicalToken(endInstruction,";");

        LexicalToken token7 = new LexicalToken(BasicType,"bool");
        LexicalToken token8 = new LexicalToken(variable,"c");
        LexicalToken token9 = new LexicalToken(endInstruction,";");

        phrase.add(token);
        phrase.add(token2);
        phrase.add(token3);

        phrase.add(token4);
        phrase.add(token5);
        phrase.add(token6);

        phrase.add(token7);
        phrase.add(token8);
        phrase.add(token9);

        Node retAST = SynParser.parsing(phrase);
        ArrayList<String> scoringRet = Scorer.varNameConventions(retAST);

        System.out.println("\n\n====== Scoring ret : ======");
        System.out.println(scoringRet.size());
        for (String s : scoringRet){
            System.out.println(s);
        }
    }
}
