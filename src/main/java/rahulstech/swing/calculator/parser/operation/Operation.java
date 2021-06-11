package rahulstech.swing.calculator.parser.operation;

import java.math.BigDecimal;

public interface Operation {

    enum Priority {
        ADDITIVE,
        MULTIPLICATIVE,
        NONE
    }

    Priority priority();

    String name();

    BigDecimal evaluate();
}
