package rahulstech.swing.calculator.parser.operation;

public abstract class AbstractOperation implements Operation {

    private String name;
    private Priority priority;

    protected AbstractOperation(String name, Priority priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public Priority priority() {
        return priority;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name+"["+priority+"]";
    }
}
