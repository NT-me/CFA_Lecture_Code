package Tools;

enum SyntaxiqueType{

}

public class SyntaxiqueToken {
    private SyntaxiqueType type;
    private String name;
    private LexicalToken value;

    public SyntaxiqueToken(SyntaxiqueType type, String name, LexicalToken value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public SyntaxiqueType getType() {
        return type;
    }

    public void setType(SyntaxiqueType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LexicalToken getValue() {
        return value;
    }

    public void setValue(LexicalToken value) {
        this.value = value;
    }
}
