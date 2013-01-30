package net.kkolyan.json2.evaluation;

import net.kkolyan.json2.util.Generics;

import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public class PrimitiveTypeConverter implements TypeConverter {

    @SuppressWarnings("unchecked")
    @Override
    public Object convertTo(Object value, Type requiredType) {
        if (value == null) {
            return null;
        }
        Class rawType = Generics.getRawType(requiredType);
        if (rawType == byte.class || rawType == Byte.class)
            return (value instanceof Number) ? ((Number) value).byteValue() : Byte.valueOf(value.toString());

        if (rawType == short.class || rawType == Short.class)
            return (value instanceof Number) ? ((Number) value).shortValue() : Byte.valueOf(value.toString());

        if (rawType == int.class || rawType == Integer.class)
            return (value instanceof Number) ? ((Number) value).intValue() : Integer.valueOf(value.toString());

        if (rawType == long.class || rawType == Long.class)
            return (value instanceof Number) ? ((Number) value).longValue() : Long.valueOf(value.toString());

        if (rawType == float.class || rawType == Float.class)
            return (value instanceof Number) ? ((Number) value).floatValue() : Float.valueOf(value.toString());

        if (rawType == double.class || rawType == Double.class)
            return (value instanceof Number) ? ((Number) value).doubleValue() : Double.valueOf(value.toString());

        if (rawType == char.class || rawType == Character.class) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            String s = (String) value;
            if (s.length() != 1) {
                throw new IllegalStateException();
            }
            return s.charAt(0);
        }

        if (rawType == boolean.class || rawType == Boolean.class)
            return (value instanceof Number) ? ((Number) value).intValue() != 0 : Boolean.valueOf(value.toString());

        if (value instanceof Boolean) {
            if (rawType == boolean.class || rawType == Boolean.class) return value;
        }

        if (rawType == String.class)
            return value.toString();

        if (rawType.isEnum())
            return Enum.valueOf(rawType, value.toString());

        if (rawType == Class.class) {
            try {
                return Class.forName(value.toString());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        throw new IllegalStateException("can't coerce " + value + " to " + rawType.getName());
    }

    @Override
    public boolean isSupported(Type requiredType) {
        Class rawType = Generics.getRawType(requiredType);
        return rawType.isPrimitive() || Number.class.isAssignableFrom(rawType) || rawType.isEnum()
                || rawType == Boolean.class || rawType == String.class || rawType == Class.class;
    }
}
