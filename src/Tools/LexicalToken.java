package Tools;

public class LexicalToken {
    private lexicalType type;
    private String value;
    private int line;


    public lexicalType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int numLigne) {
        this.line = numLigne;
    }

    public void setType(lexicalType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LexicalToken() {
        this.type = null;
        this.value ="";
        this.line = -1;
    }

    public LexicalToken(lexicalType type, String value) {
        this.type = type;
        this.value = value;
        this.line = -1;
    }


    public LexicalToken(lexicalType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    public void setToken(LexicalToken token) {
        this.setValue(token.getValue());
        this.setType(token.getType());
    }
}
