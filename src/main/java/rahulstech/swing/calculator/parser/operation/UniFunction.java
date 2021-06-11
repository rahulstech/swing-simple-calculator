package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;
import java.util.List;

public abstract class UniFunction extends AbstractParameterizedOperation {

    public UniFunction(String name, Priority priority) {
        super(name, priority);
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        if (null == parameters || parameters.size() > 1) {
            throw new OperationException(name()+" requires exactly one parameter");
        }
        super.parameters(parameters);
    }

    protected abstract BigDecimal evaluate(BigDecimal param);

    @Override
    public final BigDecimal evaluate() {
        BigDecimal param = parameters().get(0);
        return evaluate(param);
    }
}
