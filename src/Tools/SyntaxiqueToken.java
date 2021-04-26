package Tools;

enum SyntaxiqueType{
    declarationVariable,
    affectationVariable,
    function
}

public class SyntaxiqueToken {
    private SyntaxiqueType type;
    private String name;
    private ContenuToken contenu;

    public SyntaxiqueToken(SyntaxiqueType type, String name, ContenuToken contenu) {
        this.type = type;
        this.name = name;
        this.contenu = contenu;
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

    public ContenuToken getValue() {
        return contenu;
    }

    public void setValue(ContenuToken contenu) {
        this.contenu = contenu;
    }
}
