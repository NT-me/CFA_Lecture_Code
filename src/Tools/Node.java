package Tools;

import java.util.ArrayList;

public abstract class Node {
    protected String type;
    protected ArrayList<Node>children;
    protected Node parent;

    public Node() {
        this.type = "";
        this.children = new ArrayList<Node>();
        this.parent = null;
    }

    public Node(String type) {
        this.type = type;
        this.children = new ArrayList<Node>();
        this.parent = null;
    }

    public Node(String type, ArrayList<Node> children, Node parent) {
        this.type = type;
        this.children = children;
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void addChild(Node child){
        child.setParent(this);
        this.children.add(child);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public void printNode(int depth){
        String indentlvl = " ".repeat(depth);
        System.out.println(indentlvl + this.type + this.children);

        depth += 1;
        for(Node n : this.children) {
            n.printNode(depth);
        }
    }
}
