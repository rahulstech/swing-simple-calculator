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
import java.util.Arrays;
import java.util.List;

public abstract class BinaryOperator extends AbstractParameterizedOperation {

    public BinaryOperator(String name,Priority priority) {
        super(name,priority);
    }

    public final void parameters(BigDecimal left, BigDecimal right) {
        parameters(Arrays.asList(left,right));
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        if (null == parameters || parameters.size() != 2) {
            throw new ParseException(name()+" requires exactly two parameters");
        }
        super.parameters(parameters);
    }

    public final BigDecimal left() {
        return parameters().get(0);
    }

    public final BigDecimal right() {
        return parameters().get(1);
    }

    protected abstract BigDecimal evaluate(BigDecimal left, BigDecimal right);

    @Override
    public final BigDecimal evaluate() {
        ensureParameterCount(2,2,name()+" requires exactly two paramters");
        return evaluate(left(),right());
    }
}
