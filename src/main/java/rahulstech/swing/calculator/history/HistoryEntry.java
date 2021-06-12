package rahulstech.swing.calculator.history;

import java.math.BigDecimal;

public class HistoryEntry {

    private String expression;
    private BigDecimal result;

    public HistoryEntry() {}

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "<html>" +
                "<br/><b>"+expression+"</b> " +
                "<br/> &#61; <i>"+result+"</i>" +
                "<br/></html>";
    }
}
