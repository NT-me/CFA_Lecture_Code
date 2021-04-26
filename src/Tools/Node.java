package Tools;

import java.util.ArrayList;

public class Node {
    private Token token;
    private ArrayList<Node>children;

    // Creating node without children
    public Node(Token root){
        this.token = root;
        this.children = new ArrayList<Node>();
    }

    //Creating node with children
    public Node(Token root, ArrayList<Node> children){
        this.token = root;
        this.children = children;
    }

    // Utility function add child none
    public Node newChild(Token childToken)
    {
        Node temp = new Node(childToken);
        this.children.add(temp);
        return temp;
    }
}
