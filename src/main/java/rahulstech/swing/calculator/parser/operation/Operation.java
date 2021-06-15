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

import java.math.BigDecimal;

/**
 * Base class for all kind of mathematical operations or
 * constant values. Each operation has a name, priority
 * to identiry its calculation precendence and an evaluation
 * method to perform actula mathematical operation
 *
 * @author Rahul Bagchi
 */
public interface Operation {

    /**
     * Priority of the operation
     */
    enum Priority {
        ADDITIVE,
        MULTIPLICATIVE
    }

    Priority priority();

    String name();

    BigDecimal evaluate();
}
