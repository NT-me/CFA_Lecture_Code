package Tools;

public class RootNode extends Node{
    public RootNode() {
        super("root");
    }

    public void addChild(Node nodeDec) {
        super.children.add(nodeDec);
    }
}
