package net.kkolyan.json2.introspection;

import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public interface Property {
    Object getValue(Object instance);
    void setValue(Object instance, Object value);
    Type getType();
    String getName();
}
