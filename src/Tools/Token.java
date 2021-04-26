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
    list
}

public class Token {
    private lexicalType type;
    private String value;

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

    public Token(lexicalType type, String value) {
        this.type = type;
        this.value = value;
    }
}
