package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;

import static rahulstech.swing.calculator.parser.operation.Operation.Priority.NONE;

public class Constant extends AbstractOperation {

    private BigDecimal value;

    public Constant(String name, BigDecimal value) {
        super(name,NONE);
        this.value = value;
    }

    @Override
    public BigDecimal evaluate() {
        return value;
    }
}
