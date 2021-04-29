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

        LexicalToken token4 = new LexicalToken(Word,"a");
        LexicalToken token5 = new LexicalToken(Ope,"=");
        LexicalToken token6 = new LexicalToken(Number,"0");
        LexicalToken token7 = new LexicalToken(EoI,";");


        LexicalToken token10 = new LexicalToken(BasicType,"int");
        LexicalToken token11 = new LexicalToken(Function,"main");
        LexicalToken token12 = new LexicalToken(Lp,"(");
        LexicalToken token13 = new LexicalToken(BasicType,"int");
        LexicalToken token14 = new LexicalToken(Word,"a");
        LexicalToken token15 = new LexicalToken(Coma,",");
        LexicalToken token16 = new LexicalToken(BasicType,"int");
        LexicalToken token17 = new LexicalToken(Word,"b");
        LexicalToken token18 = new LexicalToken(Rp,")");
        LexicalToken token19 = new LexicalToken(Lb,"{");
        LexicalToken token20 = new LexicalToken(BasicType,"int");
        LexicalToken token21 = new LexicalToken(Word,"c");
        LexicalToken token22 = new LexicalToken(Ope,"=");
        LexicalToken token23 = new LexicalToken(Word,"a");
        LexicalToken token24 = new LexicalToken(Ope,"+");
        LexicalToken token25 = new LexicalToken(Word,"b");
        LexicalToken token26 = new LexicalToken(EoI,";");
        LexicalToken token27 = new LexicalToken(Keyword,"return");
        LexicalToken token28 = new LexicalToken(Word,"c");
        LexicalToken token29 = new LexicalToken(EoI,";");
        LexicalToken token30 = new LexicalToken(Rb,"}");


        /*phrase.add(token);
        phrase.add(token2);
        phrase.add(token3);

        phrase.add(token4);
        phrase.add(token5);
        phrase.add(token6);
        phrase.add(token7);*/

        /*phrase.add(token8);
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
        phrase.add(token26);
        phrase.add(token27);
        phrase.add(token28);
        phrase.add(token29);
        phrase.add(token30);


        SynParser.parsing(phrase);
    }

}
