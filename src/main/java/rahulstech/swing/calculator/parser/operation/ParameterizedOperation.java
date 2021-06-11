package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;
import java.util.List;

public interface ParameterizedOperation extends Operation {

    List<BigDecimal> parameters();

    void parameters(List<BigDecimal> parameters);
}
