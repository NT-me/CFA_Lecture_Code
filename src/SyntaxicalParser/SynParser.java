package SyntaxicalParser;

import Tools.*;

import java.util.ArrayList;

import static Tools.SyntaxiqueType.affectationVariable;
import static Tools.SyntaxiqueType.declarationVariable;
import static Tools.lexicalType.KeywordType;
import static Tools.lexicalType.endInstruction;

public class SynParser {
    //ArrayList<Node> token = new ArrayList<Node>();
    public ArrayList<Node> parsing(ArrayList<LexicalToken> token){
        ArrayList<Node> AST = new ArrayList<>();
        LexicalToken tokenVerification;
        //exemple: int a;
        SyntaxiqueToken nodeToken = new SyntaxiqueToken(null, "", null);
        ArrayList<LexicalToken> pileContenuValue = new ArrayList<>();
        LexicalToken lexicalValue = new LexicalToken();
        Node nodeToSetup = new Node();
        for(int i = 0;i<= token.size();i++){
            tokenVerification = token.remove(0);
            //Tant que l'on ne rencontre pas de ";" on ajoute les token dans la pile
            if(!endInstruction.equals(tokenVerification.getType())){
                pileContenuValue.add(tokenVerification);
                continue;
            }
            //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?
            if(pileContenuValue.get(0).getType() == KeywordType){
                //declarer une variable
                if(!"(".equals(pileContenuValue.get(2).getValue())){
                    nodeToken.setType(declarationVariable);
                    nodeToken.setName(pileContenuValue.get(1).getValue());
                    nodeToSetup = new Node(nodeToken);
                }
                //affecter une variable
                if("=".equals(pileContenuValue.get(2).getValue())){
                    nodeToken.setType(affectationVariable);
                    nodeToken.setName(pileContenuValue.get(1).getValue());
                    nodeToSetup = new Node(nodeToken);
                }
            }
            /*
            //on test si le mot est un int
            if("int".equals(tokenVerification.getValue())){
                //on ajoute dans la pile le int
                pileContenuValue.add(tokenVerification.getValue());
                continue;
            }
            //on test si le mot est un word
            if("word".equals(tokenVerification.getType())){
                String myValue = pileContenuValue.remove(0);
                //si le mot present dans la pile est un int, on cree une node qui declare une variable
                if("int".equals(myValue)){
                    nodeToken.setType(declarationVariable);
                    nodeToken.setName(tokenVerification.getValue());
                    nodeToSetup = new Node(nodeToken);
                }
            }
*/


            AST.add(nodeToSetup);
        }
        return AST;
    }
}

