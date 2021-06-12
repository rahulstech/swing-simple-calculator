package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;
import java.util.List;

public abstract class BiFunction extends AbstractParameterizedOperation {

    protected BiFunction(String name, Priority priority) {
        super(name, priority);
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        if (null == parameters || parameters.size() > 2) {
            throw new OperationException(name()+" requires exactly two parameter");
        }
        super.parameters(parameters);
    }

    protected abstract BigDecimal evaluate(BigDecimal param1, BigDecimal param2);

    @Override
    public BigDecimal evaluate() {
        BigDecimal param1 = parameters().get(0);
        BigDecimal param2 = parameters().get(1);
        return evaluate(param1,param2);
    }
}
