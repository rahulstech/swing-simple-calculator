/**
 * Copyright 2021 rahulstech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rahulstech.swing.calculator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static rahulstech.swing.calculator.parser.TokenType.*;

/**
 *
 * @author Rahul Bagchi
 */
public class Tokenizer {

    // the string to tokenize
    private final String input;
    private final int inputLength;
    // java.ragex.Matcher to match whit space
    // in the input string
    private final Matcher wsMatcher;
    // last found token, initially null
    private Token lastToken = null;
    // index from which next character start
    // excluding whitespaces
    private int offset = 0;

    /**
     * Create a new tokenizer for the given string
     *
     * @param input non null non empty string to parse
     * @throws IllegalArgumentException if input is null or empty
     */
    public Tokenizer(String input) {
        if (null == input || "".equals(input)) {
            throw new IllegalArgumentException("can not tokenize empty string");
        }
        this.input = input;
        this.inputLength = input.length();
        this.wsMatcher = Pattern.compile("\\s+").matcher(input);
    }

    /**
     * Parse all token form the input.
     *
     * @return non null instance  of {@link List} of  {@link Token}
     * @throws ParseException exception thrown by {@link #nextToken()}
     */
    public List<Token> tokenize() throws ParseException {
        List<Token> tokens = new ArrayList<>();
        while (hasToken()) {
            Token token = nextToken();
            tokens.add(token);
        }
        return tokens;
    }

    /**
     * Check if any charatcer availble after the current postion or not,
     * exclusing whitespace
     */
    public boolean hasToken() {
        if (wsMatcher.find(offset) && offset == wsMatcher.start()) {
            offset = wsMatcher.end();
        }
        return offset < inputLength;
    }

    /**
     * Get the next token. This method does not check for availablity of
     * token. Call {@link #hasToken() hasToken} before calling this method.
     *
     * @return an instance of {@link Token} if successfully parsed
     * @throws ParseException if any invalid character found
     */
    public Token nextToken() throws ParseException {
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

    /**
     * Parse next keyword type token
     *
     * @see #pop(TokenType)
     */
    public Token nextKeyword() {
        return pop(KEYWORD);
    }

    /**
     * Parse next symbol type token
     *
     * @see #pop(TokenType)
     */
    public Token nextSymbol() {
        return pop(SYMBOL);
    }

    /**
     * Parser next number type token
     *
     * @see #pop(TokenType)
     */
    public Token nextNumber() {
        return pop(NUMERIC);
    }

    /**
     * Check if next token is of given type with out changing the
     * cursor position.
     *
     * @param type the token type to check
     * @return {@literal true} if available, {@literal false} otherwise
     */
    private boolean check(TokenType type) {
        Matcher matcher = Pattern.compile(type.getPattern()).matcher(input).region(offset,inputLength);
        if (matcher.find()) {
            return offset == matcher.start();
        }
        return false;
    }

    /**
     * Parse the next token from the input and move the parser postion.
     * This method assumes that there is a valid token of given type
     * available just after the current parser position.
     *
     * @param type token type to parse
     * @return parsed token
     */
    private Token pop(TokenType type) {
        Matcher matcher = Pattern.compile(type.getPattern()).matcher(input).region(offset,inputLength);
        matcher.find();
        Token token = new Token(type,matcher.group(),matcher.start());
        this.offset = matcher.end();
        return token;
    }
}
