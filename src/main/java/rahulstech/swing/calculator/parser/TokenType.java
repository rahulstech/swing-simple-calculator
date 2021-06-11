package rahulstech.swing.calculator.parser;

public enum TokenType {

    KEYWORD("[A-Za-z_][A-Za-z0-9_]*"),
    NUMERIC("(\\+|-)?(\\d*\\.\\d*|[1-9]\\d*|(0))"),
    SYMBOL("[^\\s]");

    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
