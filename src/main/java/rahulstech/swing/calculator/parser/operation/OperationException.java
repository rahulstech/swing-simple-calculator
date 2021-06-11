package rahulstech.swing.calculator.parser.operation;

import rahulstech.swing.calculator.parser.CalculatorException;

public class OperationException extends CalculatorException {

    public OperationException() {}

    public OperationException(String message) {
        super(message);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
