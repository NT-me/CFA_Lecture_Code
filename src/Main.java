import Scoring.Scorer;
import Tools.LexicalToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Tools.lexicalType.*;
import SyntaxicalParser.SynParser;
import Tools.Node;
import Tools.LexicalToken.*;
import Scoring.Scorer.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<LexicalToken> phrase = new ArrayList<>();
        LexicalToken token = new LexicalToken(BasicType,"int",0);
        LexicalToken token2 = new LexicalToken(Word,"AAh",0);
        LexicalToken token3 = new LexicalToken(EoI,";",0);

        LexicalToken token4 = new LexicalToken(BasicType,"char",1);
        LexicalToken token5 = new LexicalToken(Word,"baba",1);
        LexicalToken token6 = new LexicalToken(EoI,";",1);

        LexicalToken token7 = new LexicalToken(BasicType,"bool",2);
        LexicalToken token8 = new LexicalToken(Word,"c",2);
        LexicalToken token9 = new LexicalToken(EoI,";",2);

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

        HashMap<Integer, Integer> indentIndex = new HashMap<Integer, Integer>();
        for(int i = 0; i<3; i++){
            indentIndex.put(i,0);
        }

        ArrayList<String> scoringRet = Scorer.varNameConventions(retAST);
        scoringRet.addAll(Scorer.checkIndentation(indentIndex, phrase));

        System.out.println("\n\n====== Scoring ret : ======");
        //System.out.println(scoringRet.size());
        for (String s : scoringRet){
            System.out.println(s);
        }
    }
}
