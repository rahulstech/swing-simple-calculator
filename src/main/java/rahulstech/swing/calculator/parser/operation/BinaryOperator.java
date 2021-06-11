package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;
import java.util.Arrays;

public abstract class BinaryOperator extends AbstractParameterizedOperation {

    public BinaryOperator(String name,Priority priority) {
        super(name,priority);
    }

    public final void parameters(BigDecimal left, BigDecimal right) {
        parameters(Arrays.asList(left,right));
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
        return evaluate(left(),right());
    }
}
