package Tools;

public class VarNode extends Node{
    private String name;

    public VarNode(String type, String name) {
        super(type, null, null);
        this.name = name;
    }

    @Override
    public void printNode(int depth){
        String indentlvl = " ".repeat(depth);
        System.out.println(indentlvl + this.name);
    }
}
