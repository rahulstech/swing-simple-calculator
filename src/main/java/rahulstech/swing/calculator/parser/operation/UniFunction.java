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
 * A functional operation with exactly one parameter.
 * For example SQRT(<< number to find square root >>)
 *
 * @author Rahul Bagchi
 */
public abstract class UniFunction extends AbstractParameterizedOperation {

    public UniFunction(String name, Priority priority) {
        super(name, priority);
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        if (null == parameters || parameters.size() > 1) {
            throw new ParseException(name()+" requires exactly one parameter");
        }
        super.parameters(parameters);
    }

    protected abstract BigDecimal evaluate(BigDecimal param);

    @Override
    public final BigDecimal evaluate() {
        ensureParameterCount(1,1,name()+" requirs exactly one parameter");
        BigDecimal param = parameters().get(0);
        return evaluate(param);
    }
}
