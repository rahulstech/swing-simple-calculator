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
package rahulstech.swing.calculator.parser.operation;

import rahulstech.swing.calculator.parser.ParseException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Base class for all parameterized operations. Create a sub class and
 * implement the evaluate method. Evaluate method performs
 * the actual mathematical operation. A paramterized operation may be a
 * operator or a mathematical function which requires only one parameter or
 * exactly two parameters or multiple paramteres.
 *
 * @author Rahul Bagchi
 * @see ParameterizedOperation
 */
public abstract class AbstractParameterizedOperation extends AbstractOperation implements ParameterizedOperation {

    private List<BigDecimal> parameters;

    protected AbstractParameterizedOperation(String name,Priority priority) {
        super(name,priority);
    }

    @Override
    public final List<BigDecimal> parameters() {
        return parameters;
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return name()+"[priority="+priority()+", params="+parameters+"]";
    }

    /**
     * Check paramter count is within the range or throw exception
     *
     * @param min minimum number of parameters required
     * @param max maximum number of parameters required
     * @param message the error message
     */
    protected void ensureParameterCount(int min, int max, String message) {
        int count = null == parameters ? 0 : parameters.size();
        if (count < min || count > max) {
            throw new ParseException(message);
        }
    }
}
