package net.kkolyan.json2.introspection;

import net.kkolyan.json2.util.Generics;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author nplekhanov
 */
public class DynamicTypeFactory {

    private ConcurrentMap<Type,DynamicType> strategyCache = new ConcurrentHashMap<Type, DynamicType>();

    public DynamicType getDynamicType(Type type) {
        DynamicType strategy = strategyCache.get(type);
        if (strategy == null) {
            strategy = createDynamicType(type);
            DynamicType last = strategyCache.putIfAbsent(type, strategy);
            if (last != null) {
                strategy = last;
            }
        }
        return strategy;
    }

    private DynamicType createDynamicType(Type type) {
        Class rawClass = Generics.getRawType(type);

        if (Map.class.isAssignableFrom(rawClass)) {
            Type valueType = Generics.getGenericTypeParameter(type, 1);
            return new MapDynamicType(type);
        }

        if (List.class.isAssignableFrom(rawClass)) {
            Type elementType = Generics.getGenericTypeParameter(type, 0);
            return new ListDynamicType(type);
        }

        if (rawClass.isArray()) {
            return new ArrayDynamicType(type);
        }

        return new PlainObjectDynamicType(type);
    }
}
