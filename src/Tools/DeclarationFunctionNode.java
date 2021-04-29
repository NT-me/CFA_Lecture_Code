package Tools;

import java.util.ArrayList;

public class DeclarationFunctionNode extends Node{

    public String name;
    public ArrayList<DeclarationVariableNode> params;
    public BodyNode body;
    public int scope;
    public DeclarationFunctionNode(String type, String name, BodyNode body, int scope) {
        super(type);
        this.name = name;
        this.params = new ArrayList<DeclarationVariableNode>();
        this.body = body;
        this.scope = scope;
    }

    public DeclarationFunctionNode(String type, String name, ArrayList<DeclarationVariableNode> params, BodyNode body, int scope) {
        super(type);
        this.name = name;
        this.params = params;
        this.body = body;
        this.scope = scope;
    }

    public DeclarationFunctionNode() {
        super();
        this.name = "";
        this.params = new ArrayList<DeclarationVariableNode>();
        this.body = new BodyNode();
        this.scope = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    @Override
    public ArrayList<Node> getChildren(){
        ArrayList<Node> res = new ArrayList<>();
        res.addAll(this.params);
        res.add(this.body);

        return res;
    }

    public ArrayList<DeclarationVariableNode> getParams() {
        return params;
    }

    public void setParams(ArrayList<DeclarationVariableNode> params) {
        this.params = params;
    }

    public void addParams(DeclarationVariableNode param){
        this.params.add(param);
    }

    public BodyNode getBody() {
        return body;
    }

    public void setBody(BodyNode body) {
        this.body = body;
    }

    @Override
    public void printNode(int depth){
        String indentlvl = " ".repeat(depth);
        System.out.println(indentlvl + this.type.toString() + " " + this.name + " " + this.params + " " + this.body);

        depth += 1;
        for(Node n : this.params) {
            n.printNode(depth);
        }
        this.body.printNode(depth);

    }
}
