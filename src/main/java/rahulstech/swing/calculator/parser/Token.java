package rahulstech.swing.calculator.parser;

public class Token {

    private final TokenType type;
    private final String literal;
    private final int start;

    public Token(TokenType type, String literal, int start) {
        this.type = type;
        this.literal = literal;
        this.start = start;
    }

    public TokenType type() {
        return type;
    }

    public String literal() {
        return literal;
    }

    public int start() {
        return start;
    }

    @Override
    public String toString() {
        return "Token("+type+"="+literal+"@"+start+")";
    }
}
