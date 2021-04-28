package Tools;

enum lexicalType {
    word, // blabla, int
    number, // 1
    assignation,
    quote,
    doubleQuote,
    endInstruction, //(;)
    virgule,
    openParenthese,
    closeParenthese,
    superieur,
    inferieur,
    superieurOuEgal,
    inferieurOuEgal,
    different,
    equal,
    point,
    bool,
    list,

}

public class LexicalToken {
    private String type;
    private String value;
    private int line;


    public String getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int numLigne) {
        this.line = numLigne;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LexicalToken(String type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }
}
