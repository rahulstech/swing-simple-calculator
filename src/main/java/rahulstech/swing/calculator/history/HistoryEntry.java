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
package rahulstech.swing.calculator.history;

import java.math.BigDecimal;

/**
 * This class contains details for a single history entry.
 * Currently a history entry holds the expression and the
 * result only.
 * 
 * @author Rahul Bagchi
 */
public class HistoryEntry {

    private String expression;
    private BigDecimal result;

    public HistoryEntry() {}

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "<html>" +
                "<br/><b>"+expression+"</b> " +
                "<br/> &#61; <i>"+result+"</i>" +
                "<br/></html>";
    }
}
