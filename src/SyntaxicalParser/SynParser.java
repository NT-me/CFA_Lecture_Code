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
            if (tokenVerification.getType().equals(Lp) || tokenVerification.getType().equals(Lb)) {
                scope = pileContenuValue.newScope();
            }
            if (tokenVerification.getType().equals(Rp) || tokenVerification.getType().equals(Rb)) {
                scope--;
            }
            pileContenuValue.pushReadStack(scope, tokenVerification);
            if (i < token.size()-1) {
                continue;
            }

            try {
                unStack(AST, scope, pileContenuValue);
            } catch (SyntaxError syntaxError) {
                syntaxError.printStackTrace();
            }
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
        try {
            unStack(AST, scope, pileContenuValue);
        } catch (SyntaxError syntaxError) {
            syntaxError.printStackTrace();
        }
        System.out.println(pileContenuValue.getScopMap());
        AST.printNode(0);
        return AST;
    }

    private static RootNode unStack(RootNode AST, int scope, SyntaxicStack pileContenuValue) throws SyntaxError {
        boolean parameters = false;
        boolean variable = false;
        boolean body = false;

        //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?
        DeclarationFunctionNode nodeFunc = new DeclarationFunctionNode();
        BodyNode nodeBody = new BodyNode();

        LexicalToken basicToken = new LexicalToken();
        LexicalToken functionToken = new LexicalToken();
        LexicalToken delimiterToken = new LexicalToken();

        for (int j = 0; j < pileContenuValue.readStackSize(scope); j++) {
            LexicalToken currentToken = pileContenuValue.popReadStack(scope, j);
            //Ici on dépile !
            switch (currentToken.getType()) {
                case BasicType:
                    basicToken.setToken(currentToken);
                    break;
                case Word:
                    if (BasicType.equals(basicToken.getType())) {
                        DeclarationVariableNode nodeDec = new DeclarationVariableNode();
                        nodeDec.setName(currentToken.getValue());
                        nodeDec.setType(basicToken.getValue());
                        if(parameters){
                            variable = true;
                            nodeFunc.addParams(nodeDec);
                        }else{
                            AST.addChild(nodeDec);
                        }
                        if(body){
                            nodeBody.addIteration(nodeDec);
                        }
                        pileContenuValue.newVarDeclToken(scope, currentToken.getValue());
                    }
                    break;
                case Function:
                    functionToken.setToken(currentToken);
                    break;
                case Lp:
                    //si le token actuel est une parenthèse et que celui qui précède est une fonction
                    if(functionToken.getType() != null && Function.equals(functionToken.getType())){
                        scope = pileContenuValue.newScope();
                        parameters = true;
                        nodeFunc.setName(currentToken.getValue());
                        nodeFunc.setType(basicToken.getValue());
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
                        scope--;
                        delimiterToken = currentToken;
                    }
                    break;
                case Lb:
                    if(Rp.equals(delimiterToken.getType())){
                        scope = pileContenuValue.newScope();
                        body = true;
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    break;
                case Rb:
                    AST.addChild(nodeFunc);
            }
        }
        return AST;
    }
}