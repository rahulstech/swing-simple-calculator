package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractParameterizedOperation extends AbstractOperation implements ParameterizedOperation {

    private List<BigDecimal> parameters;

    protected AbstractParameterizedOperation(String name,Priority priority) {
        super(name,priority);
    }

    @Override
    public final List<BigDecimal> parameters() {
        return parameters;
    }

    @Override
    public void parameters(List<BigDecimal> parameters) {
        this.parameters = parameters;
    }
}
