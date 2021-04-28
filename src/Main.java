import Tools.LexicalToken;

import java.util.ArrayList;

import static Tools.lexicalType.*;
import SyntaxicalParser.SynParser;

public class Main {

    public static void main(String[] args) {
        ArrayList<LexicalToken> phrase = new ArrayList<>();
        LexicalToken token = new LexicalToken(BasicType,"int");
        LexicalToken token2 = new LexicalToken(Word,"a");
        LexicalToken token3 = new LexicalToken(EoI,";");

        LexicalToken token4 = new LexicalToken(BasicType,"char");
        LexicalToken token5 = new LexicalToken(Word,"b");
        LexicalToken token6 = new LexicalToken(EoI,";");

        LexicalToken token7 = new LexicalToken(BasicType,"bool");
        LexicalToken token8 = new LexicalToken(Word,"c");
        LexicalToken token9 = new LexicalToken(EoI,";");

        LexicalToken token10 = new LexicalToken(BasicType,"int");
        LexicalToken token11 = new LexicalToken(Function,"main");
        LexicalToken token12 = new LexicalToken(Lp,"(");
        LexicalToken token13 = new LexicalToken(BasicType,"int");
        LexicalToken token14 = new LexicalToken(Word,"a");
        LexicalToken token15 = new LexicalToken(virgule,",");
        LexicalToken token16 = new LexicalToken(BasicType,"int");
        LexicalToken token17 = new LexicalToken(Word,"b");
        LexicalToken token18 = new LexicalToken(Rp,")");
        LexicalToken token19 = new LexicalToken(Lb,"{");
        LexicalToken token20 = new LexicalToken(Keyword,"return");
        LexicalToken token21 = new LexicalToken(Word,"a");
        LexicalToken token22 = new LexicalToken(Ope,"+");
        LexicalToken token23 = new LexicalToken(Word,"b");
        LexicalToken token24 = new LexicalToken(EoI,";");
        LexicalToken token25 = new LexicalToken(Rb,"}");


    /*    phrase.add(token);
        phrase.add(token2);
        phrase.add(token3);

        phrase.add(token4);
        phrase.add(token5);
        phrase.add(token6);

        phrase.add(token7);
        phrase.add(token8);
        phrase.add(token9);*/

        phrase.add(token10);
        phrase.add(token11);
        phrase.add(token12);
        phrase.add(token13);
        phrase.add(token14);
        phrase.add(token15);
        phrase.add(token16);
        phrase.add(token17);
        phrase.add(token18);
        phrase.add(token19);
        phrase.add(token20);
        phrase.add(token21);
        phrase.add(token22);
        phrase.add(token23);
        phrase.add(token24);
        phrase.add(token25);

        SynParser.parsing(phrase);
    }

}
