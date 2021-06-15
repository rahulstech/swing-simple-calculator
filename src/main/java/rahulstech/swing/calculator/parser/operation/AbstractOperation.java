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

/**
 * Base class for all operations. Create a sub class and
 * implement the evaluate method. Evaluate method performs
 * the actual mathematical operation.
 *
 * @author Rahul Bagchi
 * @see Operation
 */
public abstract class AbstractOperation implements Operation {

    private String name;
    private Priority priority;

    protected AbstractOperation(String name, Priority priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public Priority priority() {
        return priority;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name+"["+priority+"]";
    }
}
