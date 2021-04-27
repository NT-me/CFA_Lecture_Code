package Tools;

import java.beans.Expression;
import java.util.ArrayList;

public class DeclarationFunctionNode extends Node{

    public String name;
    public ArrayList<String> params;
    public ExpressionNode expression;
    public DeclarationFunctionNode(String type, String name, ExpressionNode expression) {
        super(type);
        this.name = name;
        this.params = new ArrayList<String>();
        this.expression = expression;
    }

    public DeclarationFunctionNode(String type, String name, ArrayList<String> params, ExpressionNode expression) {
        super(type);
        this.name = name;
        this.params = params;
        this.expression = expression;
    }

    public DeclarationFunctionNode() {
        super();
        this.name = "";
        this.params = new ArrayList<String>();
        this.expression = new ExpressionNode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public void addParams(String param){
        this.params.add(param);
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
}
