package Tools;

import java.util.ArrayList;

public class Node {
    private SyntaxiqueToken token;
    private ArrayList<Node>children;

    // Creating node without children
    public Node(SyntaxiqueToken root){
        this.token = root;
        this.children = new ArrayList<>();
    }

    //Creating node with children
    public Node(SyntaxiqueToken root, ArrayList<Node> children){
        this.token = root;
        this.children = children;
    }

    public Node() {
        this.token = null;
        this.children = null;
    }

    // Utility function add child none
    public Node newChild(SyntaxiqueToken childToken)
    {
        Node temp = new Node(childToken);
        this.children.add(temp);
        return temp;
    }
}
