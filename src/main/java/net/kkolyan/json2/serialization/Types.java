package net.kkolyan.json2.serialization;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nplekhanov
 */
public class Types {

    private static final Set<Class<?>> asIsTypes;
    private static final Set<Class<?>> quotedTypes;

    static {
        asIsTypes = new HashSet<Class<?>>();
        asIsTypes.add(Boolean.class);
        asIsTypes.add(Byte.class);
        asIsTypes.add(Short.class);
        asIsTypes.add(Integer.class);
        asIsTypes.add(Long.class);
        asIsTypes.add(Float.class);
        asIsTypes.add(Double.class);

        quotedTypes = new HashSet<Class<?>>();
        quotedTypes.add(String.class);
        quotedTypes.add(File.class);
        quotedTypes.add(Enum.class);
        quotedTypes.add(Character.class);
    }

    public static boolean isAsIs(Class<?> aClass) {
        return asIsTypes.contains(aClass);
    }

    public static boolean isQuoted(Class<?> aClass) {
        for (Class<?> c: quotedTypes) {
            if (c.isAssignableFrom(aClass)) {
                return true;
            }
        }
        return false;
    }
}
