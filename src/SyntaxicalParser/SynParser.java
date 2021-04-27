package SyntaxicalParser;

import Tools.*;

import java.util.ArrayList;

import static Tools.SyntaxiqueType.*;
import static Tools.lexicalType.*;

public class SynParser {
    public static Node parsing(ArrayList<LexicalToken> token){
        RootNode AST = new RootNode();
        LexicalToken tokenVerification;
       /* ContenuToken contenuToken = new ContenuToken();
        //exemple: int a;
        SyntaxiqueToken nodeToken = new SyntaxiqueToken(null, "", null);*/
        ArrayList<LexicalToken> pileContenuValue = new ArrayList<>();
        LexicalToken lexicalValue = new LexicalToken();
        Node nodeToSetup;
        for(int i = 0;i< token.size();i++){

            tokenVerification = token.get(i);
            //Tant que l'on ne rencontre pas de ";" on ajoute les token dans la pile
            if(!endInstruction.equals(tokenVerification.getType())){
                pileContenuValue.add(tokenVerification);
                continue;
            }
            //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?
            for (int j = 0; j<pileContenuValue.size();j++){
                if(pileContenuValue.get(j).getType() == BasicType){
                    //declarer une variable
                    if(variable.equals(pileContenuValue.get(j+1).getType())){
                        DeclarationNode nodeDec = new DeclarationNode();
                        nodeDec.setName(pileContenuValue.get(j+1).getValue());
                        nodeDec.setType(pileContenuValue.get(j).getValue());
                        AST.addChild((Node) nodeDec);
                    }



                    /*else if(function.equals(pileContenuValue.get(j+1).getType())){
                        nodeToken.setType(declarationFunction);
                        nodeToken.setName(pileContenuValue.get(j+1).getValue());
                        AST.newChild(nodeToken);
                    }  *//*else{
                        //throw ();
                    }*/
                }
            }
            pileContenuValue.clear();


                //affecter une variable
                /*if("=".equals(pileContenuValue.get(2).getValue())){
                    nodeToken.setType(affectationVariable);
                    nodeToken.setName(pileContenuValue.get(1).getValue());
                    //contenuToken.setType
                    contenuToken.setValue(pileContenuValue.get(3).getValue());
                    nodeToSetup = new Node(nodeToken);
                    AST.add(nodeToSetup);
                }*/

        }
        AST.printNode(0);
        return AST;
    }
}

