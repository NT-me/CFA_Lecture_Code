package Tools;

enum ContenuType{
    typeInt,
    typeFloat,
    typeString,
    typeBool,
    typeList
}

public class ContenuToken {
    private ContenuType type;
    private String value;

    public ContenuToken(ContenuType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ContenuType getType() {
        return type;
    }

    public void setType(ContenuType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
