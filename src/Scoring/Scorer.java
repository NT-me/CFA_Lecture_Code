package Scoring;

import Tools.DeclarationNode;
import Tools.Node;
import Tools.SyntaxiqueType;

import java.util.ArrayList;
import java.util.Locale;

public class Scorer {

    private static ArrayList<String> extractVarDeclarationsInList(Node ast){
        ArrayList<String> res = new ArrayList<String>();

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

    public static ArrayList<String> varNameConventions(Node ast){
        ArrayList<String> retErrors = new ArrayList<String>();

        ArrayList<String> varDeclList = extractVarDeclarationsInList(ast);

        for (String decl: varDeclList){
            if (!decl.equals(decl.toLowerCase())){
                // Cas où la variable n'est pas en minuscule
                retErrors.add(decl);
            }
        }

        return retErrors;
    }

/*    ArrayList<String> checkUsedDecl(Node asr){

    }*/
}