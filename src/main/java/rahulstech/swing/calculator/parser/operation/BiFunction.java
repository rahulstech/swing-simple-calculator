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
 * A functional operation which accepts exactly two parameters
 *
 * @author Rahul Bagchi
 */
public abstract class BiFunction extends AbstractParameterizedOperation {

    protected BiFunction(String name, Priority priority) {
        super(name, priority);
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        if (null == parameters || parameters.size() > 2) {
            throw new ParseException(name()+" requires exactly two parameter");
        }
        super.parameters(parameters);
    }

    protected abstract BigDecimal evaluate(BigDecimal param1, BigDecimal param2);

    @Override
    public BigDecimal evaluate() {
        ensureParameterCount(2,2,name()+" requirs exactly two paramters");
        BigDecimal param1 = parameters().get(0);
        BigDecimal param2 = parameters().get(1);
        return evaluate(param1,param2);
    }
}
