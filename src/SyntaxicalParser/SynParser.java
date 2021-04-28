package SyntaxicalParser;

import Tools.*;

import java.util.ArrayList;

import static Tools.lexicalType.*;

class SyntaxError extends Exception {
    SyntaxError(String msg) {
        super("An syntax error has been discovered by your favorite pre-compile and scoring tools: " + msg);
    }
}


public class SynParser {
    public static Node parsing(ArrayList<LexicalToken> token) {
        RootNode AST = new RootNode();
        LexicalToken tokenVerification;
        /* ContenuToken contenuToken = new ContenuToken();
        //exemple: int a;
        SyntaxiqueToken nodeToken = new SyntaxiqueToken(null, "", null);*/
        //ArrayList<LexicalToken> pileContenuValue = new ArrayList<>();
        LexicalToken lexicalValue = new LexicalToken();
        Node nodeToSetup;
        int scope = 0;
        SyntaxicStack pileContenuValue = new SyntaxicStack(0);


        for (int i = 0; i < token.size(); i++) {
            // ( ) { }
            //Ici on empile !
            tokenVerification = token.get(i);
            //Tant que l'on ne rencontre pas de ";" on ajoute les token dans la pile
            if(tokenVerification.getType().equals(Lp) || tokenVerification.getType().equals(Lb)){
                scope = pileContenuValue.newScope();
            }
            if(tokenVerification.getType().equals(Rp) || tokenVerification.getType().equals(Rb)){
                scope = pileContenuValue.newScope();
            }
            if (!Rp.equals(tokenVerification.getType())
                    && !Rb.equals(tokenVerification.getType())) {
                pileContenuValue.pushReadStack(scope, tokenVerification);
                scope--;
                continue;
            }

            unStack(AST, scope, pileContenuValue);
            pileContenuValue.clearReadStack(scope);


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
        unStack(AST, scope, pileContenuValue);
        System.out.println(pileContenuValue.getScopMap());
        AST.printNode(0);
        return AST;
    }

    private static RootNode unStack(RootNode AST, int scope, SyntaxicStack pileContenuValue) {
        //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?
        for (int j = 0; j < pileContenuValue.readStackSize(scope); j++) {
            //Ici on dépile !
            if (pileContenuValue.popReadStack(scope, j).getType() == BasicType) {
                //declarer une variable
                System.out.println(pileContenuValue.popReadStack(scope, j).getValue());
                if (Var.equals(pileContenuValue.popReadStack(scope, j + 1).getType())) {
                    if (EoI.equals(pileContenuValue.popReadStack(scope, j + 2).getType()) ||
                            Ope.equals(pileContenuValue.popReadStack(scope, j + 2).getType())) {
                        DeclarationVariableNode nodeDec = new DeclarationVariableNode();
                        nodeDec.setName(pileContenuValue.popReadStack(scope, j + 1).getValue());
                        nodeDec.setType(pileContenuValue.popReadStack(scope, j).getValue());
                        AST.addChild((Node) nodeDec);
                        pileContenuValue.newVarDeclToken(scope, pileContenuValue.popReadStack(scope, j + 1).getValue());
                    }
                    //Déclarer une fonction
                } else if (Function.equals(pileContenuValue.popReadStack(scope, j + 1).getType())
                        && Lp.equals(pileContenuValue.popReadStack(scope, j + 1).getType())) {
                    scope = pileContenuValue.newScope();
                    DeclarationFunctionNode nodeFunc = new DeclarationFunctionNode();
                    nodeFunc.setName(pileContenuValue.popReadStack(scope, j + 1).getValue());
                    nodeFunc.setType(pileContenuValue.popReadStack(scope, j).getValue());
                    for (int k = j; k < pileContenuValue.readStackSize(scope); k++) {
                        if (")".equals(pileContenuValue.popReadStack(scope, k))) {
                            break;
                        }
                        if(pileContenuValue.popReadStack(scope, j).getType() == BasicType){
                            DeclarationVariableNode argument = new DeclarationVariableNode();
                            argument.setName(pileContenuValue.popReadStack(scope,k+1).getValue());
                            argument.setType(pileContenuValue.popReadStack(scope,k).getValue());
                            nodeFunc.addParams(argument);
                            System.out.println(argument);
                        }
                    }

                    AST.addChild((Node) nodeFunc);
                } else {
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
        return AST;
    }
}