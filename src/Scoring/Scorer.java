package Scoring;

import Tools.DeclarationNode;
import Tools.Node;
import Tools.SyntaxiqueType;

import java.util.ArrayList;
import java.util.Locale;

public class Scorer {

    /**
     * @param ast of type Node, Ast of the source file that contains the program
     * @return a list of type ArrayList<String>, all the names of all the variable contained on the program
     */
    private static ArrayList<String> extractVarDeclarationsInList(Node ast){
        ArrayList<String> res = new ArrayList<>();

        if (ast instanceof DeclarationNode){
            DeclarationNode currentNode = (DeclarationNode) ast;
            res.add(currentNode.getName());
        }

        for (Node n : ast.getChildren()){
            res.addAll(extractVarDeclarationsInList(n));
        }

        return res;
    }

/*    ArrayList<String> extractFunDeclarationsInList(Node ast){

    }*/

    /**
     *
     * @param ast of type Node
     * @return result
     */
    public static ArrayList<String> varNameConventions(Node ast){
        ArrayList<String> retErrors = new ArrayList<>();

        ArrayList<String> varDeclList = extractVarDeclarationsInList(ast);

        for (String decl: varDeclList){
            if (!decl.equals(decl.toLowerCase())){
                // Cas où la variable n'est pas en minuscule
                retErrors.add(decl);
            }
        }

        return retErrors;
    }

    public static ArrayList<String> varConstConventions(Node ast){
        ArrayList<String> retErrors = new ArrayList<>();

        ArrayList<String> varDeclList = extractVarDeclarationsInList(ast);

        for (String decl: varDeclList){
            if (!decl.equals(decl.toUpperCase())){
                // Cas où la variable (supposée un constante) n'est pas en Majuscule
                retErrors.add(decl);
            }
        }

        return retErrors;
    }

/*    ArrayList<String> checkUsedDecl(Node asr){

    }*/
}