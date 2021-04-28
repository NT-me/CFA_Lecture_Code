package Tools;

public class LexicalToken {
    private lexicalType type;
    private String value;
    private int line;

    public LexicalToken(lexicalType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public lexicalType getType() {
        return type;
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

    public LexicalToken(lexicalType type, String value) {
        this.type = type;
        this.value = value;
    }

    public LexicalToken() {
        this.type = null;
        this.value = "";
    }
}
