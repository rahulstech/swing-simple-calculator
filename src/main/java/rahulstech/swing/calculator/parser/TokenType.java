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
 * Differ types of token accepted by the {@link Tokenizer}
 *
 * @author Rahul Bagchi
 */
public enum TokenType {

    // all alpha numeric of length at least 1
    // keyword can not start with a number
    KEYWORD("[A-Za-z_][A-Za-z0-9_]*"),
    // all integer and decimal
    NUMERIC("(\\d*\\.\\d*|[1-9]\\d*|(0))"),
    // all single length character expect number and whitespace
    SYMBOL("[^\\d\\s]");

    // regular expression used for the token
    // of current type
    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
