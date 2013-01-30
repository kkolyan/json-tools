package net.kkolyan.json2.evaluation;

import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public interface Callable {
    Object call(Object[] args);
    Type getArgType(int index);
}
