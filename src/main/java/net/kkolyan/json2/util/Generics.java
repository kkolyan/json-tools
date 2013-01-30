package net.kkolyan.json2.util;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public class Generics {
    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getRawType(((ParameterizedType) type).getRawType());
        }
        throw new IllegalStateException("unsupported type: " + type.getClass().getName() + " for " + type);
    }

    public static Type getGenericTypeParameter(Type type, int index) {
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types.length > index) {
                return types[index];
            }
        }
        return null;
    }
}
