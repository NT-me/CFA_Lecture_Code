import Tools.LexicalToken;

import java.util.ArrayList;
import static Tools.lexicalType.*;
import SyntaxicalParser.SynParser;
import Tools.LexicalToken.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<LexicalToken> phrase = new ArrayList<>();
        LexicalToken token = new LexicalToken(BasicType,"int");
        LexicalToken token2 = new LexicalToken(variable,"a");
        LexicalToken token3 = new LexicalToken(endInstruction,";");


        phrase.add(token);
        phrase.add(token2);
        phrase.add(token3);


        SynParser.parsing(phrase);
    }

}
