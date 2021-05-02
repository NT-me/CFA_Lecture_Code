package Tools;

public class DeclarationVariableNode extends Node{

    public String name;
    public DeclarationVariableNode(String type, String name) {
        super(type);
        this.name = name;
    }

    public DeclarationVariableNode() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printNode(int depth){
        String indentlvl = " ".repeat(depth);
        System.out.println(indentlvl + this.type + " " + this.name + " " + this.children);

        depth += 1;
        for(Node n : this.children) {
            n.printNode(depth);
        }
    }
}
