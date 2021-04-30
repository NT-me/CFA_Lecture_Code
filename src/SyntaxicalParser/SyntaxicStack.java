package SyntaxicalParser;
import Tools.DeclarationFunctionNode;
import Tools.DeclarationVariableNode;
import Tools.LexicalToken;

import java.util.*;

public class SyntaxicStack {
    private HashMap<Integer, HashMap> scopMap;
    private int deepestScope;
    private HashMap<String, LexicalToken> flags;

    public HashMap getScopMap() {
        return scopMap;
    }

    /*public void setScopMap(HashMap scopMap) {
        this.scopMap = scopMap;
    }*/

    public int getDeepestScope() {
        return deepestScope;
    }

    public void setDeepestScope(int deepestScope) {
        this.deepestScope = deepestScope;
    }

    public void setScopMap(HashMap<Integer, HashMap> scopMap) {
        this.scopMap = scopMap;
    }

    public HashMap<String, LexicalToken> getFlags() {
        return flags;
    }

    public void setFlags(HashMap<String, LexicalToken> flags) {
        this.flags = flags;
    }

    public void addFlags(String name,LexicalToken flag){
        this.flags.put(name,flag);
    }

    public LexicalToken getFlag(String nameFlag){
        return this.flags.get(nameFlag);
    }

    public void clearFlag(String nameFlag){
        this.flags.put(nameFlag, new LexicalToken());
    }

    public SyntaxicStack(int deepestScope) {
        this.deepestScope = deepestScope;
        this.flags = new HashMap<String, LexicalToken>();

        HashMap<String, ArrayList>  currentLists = new HashMap<String, ArrayList> ();

        ArrayList<DeclarationVariableNode> varDeclList = new ArrayList<DeclarationVariableNode>();
        currentLists.put("declaredVar", varDeclList);

        ArrayList<DeclarationFunctionNode> funDeclList = new ArrayList<DeclarationFunctionNode>();
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
    public int newVarDeclToken(int scope, DeclarationVariableNode token){
        ArrayList<DeclarationVariableNode> currentRS = (ArrayList<DeclarationVariableNode>)this.scopMap.get(scope).get("declaredVar");
        currentRS.add(token);
        return scope;
    }

    // Ajoute une nouvelle déclaration de fonction
    public int newFunDeclToken(int scope, DeclarationFunctionNode token){
        ArrayList<DeclarationFunctionNode> currentRS = (ArrayList<DeclarationFunctionNode>)this.scopMap.get(scope).get("declaredFun");
        currentRS.add(token);
        return scope;
    }

    public boolean checkIfFunIsDecl(DeclarationFunctionNode foo){
        ArrayList<DeclarationFunctionNode> allFunDecl = new ArrayList<>();
        for(int i = 0; i<=this.deepestScope; i++){
            allFunDecl.addAll((ArrayList<DeclarationFunctionNode>)this.scopMap.get(i).get("declaredFun"));
        }

        return allFunDecl.contains(foo);
    }

    public boolean checkIfVarIsDecl(DeclarationVariableNode var){
        ArrayList<DeclarationVariableNode> allVarDecl = new ArrayList<>();
        for(int i = 0; i<=this.deepestScope; i++){
            allVarDecl.addAll((ArrayList<DeclarationVariableNode>)this.scopMap.get(i).get("declaredVar"));
        }

        return allVarDecl.contains(var);
    }

    public ArrayList<DeclarationVariableNode> getDeclarationVariableNodesFromName(String name){
        ArrayList<DeclarationVariableNode> res = new ArrayList<>();

        for(int i = 0; i<=this.deepestScope; i++){
            for (DeclarationVariableNode dvn : (ArrayList<DeclarationVariableNode>)this.scopMap.get(i).get("declaredVar")){
                if(name.equals(dvn.getName())){
                    res.add(dvn);
                }
            }
        }
        return res;
    }

    public boolean checkTypeVar(String type, String name){
        ArrayList<DeclarationVariableNode> allVarDecl = new ArrayList<>();
        for(int i = 0; i<=this.deepestScope; i++){
            allVarDecl.addAll((ArrayList<DeclarationVariableNode>)this.scopMap.get(i).get("declaredVar"));
        }
        for(int j = 0; j<allVarDecl.size();j++){
            if(type.equals(allVarDecl.get(j).getType()) && name.equals(allVarDecl.get(j).getName())){
                return true;
            }
        }
        return false;
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
