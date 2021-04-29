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
        LexicalToken previousToken = new LexicalToken();
        pileContenuValue.addFlags("previousToken", previousToken);
        LexicalToken operandeToken = new LexicalToken();
        pileContenuValue.addFlags("operandeToken", operandeToken);



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
        boolean isReturn = false;
        boolean addition = false;

        AffectationNode affecNode = new AffectationNode();
        OperationNode opeNode = new OperationNode();
        //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?

        int currentScope = pileContenuValue.readStackSize(scope);


        for (int j = 0; j < currentScope; j++) {
            LexicalToken currentToken = pileContenuValue.popReadStack(scope, j);
            System.out.println(currentToken.getType() + " " + scope);
            //Ici on dépile !
            switch (currentToken.getType()) {
                case BasicType:
                    pileContenuValue.getFlag("basicToken").setToken(currentToken);
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
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
                        pileContenuValue.clearFlag("basicToken");
                        pileContenuValue.newVarDeclToken(scope, nodeDec);
                    }
                    if (isReturn){
                        if(!pileContenuValue.checkTypeVar(currentToken.getValue())){
                            throw new SyntaxError(currentToken.getValue());
                        }
                        isReturn = false;
                    }
                    if (addition){
                        if(Number.equals(pileContenuValue.getFlag("previousToken")) ||
                                Word.equals(pileContenuValue.getFlag("previousToken"))){
                            NumberNode number = new NumberNode(currentToken.getValue());
                            opeNode.addChild(number);
                            if(body){
                                nodeBody.addChild(opeNode);
                            }else{
                                AST.addChild(opeNode);
                            }
                        }
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Function:
                    pileContenuValue.getFlag("functionToken").setToken(currentToken);
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Lp:
                    //si le token actuel est une parenthèse et que celui qui précède est une fonction
                    if(Function.equals(pileContenuValue.getFlag("functionToken").getType())){
                        parameters = true;
                        nodeFunc.setName(pileContenuValue.getFlag("functionToken").getValue());
                        nodeFunc.setType(pileContenuValue.getFlag("basicToken").getValue());
                        nodeFunc.setScope(scope-1);
                        pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    }

                    break;
                case virgule:
                    if(parameters){
                        if(variable){
                            variable = false;
                            pileContenuValue.getFlag("previousToken").setToken(currentToken);
                            break;
                        }
                        else{
                            throw new SyntaxError(currentToken.getValue());
                        }
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Rp:
                    if(parameters){
                        parameters = false;
                        pileContenuValue.addFlags("previousToken",currentToken);

                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Lb:
                    if(Rp.equals(pileContenuValue.getFlag("previousToken").getType())){
                        body = true;
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Rb:
                    if(body){
                        nodeFunc.setBody(nodeBody);
                        AST.addChild(nodeFunc);
                        pileContenuValue.newFunDeclToken(nodeFunc.getScope(), nodeFunc);
                        body = false;
                        pileContenuValue.clearFlag("functionToken");
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Ope:
                    switch (currentToken.getValue()){
                        case "=":
                            if(Word.equals(pileContenuValue.getFlag("previousToken").getType())){
                                affecNode.setName(pileContenuValue.getFlag("previousToken").getValue());
                            }
                            pileContenuValue.getFlag("previousToken").setToken(currentToken);
                            break;
                        case "+":
                            if(Word.equals(pileContenuValue.getFlag("previousToken").getType()) ||
                                    Number.equals(pileContenuValue.getFlag("previousToken").getType())) {
                                addition = true;
                                NumberNode number = new NumberNode(pileContenuValue.getFlag("previousToken").getValue());
                                opeNode.setType("+");
                                opeNode.addChild(number);
                                break;
                            }else{
                                throw new SyntaxError(currentToken.getValue());
                            }

                    }

                case EoI:
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Number:
                    if ("=".equals(pileContenuValue.getFlag("previousToken").getValue())){
                        NumberNode number = new NumberNode();
                        number.setValue(currentToken.getValue());
                        affecNode.addChild(number);
                        if(scope == 0){
                            AST.addChild(affecNode);
                        }
                        else{
                            nodeBody.addChild(affecNode);
                        }
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                case Keyword:
                    switch (currentToken.getValue()){
                        case "return":
                            if(!body){
                                throw new SyntaxError(currentToken.getValue());
                            }
                            isReturn = true;
                            pileContenuValue.getFlag("previousToken").setToken(currentToken);
                            break;
                    }

            }
        }
        return AST;
    }
}