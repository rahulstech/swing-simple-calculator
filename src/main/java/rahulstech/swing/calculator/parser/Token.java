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

/**
 * This class contains details for a single token
 * in an expression.
 *
 * @author Rahul Bagchi
 */
public class Token {

    // type of the token
    private final TokenType type;
    // the token
    private final String literal;
    // start position token in the expression
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
