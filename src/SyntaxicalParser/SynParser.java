package SyntaxicalParser;

import Tools.*;

import java.util.ArrayList;

import static Tools.lexicalType.*;

//Si le cas n'est pas possible dans le langage, on renvoie cette erreur
class SyntaxError extends Exception {
    SyntaxError(String msg) {
        super("An syntax error has been discovered by your favorite pre-compile and scoring tools: " + msg);
    }
}

public class SynParser {
    public static Node parsing(ArrayList<LexicalToken> token) {
        //Declaration et instanciation des tokens, nodes et variables
        RootNode AST = new RootNode();
        LexicalToken tokenVerification;
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
            //Ici on empile, dans notre SyntaxicStack dans le scope correspondant, qui change si l'on rencontre des parentheses(Lp/Rp) ou accolades(Lb/Rb)
            tokenVerification = token.get(i);
            if (tokenVerification.getType().equals(Lp) || tokenVerification.getType().equals(Lb)) {
                scope = pileContenuValue.newScope();
                pileContenuValue.pushReadStack(scope, tokenVerification);
                if (i < token.size() - 1) {
                    continue;
                }
            }
            else if (tokenVerification.getType().equals(Rp) || tokenVerification.getType().equals(Rb)) {
                pileContenuValue.pushReadStack(scope, tokenVerification);
                scope = tokenVerification.getType().equals(Rp) ? scope - 1 : scope - 2;
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
                //Si tout va bien une fois l'empilation terminé, on va depiler chaque scope
                DeclarationFunctionNode nodeFunc = new DeclarationFunctionNode();
                BodyNode nodeBody = new BodyNode();
                NumberNode nodeNumber = new NumberNode();
                for(int currentscope = 0; currentscope <= pileContenuValue.getDeepestScope();currentscope++){
                    unStack(AST, currentscope, pileContenuValue, nodeFunc, nodeBody, nodeNumber);
                    pileContenuValue.clearReadStack(currentscope);
                }

            } catch (SyntaxError syntaxError) {
                syntaxError.printStackTrace();
            }




        }
        //Si vous voulez avoir un leger aperçu de l'AST avec un petit affichage, vous pouvez decommenter la ligne suivante
        AST.printNode(0);
        return AST;
    }

    //Voici la fonction qui va permettre la depilation, qui prend l'AST, le scope actuel, la syntaxicStack et differents noeuds tampons
    private static RootNode unStack(RootNode AST, int scope, SyntaxicStack pileContenuValue, DeclarationFunctionNode nodeFunc,
                                    BodyNode nodeBody, NumberNode nodeNumber) throws SyntaxError {

        //Ici nous créons des booleans pour savoir dans quel statut nous sommes pour que les elements suivants savent où ils sont
        boolean isParameters = false;
        boolean isVariable = false;
        boolean isBody = false;
        boolean isReturn = false;
        boolean isAddition = false;
        boolean isAffectation = false;

        AffectationNode affecNode = new AffectationNode();
        OperationNode opeNode = new OperationNode();
        //Si l'instruction commence par un type, on declare une variable ou fonction, sinon erreur ?

        int currentScope = pileContenuValue.readStackSize(scope);


        for (int j = 0; j < currentScope; j++) {
            //On recupere le premier LexicalToken de la pile, et on regarde son type dans ce grand switch/case
            //On recupere la grande majorité du temps le precedent token, car beaucoup sont liés
            LexicalToken currentToken = pileContenuValue.popReadStack(scope, j);
            switch (currentToken.getType()) {
                //On sauvegarde ce type pour la prochaine declaration de fonction/variable
                case BasicType:
                    pileContenuValue.getFlag("basicToken").setToken(currentToken);
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                    //N'importe quel mot, souvent une variable pour nous qui sera:
                case Word:
                    //Ici declaree
                    if (BasicType.equals(pileContenuValue.getFlag("basicToken").getType())) {
                        DeclarationVariableNode nodeDec = new DeclarationVariableNode();
                        nodeDec.setName(currentToken.getValue());
                        nodeDec.setType(pileContenuValue.getFlag("basicToken").getValue());

                        if(isParameters){
                            isVariable = true;
                            nodeFunc.addParams(nodeDec);
                        }else if(isBody){
                            nodeBody.addChild(nodeDec);
                        }else{
                            AST.addChild(nodeDec);
                        }
                        pileContenuValue.clearFlag("basicToken");
                        pileContenuValue.newVarDeclToken(scope, nodeDec);
                    }
                    //Ici on verifie le type de retour de la fonction
                    if (isReturn){
                        if(!pileContenuValue.checkTypeVar(nodeFunc.getType(),currentToken.getValue())){
                            throw new SyntaxError(currentToken.getValue());
                        }
                        isReturn = false;
                    }
                //Ici on cree/agremente une addition
                    if (isAddition){
                        if(Number.equals(pileContenuValue.getFlag("previousToken").getType()) ||
                                Word.equals(pileContenuValue.getFlag("previousToken").getType())){
                            //VarNode number = new VarNode(currentToken.getValue());
                            VarNode var = new VarNode(currentToken.getType().toString(),
                                    currentToken.getValue());
                            opeNode.addChild(var);
                        }
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //On declare une fonction, donc on initialise nos flags
                case Function:
                    pileContenuValue.getFlag("functionToken").setToken(currentToken);
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Parenthese ouvrante, constitue une declaration de fonction avec potentiellement parametres
                case Lp:
                    if(Function.equals(pileContenuValue.getFlag("functionToken").getType())){
                        isParameters = true;
                        nodeFunc.setName(pileContenuValue.getFlag("functionToken").getValue());
                        nodeFunc.setType(pileContenuValue.getFlag("basicToken").getValue());
                        nodeFunc.setScope(scope-1);
                        pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    }

                    break;
                //Une virgule dans les parametres precise que plusieurs variables sont instanciees
                case Coma:
                    if(isParameters){
                        if(isVariable){
                            isVariable = false;
                            pileContenuValue.getFlag("previousToken").setToken(currentToken);
                            break;
                        }
                        else{
                            throw new SyntaxError(currentToken.getValue());
                        }
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Parenthese fermante, precise que les parametres de la fonctions sont fini et que nous allons passer a la suite: le body
                case Rp:
                    if(isParameters){
                        isParameters = false;
                        pileContenuValue.addFlags("previousToken",currentToken);

                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Accolade ouvrante ouvre la voie au body de la fonction
                case Lb:
                    if(Rp.equals(pileContenuValue.getFlag("previousToken").getType())){
                        isBody = true;
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Accolade fermante termine le remplissage du body et fin de la declaration de fonction, qui est donc mis dans l'AST
                case Rb:
                    if(isBody){
                        nodeFunc.setBody(nodeBody);
                        AST.addChild(nodeFunc);
                        pileContenuValue.newFunDeclToken(nodeFunc.getScope(), nodeFunc);
                        isBody = false;
                        pileContenuValue.clearFlag("functionToken");
                    }
                    else{
                        throw new SyntaxError(currentToken.getValue());
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Les operateurs, ici = pour l'affectation de variable et + pour les operations de variables et nombres
                case Ope:
                    switch (currentToken.getValue()){
                        case "=":
                            if(Word.equals(pileContenuValue.getFlag("previousToken").getType())){
                                affecNode.setName(pileContenuValue.getFlag("previousToken").getValue());
                                isAffectation = true;
                                break;
                            }
                            pileContenuValue.getFlag("previousToken").setToken(currentToken);
                            break;
                        case "+":
                            isAddition = true;
                            //Si nous avons un nombre ou une variable, la node en question sera utilise/instancie
                            switch (pileContenuValue.getFlag("previousToken").getType()){
                                case Word:
                                    ArrayList<DeclarationVariableNode> varDeclNodeWithThisName =
                                            pileContenuValue.getDeclarationVariableNodesFromName(
                                                    pileContenuValue.getFlag("previousToken").getValue()
                                            );
                                    if (varDeclNodeWithThisName.size()>0){
                                        VarNode var = new VarNode(pileContenuValue.getFlag("previousToken").getType().toString(),
                                                pileContenuValue.getFlag("previousToken").getValue());
                                        opeNode.setType("+");
                                        opeNode.addChild(var);
                                    }
                                    else{
                                        throw new SyntaxError(currentToken.getValue());
                                    }

                                    break;

                                case Number:
                                    opeNode.setType("+");
                                    opeNode.addChild(nodeNumber);
                                    nodeNumber = new NumberNode();
                                    break;

                                default:
                                    throw new SyntaxError(currentToken.getValue());

                            }
                    }
                    break;
                //le ; precise la fin d'une instruction, et va reinitialise les booleans  et ajouter les nodes correspondantes au bons endroits suivant le statut present
                case EoI:
                    if(isAddition && isAffectation){
                        affecNode.addChild(opeNode);
                        if(isBody){
                            nodeBody.addChild(affecNode);
                        }else{
                            AST.addChild(affecNode);
                        }
                    }
                    if(isAddition && !isAffectation){
                        if(isBody){
                            if(nodeNumber.getValue() != ""){
                                opeNode.addChild(nodeNumber);
                            }
                            nodeBody.addChild(opeNode);
                        }else{
                            AST.addChild(opeNode);
                        }
                    }
                    if (!isAddition && isAffectation){
                        if(isBody){
                            affecNode.addChild(nodeNumber);
                            nodeBody.addChild(affecNode);
                        }else{
                            AST.addChild(affecNode);
                        }
                    }

                    isAddition = false;
                    isAffectation = false;
                    affecNode = new AffectationNode();
                    opeNode = new OperationNode();
                    nodeNumber = new NumberNode();
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Un nombre est utilise pour les affectations et operations
                case Number:
                    nodeNumber.setValue(currentToken.getValue());
                    if (isAffectation && isAddition){
                            opeNode.addChild(nodeNumber);
                    }
                    pileContenuValue.getFlag("previousToken").setToken(currentToken);
                    break;
                //Pour nous, le seul Keyword est return, et on va initialiser ce qu'il faut pour voir si le prochain token a le bon type
                case Keyword:
                    switch (currentToken.getValue()){
                        case "return":
                            if(!isBody){
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