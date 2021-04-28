package Tools;

import java.util.ArrayList;

public class DeclarationFunctionNode extends Node{

    public String name;
    public ArrayList<DeclarationVariableNode> params;
    public BodyNode body;
    public DeclarationFunctionNode(String type, String name, BodyNode body) {
        super(type);
        this.name = name;
        this.params = new ArrayList<DeclarationVariableNode>();
        this.body = body;
    }

    public DeclarationFunctionNode(String type, String name, ArrayList<DeclarationVariableNode> params, BodyNode body) {
        super(type);
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public DeclarationFunctionNode() {
        super();
        this.name = "";
        this.params = new ArrayList<DeclarationVariableNode>();
        this.body = new BodyNode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
