package SyntaxicalParser;

import Tools.*;

import java.util.ArrayList;

import static Tools.lexicalType.*;

class SyntaxError extends Exception {
    SyntaxError(String msg){
        super("An syntax error has been discovered by your favorite pre-compile and scoring tools: "+msg);
    }
}



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
            if(!EoI.equals(tokenVerification.getType())){
                pileContenuValue.add(tokenVerification);
                continue;
            }

            //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?
            for (int j = 0; j<pileContenuValue.size();j++){
                if(pileContenuValue.get(j).getType() == BasicType){
                    //declarer une variable
                    if(Var.equals(pileContenuValue.get(j+1).getType())){
                        DeclarationVariableNode nodeDec = new DeclarationVariableNode();
                        nodeDec.setName(pileContenuValue.get(j+1).getValue());
                        nodeDec.setType(pileContenuValue.get(j).getValue());
                        AST.addChild((Node) nodeDec);
                    }

                    else if(Function.equals(pileContenuValue.get(j+1).getType())
                            && Lp.equals(pileContenuValue.get(j+2))){

                        DeclarationFunctionNode nodeFunc = new DeclarationFunctionNode();
                        nodeFunc.setName(pileContenuValue.get(j+1).getValue());
                        nodeFunc.setType(pileContenuValue.get(j).getValue());
                        int k = 0;
                        for(k = j; k < pileContenuValue.size(); k++){
                            if (")".equals(pileContenuValue.get(k))){
                                break;
                            }
                            nodeFunc.addParams(pileContenuValue.get(k).getValue());
                        }
                        if ("{".equals(pileContenuValue.get(k))) {
                            for (int l = k;l<pileContenuValue.size();l++){

                            }
                        }
                        AST.addChild((Node) nodeFunc);
                    }
                    else{
                        // Try cath sans doute en trop mais osef un peu
                        String msg = " Strange thing after an type";
                        SyntaxError exName = new SyntaxError(msg);
                        try {
                            throw exName;
                        } catch (SyntaxError syntaxError) {
                            syntaxError.printStackTrace();
                        }
                    }
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

