package net.kkolyan.json2.evaluation;

import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public interface Expression {
    Callable getAsCallable(int arity);
    void setValue(Object value);
    Object getValue();
    Type getDesiredType();
}
