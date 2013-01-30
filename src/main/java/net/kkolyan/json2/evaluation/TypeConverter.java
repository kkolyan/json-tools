package net.kkolyan.json2.evaluation;

import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public interface TypeConverter {
    Object convertTo(Object value, Type requiredType);
    boolean isSupported(Type requiredType);
}
