package Tools;

public class AffectationNode extends Node{

    private String name;

    public AffectationNode(String name) {
        super();
        this.name = name;
    }

    public AffectationNode() {
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
