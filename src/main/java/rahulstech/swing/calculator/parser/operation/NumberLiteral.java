package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;

import static rahulstech.swing.calculator.parser.operation.Operation.Priority.NONE;

public class NumberLiteral extends AbstractOperation {

    private BigDecimal value;

    public NumberLiteral(BigDecimal value) {
        super(null,NONE);
        this.value = value;
    }

    @Override
    public final BigDecimal evaluate() {
        return value;
    }
}
