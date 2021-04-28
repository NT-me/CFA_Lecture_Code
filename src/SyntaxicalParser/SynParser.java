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

    private static RootNode unStack(RootNode AST, int scope, SyntaxicStack pileContenuValue) throws SyntaxError {
        boolean parameters = false;
        boolean variable = false;
        //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?
        DeclarationFunctionNode nodeFunc;
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
                    if (basicToken.getType().equals(BasicType)) {
                        DeclarationVariableNode nodeDec = new DeclarationVariableNode();
                        nodeDec.setName(currentToken.getValue());
                        nodeDec.setType(basicToken.getValue());
                        if(parameters){
                            variable = true;
                            nodeFunc.addParams(nodeDec);
                        }else{
                            AST.addChild((Node) nodeDec);
                        }
                        pileContenuValue.newVarDeclToken(scope, currentToken.getValue());
                    }
                    break;
                case Function:
                    functionToken.setToken(currentToken);
                    break;
                case Lp:
                    //si le token actuel est une parenthèse et que celui qui précède est une fonction
                    if(functionToken.getType() != null && functionToken.getType().equals(Function)){
                        scope = pileContenuValue.newScope();
                        parameters = true;
                        nodeFunc = new DeclarationFunctionNode();
                        nodeFunc.setName(currentToken.getValue());
                        nodeFunc.setType(basicToken.getValue());
                    }
                    break;
                case virgule:
                    if(parameters && variable){
                        variable = false;
                        break;
                    }else if (parameters && !variable){
                        throw new SyntaxError(currentToken.getValue());
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
                    if(delimiterToken.getType() == Rp){

                    }
            }
            if (currentToken.getType() == BasicType) {

                //declarer une variable
                System.out.println(pileContenuValue.popReadStack(scope, j).getValue());
                if (Word.equals(pileContenuValue.popReadStack(scope, j + 1).getType())) {
                    if (EoI.equals(pileContenuValue.popReadStack(scope, j + 2).getType()) ||
                            Ope.equals(pileContenuValue.popReadStack(scope, j + 2).getType())) {


                    }
                    //Déclarer une fonction
                } else if (Function.equals(pileContenuValue.popReadStack(scope, j + 1).getType())
                        && Lp.equals(pileContenuValue.popReadStack(scope, j + 1).getType())) {

                    for (int k = j; k < pileContenuValue.readStackSize(scope); k++) {
                        if (")".equals(pileContenuValue.popReadStack(scope, k))) {
                            break;
                        }
                        if (pileContenuValue.popReadStack(scope, j).getType() == BasicType) {
                            DeclarationVariableNode argument = new DeclarationVariableNode();
                            argument.setName(pileContenuValue.popReadStack(scope, k + 1).getValue());
                            argument.setType(pileContenuValue.popReadStack(scope, k).getValue());
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

    public void getFunction(LexicalToken tokenFunction) {
        tokenFunction
        DeclarationFunctionNode function =
                scope = tokenFunction.
        DeclarationFunctionNode nodeFunc = new DeclarationFunctionNode();
        nodeFunc.setName(token.getValue());
        nodeFunc.setType(value.getValue());
        //extraire les arguments/parametres
        ArrayList<DeclarationVariableNode> parameters = getParameters();
        //étudier le scope, I.E. tout ce qui est dans les accolades
        ArrayList<BodyNode> body = getBody();
        //vérifier la correspondance entre le type déclaré et le type retourné
    }

    private ArrayList<DeclarationVariableNode> getParameters(, int scope) {

        scope = pileContenuValue.newScope();

    }
}