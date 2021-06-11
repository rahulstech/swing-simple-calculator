package rahulstech.swing.calculator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static rahulstech.swing.calculator.parser.TokenType.*;

public class Tokenizer {

    private final String input;
    private final int inputLength;
    private final Matcher wsMatcher;
    private Token lastToken = null;
    private int offset = 0;

    public Tokenizer(String input) {
        if (null == input || "".equals(input)) {
            throw new IllegalArgumentException("can not tokenize empty string");
        }
        this.input = input;
        this.inputLength = input.length();
        this.wsMatcher = Pattern.compile("\\s+").matcher(input);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (hasToken()) {
            Token token = nextToken();
            tokens.add(token);
        }
        return tokens;
    }

    public boolean hasToken() {
        if (wsMatcher.find(offset) && offset == wsMatcher.start()) {
            offset = wsMatcher.end();
        }
        return offset < inputLength;
    }

    public Token nextToken() {
        Token token;
        if (check(KEYWORD)) {
            token = nextKeyword();
        }
        else if (check(SYMBOL)) {
            token = nextSymbol();

        }
        else if (check(NUMERIC)) {
            token = nextNumber();
        }
        else {
            throw new ParseException("invalid character '"+input.charAt(offset)+"'");
        }
        this.lastToken = token;
        return token;
    }

    public Token nextKeyword() {
        return pop(KEYWORD);
    }

    public Token nextSymbol() {
        return pop(SYMBOL);
    }

    public Token nextNumber() {
        return pop(NUMERIC);
    }

    private boolean check(TokenType type) {
        Matcher matcher = Pattern.compile(type.getPattern()).matcher(input).region(offset,inputLength);
        if (matcher.find()) {
            return offset == matcher.start();
        }
        return false;
    }

    private Token pop(TokenType type) {
        Matcher matcher = Pattern.compile(type.getPattern()).matcher(input).region(offset,inputLength);
        matcher.find();
        Token token = new Token(type,matcher.group(),matcher.start());
        this.offset = matcher.end();
        return token;
    }
}
