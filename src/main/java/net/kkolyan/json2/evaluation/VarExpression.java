package net.kkolyan.json2.evaluation;

import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public class VarExpression implements Expression {
    private String name;
    private EvaluationContext context;

    public VarExpression(String name, EvaluationContext context) {
        this.name = name;
        this.context = context;
    }

    @Override
    public Callable getAsCallable(int arity) {
        throw new UnsupportedOperationException("'" + name + "' is not a callable");
    }

    @Override
    public void setValue(Object value) {
        context.setValue(name, value);
    }

    @Override
    public Object getValue() {
        return context.getValue(name);
    }

    @Override
    public Type getDesiredType() {
        Object value = getValue();
        if (value == null) {
            return null;
        }
        return value.getClass();
    }

    @Override
    public String toString() {
        return "VarExpression{" +
                "" + name + '}';
    }
}
