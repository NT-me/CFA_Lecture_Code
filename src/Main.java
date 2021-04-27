import Tools.LexicalToken;

import java.util.ArrayList;

import static Tools.lexicalType.*;
import SyntaxicalParser.SynParser;

public class Main {

    public static void main(String[] args) {
        ArrayList<LexicalToken> phrase = new ArrayList<>();
        LexicalToken token = new LexicalToken(BasicType,"int");
        LexicalToken token2 = new LexicalToken(Var,"a");
        LexicalToken token3 = new LexicalToken(EoI,";");

        LexicalToken token4 = new LexicalToken(BasicType,"char");
        LexicalToken token5 = new LexicalToken(Var,"b");
        LexicalToken token6 = new LexicalToken(EoI,";");

        LexicalToken token7 = new LexicalToken(BasicType,"bool");
        LexicalToken token8 = new LexicalToken(Var,"c");
        LexicalToken token9 = new LexicalToken(EoI,";");

        phrase.add(token);
        phrase.add(token2);
        phrase.add(token3);

        phrase.add(token4);
        phrase.add(token5);
        phrase.add(token6);

        phrase.add(token7);
        phrase.add(token8);
        phrase.add(token9);


        SynParser.parsing(phrase);
    }

}
