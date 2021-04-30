package Scoring;

import Tools.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Scorer {
    final static int TAB = 8;

    private static ArrayList<String> extractVarDeclarationsInList(Node ast){
        ArrayList<String> res = new ArrayList<>();

        if (ast instanceof DeclarationVariableNode){
            DeclarationVariableNode currentNode = (DeclarationVariableNode) ast;
            res.add(currentNode.getName());
        }

        for (Node n : ast.getChildren()){
            if (!(n instanceof VarNode)){
                res.addAll(extractVarDeclarationsInList(n));
            }
        }

        return res;
    }

    private static ArrayList<String> extractVarAffectationInList(Node ast){
        ArrayList<String> res = new ArrayList<String>();

        if (ast instanceof AffectationNode){
            AffectationNode currentNode = (AffectationNode) ast;
            res.add(currentNode.getName());
        }

        for (Node n : ast.getChildren()){
            if (!(n instanceof VarNode)){
                res.addAll(extractVarDeclarationsInList(n));
            }
        }

        return res;
    }

    public static ArrayList<String> findOperationWithoutStore(Node ast){
        ArrayList<String> res = new ArrayList<>();

        String operationNotStored = "This operation : %s %s %s is not stored after the calculation";
        if (ast instanceof OperationNode){
            if(!(ast.getParent() instanceof AffectationNode)){
                res.add(String.format(operationNotStored, ast.getChildren().get(0).toString(), ast.getType(), ast.getChildren().get(1)));
            }
        }

        for (Node n : ast.getChildren()){
            if (!(n instanceof VarNode)){
                res.addAll(findOperationWithoutStore(n));
            }
        }

        return res;
    }

    ArrayList<String> extractFunDeclarationsInList(Node ast){
        ArrayList<String> res = new ArrayList<>();

        if (ast instanceof DeclarationFunctionNode){
            DeclarationFunctionNode currentNode = (DeclarationFunctionNode) ast;
            res.add(currentNode.getName());
        }

        for (Node n : ast.getChildren()){
            res.addAll(extractVarDeclarationsInList(n));
        }

        return res;
    }

    public static ArrayList<String> checkLineLength(HashMap<Integer, Integer> lenghtLine){
        ArrayList<String> retErrors = new ArrayList<>();
        String tooLongLine = "This line : %d is too long (>80 characters)";
        for(Map.Entry<Integer, Integer> item : lenghtLine.entrySet()){
            if(lenghtLine.get(item.getKey()) > 80){
                retErrors.add(String.format(tooLongLine, item.getKey()));
            }
        }
        return retErrors;
    }

    public static ArrayList<String> varNameConventions(Node ast){
        ArrayList<String> retErrors = new ArrayList<>();

        ArrayList<String> varDeclList = extractVarDeclarationsInList(ast);

        String caseError = "Your variable %s is not in lower case and it's wrong !";
        String caseLenght = "This var %s is not long enough and can't be clear !";
        for (String decl: varDeclList){
            if (!decl.equals(decl.toLowerCase())){
                // Cas où la variable n'est pas en minuscule
                retErrors.add(String.format(caseError, decl));
            }

            if (decl.length() < 3){
                retErrors.add(String.format(caseLenght, decl));
            }
        }

        return retErrors;
    }

    public static ArrayList<String> checkUsedDecl(Node ast){
        ArrayList<String> varDeclList = extractVarDeclarationsInList(ast);
        ArrayList<String> varAffectList = extractVarAffectationInList(ast);
        ArrayList<String> retErrors = new ArrayList<String>();
        String varNotUsedOrInit = "This variable : %s is not initialized or not used at all !";
        for (String word: varDeclList){
            if (!varAffectList.contains(word)){
                retErrors.add(String.format(varNotUsedOrInit, word));
            }
        }
        return retErrors;
    }

    public static ArrayList<String> checkIndentation(HashMap<Integer, Integer> indentationIndex, ArrayList<LexicalToken> lexicalsTokens){
        ArrayList<String> retErrors = new ArrayList<>();

        String wrongIndentationLenght = "Your indendation line %d is not a multiple of %d it seem you used mix indentation style or weird tab length";
        for (int line = 1; line < indentationIndex.size(); line++){
            if (indentationIndex.get(line) % TAB != 0){
                retErrors.add(String.format(wrongIndentationLenght, line, TAB));
            }
        }

        // Generation du guide d'indentation
        HashMap<Integer, Integer> indentGuide = new HashMap<Integer, Integer>();
        int indentChar = 0;
        for(LexicalToken token : lexicalsTokens){
            //System.out.println(token.getType() + " " + token.getValue());
            if(token.getType().equals(lexicalType.Lb)){
                indentGuide.put(token.getLine(), indentChar);
                indentChar += TAB;
            }
            else if (token.getType().equals(lexicalType.Rb)){
                indentChar -= TAB;
                indentGuide.put(token.getLine(), indentChar);
            }
            else{
                indentGuide.put(token.getLine(), indentChar);
            }
        }

        String wrongIdent = "On the line %d you're indendation is bad, too much or not enough tab";
        for(Map.Entry<Integer, Integer> item : indentGuide.entrySet()){
            if(!indentationIndex.get(item.getKey()).equals(item.getValue())){
                retErrors.add(String.format(wrongIdent, item.getKey()));
            }
        }

        return retErrors;
    }

    public static ArrayList<String> notMore200Lines(HashMap<Integer, Integer> indentationIndex){
        ArrayList<String> retError = new ArrayList<>();
        if(indentationIndex.size() > 200){
            retError.add("Your file is too long ! More than 200 lines !");
        }

        return retError;
    }
}