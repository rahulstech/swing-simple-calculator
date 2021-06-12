package rahulstech.swing.calculator.parser;

import rahulstech.swing.calculator.parser.operation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static rahulstech.swing.calculator.parser.TokenType.KEYWORD;
import static rahulstech.swing.calculator.parser.TokenType.NUMERIC;
import static rahulstech.swing.calculator.parser.operation.Operation.Priority.ADDITIVE;
import static rahulstech.swing.calculator.parser.operation.Operation.Priority.MULTIPLICATIVE;

public class Calculator {

    private Map<String,Operation> operations = new HashMap<>();
    private Map<Operation.Priority,List<String>> priorityOperationNames = new HashMap<>();

    private List<Token> tokens = null;
    private int tokenCount = 0;
    private int index;
    private BigDecimal lastResult = BigDecimal.ZERO;

    public Calculator() {
        registerDefaultOperations();
    }

    public void registerOperation(Operation operation) throws CalculatorException {
        if (null == operation) {
            throw new NullPointerException("operation == null");
        }
        String name = operation.name();
        Operation.Priority priority = operation.priority();
        if (operations.containsKey(name)) {
            throw new CalculatorException("another operation with name \""+name+"\" is already added");
        }
        operations.put(name,operation);
        List<String> names = priorityOperationNames.get(priority);
        names.add(name);
    }

    public void unregisterOperation(String name) throws CalculatorException {
        if (null == name) {
            throw new NullPointerException("name == null");
        }

        if (!operations.containsKey(name)) {
            throw new CalculatorException("no operation found with name \""+name+"\"");
        }
        Operation operation = operations.get(name);
        Operation.Priority priority = operation.priority();
        List<String> names = priorityOperationNames.get(priority);
        names.remove(name);
    }

    public BigDecimal calculate(String expression) throws CalculatorException {
        Tokenizer tokenizer = new Tokenizer(expression);
        this.tokens = tokenizer.tokenize();
        this.tokenCount = tokens.size();
        this.index = 0;
        BigDecimal result = parseOperation();
        this.lastResult = result;
        return result;
    }

    private void registerDefaultOperations() {
        priorityOperationNames.put(Operation.Priority.ADDITIVE,new ArrayList<>());
        priorityOperationNames.put(Operation.Priority.MULTIPLICATIVE,new ArrayList<>());
        priorityOperationNames.put(Operation.Priority.NONE,new ArrayList<>());

        registerDefaultAdditiveOperators();
        registerDefaultMultiplicativeOperators();
        registerDefaultFunctions();
    }

    private void registerDefaultAdditiveOperators() {
        registerOperation(new BinaryOperator("+", Operation.Priority.ADDITIVE) {
            @Override
            public BigDecimal evaluate(BigDecimal left, BigDecimal right) {
                return left.add(right);
            }
        });
        registerOperation(new BinaryOperator("-", Operation.Priority.ADDITIVE) {
            @Override
            public BigDecimal evaluate(BigDecimal left, BigDecimal right) {
                return left.subtract(right);
            }
        });
    }

    private void registerDefaultMultiplicativeOperators() {
        registerOperation(new BinaryOperator("*", Operation.Priority.MULTIPLICATIVE) {
            @Override
            public BigDecimal evaluate(BigDecimal left, BigDecimal right) {
                return left.multiply(right,MathContext.DECIMAL128);
            }
        });
        registerOperation(new BinaryOperator("/", Operation.Priority.MULTIPLICATIVE) {
            @Override
            public BigDecimal evaluate(BigDecimal left, BigDecimal right) {
                if (0 == right.compareTo(BigDecimal.ZERO)) {
                    throw new OperationException("can not divide with 0");
                }
                return left.divide(right,MathContext.DECIMAL128);
            }
        });
        registerOperation(new BinaryOperator("^", Operation.Priority.MULTIPLICATIVE) {
            @Override
            public BigDecimal evaluate(BigDecimal left, BigDecimal right) {
                int power = right.intValue();
                if (0 == power && 0 == left.compareTo(BigDecimal.ZERO)) {
                    throw new OperationException("0 to the power 0 is not valid");
                }
                return left.pow(power,MathContext.DECIMAL128);
            }
        });
        registerOperation(new BinaryOperator("%", Operation.Priority.MULTIPLICATIVE) {
            @Override
            public BigDecimal evaluate(BigDecimal left, BigDecimal right) {
                return left.multiply(right,MathContext.DECIMAL128)
                        .divide(new BigDecimal(100),MathContext.DECIMAL128);
            }
        });
    }

