package Tools;

public class NumberNode extends Node{
    private String value;

    public NumberNode(String value) {
        super();
        this.value = value;
    }

    public NumberNode() {
        super();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void printNode(int depth){
        String indentlvl = " ".repeat(depth);
        System.out.println(indentlvl + this.type.toString() + " " + this.value);
    }
}
