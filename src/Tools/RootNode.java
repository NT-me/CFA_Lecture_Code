package Tools;

public class RootNode extends Node{
    public RootNode() {
        super("root");
    }

    public void addChild(Node nodeDec) {
        super.children.add(nodeDec);
    }

    @Override
    public void printNode(int depth){
        String indentlvl = " ".repeat(depth);
        System.out.println(indentlvl + this.type + "  " + this.children);

        depth += 1;
        for(Node n : this.children) {
            n.printNode(depth);
        }
    }
}