    private void registerDefaultFunctions() {
        registerOperation(new UniFunction("SQRT",MULTIPLICATIVE) {
            @Override
            protected BigDecimal evaluate(BigDecimal param) {
                if (-1 == param.signum()) {
                    throw new OperationException("can not calculate square root of a negative number");
                }
                return param.sqrt(MathContext.DECIMAL128);
            }
        });
        registerOperation(new BiFunction("REMAINDER",MULTIPLICATIVE) {
            @Override
            protected BigDecimal evaluate(BigDecimal param1, BigDecimal param2) {
                if (param2.equals(BigDecimal.ZERO)) {
                    throw new OperationException("can not divide by zero");
                }
                return param1.remainder(param2);
            }
        });
        registerOperation(new AbstractParameterizedOperation("AVG",ADDITIVE) {
            @Override
            public BigDecimal evaluate() {
                List<BigDecimal> params = parameters();
                if (null == params || params.size() < 2) {
                    throw new OperationException("average function requires at least 2 parameters");
                }
                BigDecimal sum = BigDecimal.ZERO;
                for (BigDecimal n : params) {
                    sum = sum.add(n);
                }
                return sum.divide(new BigDecimal(params.size()),MathContext.DECIMAL128);
            }
        });
    }

    BigDecimal parseOperation() throws ParseException {
        return parseAdditiveOperation();
    }

    BigDecimal parseAdditiveOperation() throws ParseException {
        BigDecimal left = parseMultiplicativeOperation();
        List<String> names = priorityOperationNames.get(ADDITIVE);
        if (!hasToken(1) || !names.contains(peek(0).literal())){
            return left;
        }
        BigDecimal root = null;
        while (hasToken(1) && names.contains(peek(0).literal())) {
            Token operator = pop();
            BigDecimal right = parseMultiplicativeOperation();
            BigDecimal result = createBinaryOperator(operator.literal(),left,right).evaluate();
            left = result;
            if (null != result) {
                root = result;
            }
        }
        return root;
    }

    BigDecimal parseMultiplicativeOperation() throws ParseException {
        BigDecimal left = parseBaseOperation();
        List<String> names = priorityOperationNames.get(MULTIPLICATIVE);
        if (!hasToken(1) || !names.contains(peek(0).literal())) return left;
        BigDecimal root = null;
        while (hasToken(1) && names.contains(peek(0).literal())) {
            Token operator = pop();
            BigDecimal right = parseBaseOperation();
            BigDecimal result = createBinaryOperator(operator.literal(),left,right).evaluate();
            left = result;
            if (null != result) {
                root = result;
            }
        }
        return root;
    }

    BigDecimal parseBaseOperation() throws ParseException {
        if (check("+",NUMERIC) || check("-",NUMERIC)) {
            Token sign = pop();
            BigDecimal number = new BigDecimal(pop().literal());
            if (sign.literal().equals("+")) {
                return number;
            }
            else {
                return number.negate();
            }
        }
        else if (check(NUMERIC)) {
            Token token = pop();
            String literal = token.literal();
            return new BigDecimal(literal);
        }
        else if (check(KEYWORD,"(")) {
            Token name = pop();
            List<BigDecimal> parameters = new ArrayList<>();
            advance(); // for '('
            while (!check(")")) {
                BigDecimal param = parseOperation();
                parameters.add(param);
                if (check(",")) {
                    advance();
                }
            }
            if (!check(")")) {
                throw new ParseException("expected )");
            }
            advance(); // for ')'
            Operation operation = createFunction(name.literal(),parameters);
            return operation.evaluate();
        }
        else if (check("ANS")) {
            advance();
            return lastResult;
        }
        else if (check("(")) {
            advance();
            BigDecimal result = parseOperation();
            if (!check(")")) {
                throw new ParseException("expected )");
            }
            advance();
            return result;
        }
        else {
            throw new ParseException("unexpected token "+peek(0));
        }
    }

    private boolean check(Object... tokenTypeOrLiteral) {
        int offset = 0;
        for (Object o : tokenTypeOrLiteral) {
            if (!hasToken(offset)) return false;
            Token token = peek(offset);
            if (o instanceof TokenType) {
                TokenType type = (TokenType) o;
                if (token.type() != type)
                    return false;
            }
            else {
                String literal = (String) o;
                if (!token.literal().equals(literal))
                    return false;
            }
            offset++;
        }
        return true;
    }

    private boolean hasToken(int offset) {
        return index+offset < tokenCount;
    }

    private Token peek(int offset) {
        int at = index+offset;
        if (at >= tokenCount) {
            throw new IndexOutOfBoundsException("available tokens count "+tokenCount+
                    "; but requested token @ "+at);
        }
        return tokens.get(at);
    }

    private Token pop() {
        Token token = tokens.get(index);
        index++;
        return token;
    }

    private void advance() {
        int newIndex = index+ 1;
        if (newIndex < 0 || newIndex > tokenCount) {
            throw new IndexOutOfBoundsException("can not move pointer @ "+newIndex);
        }
        index = newIndex;
    }

    private BinaryOperator createBinaryOperator(String name, BigDecimal left, BigDecimal right) {
        BinaryOperator operation = getOrThrow(name);
        operation.parameters(left,right);
        return operation;
    }

    private ParameterizedOperation createFunction(String name, List<BigDecimal> parameters) {
        ParameterizedOperation operation = getOrThrow(name);
        operation.parameters(parameters);
        return operation;
    }

    private <O extends Operation> O getOrThrow(String name) throws OperationException {
        if (!operations.containsKey(name)) {
            throw new OperationException("Operation \""+name+"\" not implemented");
        }
        return (O) operations.get(name);
    }
}
