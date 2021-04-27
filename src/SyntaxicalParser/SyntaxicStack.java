package SyntaxicalParser;
import Tools.LexicalToken;

import java.util.*;

public class SyntaxicStack {
    private HashMap<Integer, HashMap> scopMap;
    private int deepestScope;

    public HashMap getScopMap() {
        return scopMap;
    }

    public void setScopMap(HashMap scopMap) {
        this.scopMap = scopMap;
    }

    public int getDeepestScope() {
        return deepestScope;
    }

    public void setDeepestScope(int deepestScope) {
        this.deepestScope = deepestScope;
    }

    public SyntaxicStack(int deepestScope) {
        this.deepestScope = deepestScope;

        HashMap<String, ArrayList<String>>  currentLists = new HashMap<String, ArrayList<String>> ();

        ArrayList<String> varDeclList = new ArrayList<String>();
        currentLists.put("declaredVar", varDeclList);

        ArrayList<String> funDeclList = new ArrayList<String>();
        currentLists.put("declaredFun", funDeclList);

        ArrayList<String> readList = new ArrayList<String>();
        currentLists.put("readStack", readList);

        this.scopMap = new HashMap<Integer, HashMap>();

        this.scopMap.put(deepestScope, currentLists);
    }

    // Ajoute un nvx token de lecture
    public int pushReadStack(int scope, LexicalToken token){
        ArrayList<LexicalToken> currentRS = (ArrayList<LexicalToken>)this.scopMap.get(scope).get("readStack");
        currentRS.add(token);

        return scope;
    }

    // Ajoute une nouvelle déclaration de variable
    public int newVarDeclToken(int scope, String token){
        ArrayList<String> currentRS = (ArrayList<String>)this.scopMap.get(scope).get("declaredVar");
        currentRS.add(token);
        return scope;
    }

    // Ajoute une nouvelle déclaration de fonction
    public int newFunDeclToken(int scope, String token){
        ArrayList<String> currentRS = (ArrayList<String>)this.scopMap.get(scope).get("declaredFun");
        currentRS.add(token);
        return scope;
    }

    public boolean checkIfFunIsDecl(String foo){
        ArrayList<String> allFunDecl = new ArrayList<String>();
        for(int i = 0; i<=this.deepestScope; i++){
            allFunDecl.addAll((ArrayList<String>)this.scopMap.get(i).get("declaredFun"));
        }

        return allFunDecl.contains(foo);
    }

    public boolean checkIfVarIsDecl(String var){
        ArrayList<String> allVarDecl = new ArrayList<String>();
        for(int i = 0; i<=this.deepestScope; i++){
            allVarDecl.addAll((ArrayList<String>)this.scopMap.get(i).get("declaredVar"));
        }

        return allVarDecl.contains(var);
    }

    public LexicalToken popReadStack(int scope, int i){

        return ((ArrayList<LexicalToken>)this.scopMap.get(scope).get("readStack")).get(i);
    }

    public int newScope(){
        int newScope = (this.deepestScope + 1);
        HashMap<String, ArrayList<String>>  currentLists = new HashMap<String, ArrayList<String>> ();

        ArrayList<String> varDeclList = new ArrayList<String>();
        currentLists.put("declaredVar", varDeclList);

        ArrayList<String> funDeclList = new ArrayList<String>();
        currentLists.put("declaredFun", funDeclList);

        ArrayList<String> readList = new ArrayList<String>();
        currentLists.put("readStack", readList);

        this.scopMap.put(newScope, currentLists);

        this.deepestScope = newScope;

        return newScope;
    }

    public int deleteScope(int scope){
        this.scopMap.remove(scope);
        this.deepestScope =- 1;
        return this.deepestScope;
    }

    public int readStackSize(int scope){
        return ((ArrayList<LexicalToken>)this.scopMap.get(scope).get("readStack")).size();
    }

    public int clearReadStack(int scope){
        ((ArrayList<LexicalToken>)this.scopMap.get(scope).get("readStack")).clear();
        return scope;
    }
}
