package SyntaxicalParser;
import java.util.*;

public class SyntaxicStack {
    private HashMap scopMap;
    private int currentScope;

    public HashMap getScopMap() {
        return scopMap;
    }

    public void setScopMap(HashMap scopMap) {
        this.scopMap = scopMap;
    }

    public int getCurrentScope() {
        return currentScope;
    }

    public void setCurrentScope(int currentScope) {
        this.currentScope = currentScope;
    }

    public SyntaxicStack(int currentScope) {
        this.currentScope = currentScope;

        HashMap currentLists = new HashMap();

        ArrayList<String> varDeclList = new ArrayList<String>();
        currentLists.put("declaredVar", varDeclList);

        ArrayList<String> funDeclList = new ArrayList<String>();
        currentLists.put("declaredFun", funDeclList);

        ArrayList<String> readList = new ArrayList<String>();
        currentLists.put("readStack", readList);

        this.scopMap = new HashMap();

        this.scopMap.put(currentScope, currentLists);
    }

    // Ajoute un nvx token de lecture
    public int newReadToken(int scope){
        return scope;
    }

    // Ajoute une nouvelle déclaration de variable
    public int newVarDeclToken(int scope){
        return scope;
    }

    // Ajoute une nouvelle déclaration de fonction
    public int newFunDeclToken(int scope){
        return scope;
    }


}
