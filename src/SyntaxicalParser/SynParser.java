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
        LexicalToken basicToken = new LexicalToken();
        pileContenuValue.addFlags("basicToken", basicToken);
        LexicalToken functionToken = new LexicalToken();
        pileContenuValue.addFlags("functionToken", functionToken);
        LexicalToken delimiterToken = new LexicalToken();
        pileContenuValue.addFlags("delimiterToken", delimiterToken);



        for (int i = 0; i < token.size(); i++) {
            // ( ) { }
            //Ici on empile !
            tokenVerification = token.get(i);
            //Tant que l'on ne rencontre pas de ";" on ajoute les token dans la pile
            if (tokenVerification.getType().equals(Lp) || tokenVerification.getType().equals(Lb)) {
                scope = pileContenuValue.newScope();
                pileContenuValue.pushReadStack(scope, tokenVerification);
                if (i < token.size() - 1) {
                    continue;
                }
            }
            else if (tokenVerification.getType().equals(Rp) || tokenVerification.getType().equals(Rb)) {
                pileContenuValue.pushReadStack(scope, tokenVerification);
                scope--;
                if (i < token.size()-1) {
                    continue;
                }
            } else {

                pileContenuValue.pushReadStack(scope, tokenVerification);
                if (i < token.size() - 1) {
                    continue;
                }
            }
            try {
                DeclarationFunctionNode nodeFunc = new DeclarationFunctionNode();
                BodyNode nodeBody = new BodyNode();
                for(int currentscope = 0; currentscope <= pileContenuValue.getDeepestScope();currentscope++){
                    unStack(AST, currentscope, pileContenuValue, nodeFunc, nodeBody);
                    pileContenuValue.clearReadStack(currentscope);
                }

            } catch (SyntaxError syntaxError) {
                syntaxError.printStackTrace();
            }




        }
        System.out.println(pileContenuValue.getScopMap());
        AST.printNode(0);
        return AST;
    }

    private static RootNode unStack(RootNode AST, int scope, SyntaxicStack pileContenuValue,
                                    DeclarationFunctionNode nodeFunc, BodyNode nodeBody) throws SyntaxError {
        boolean parameters = false;
        boolean variable = false;
        boolean body = false;

        //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?

        int currentScope = pileContenuValue.readStackSize(scope);


        for (int j = 0; j < currentScope; j++) {
            LexicalToken currentToken = pileContenuValue.popReadStack(scope, j);
            System.out.println(currentToken.getType() + " " + scope);
            //Ici on dépile !
            switch (currentToken.getType()) {
                case BasicType:
                    pileContenuValue.getFlag("basicToken").setToken(currentToken);
                    break;
                case Word:
                    if (BasicType.equals(pileContenuValue.getFlag("basicToken").getType())) {
                        DeclarationVariableNode nodeDec = new DeclarationVariableNode();
                        nodeDec.setName(currentToken.getValue());
                        nodeDec.setType(pileContenuValue.getFlag("basicToken").getValue());
                        if(parameters){
                            variable = true;
                            nodeFunc.addParams(nodeDec);
                        }else if(body){
                            nodeBody.addChild(nodeDec);
                        }else{
                            AST.addChild(nodeDec);
                        }

                        pileContenuValue.newVarDeclToken(scope, currentToken.getValue());
                    }
                    break;
                case Function:
                    pileContenuValue.getFlag("functionToken").setToken(currentToken);

                    break;
                case Lp:
                    //si le token actuel est une parenthèse et que celui qui précède est une fonction
                    if(Function.equals(pileContenuValue.getFlag("functionToken").getType())){
                        parameters = true;
                        nodeFunc.setName(pileContenuValue.getFlag("functionToken").getValue());
                        nodeFunc.setType(pileContenuValue.getFlag("basicToken").getValue());
                        nodeFunc.setScope(scope-1);
                    }

                    break;
                case virgule:
                    if(parameters){
                        if(variable){
                            variable = false;
                            break;
                        }
                        else{
                            throw new SyntaxError(currentToken.getValue());
                        }
                    }
                    break;
                case Rp:
                    if(parameters){
                        parameters = false;
                        pileContenuValue.addFlags("delimiterToken",currentToken);

                    }

                    break;
                case Lb:
                    if(Rp.equals(pileContenuValue.getFlag("delimiterToken").getType())){
                        body = true;
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    break;
                case Rb:
                    if(body){
                        nodeFunc.setBody(nodeBody);
                        AST.addChild(nodeFunc);
                        pileContenuValue.newFunDeclToken(nodeFunc.getScope(), nodeFunc.getName());
                        body = false;
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    break;
                /*case Keyword:
                    if(body){

                        nodeBody.addIteration();
                    }*/
            }
        }
        return AST;
    }
}