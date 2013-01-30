package net.kkolyan.json2.introspection;

import net.kkolyan.json2.util.Generics;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author nplekhanov
 */
public class MapDynamicType implements DynamicType {
    private boolean concurrent;
    private Type valueType;

    public MapDynamicType(Type type) {
        valueType = Generics.getGenericTypeParameter(type, 1);
        concurrent = ConcurrentMap.class.isAssignableFrom(Generics.getRawType(type));
    }

    @Override
    public Object newInstance() {
        return concurrent ? new ConcurrentHashMap() : new LinkedHashMap();
    }

    @Override
    public Property getProperty(String name) {
        return new PropertyImpl(name, valueType);
    }

    @Override
    public Collection<Property> getProperties() {
        throw new UnsupportedOperationException();
    }

    private static class PropertyImpl implements Property {
        private String key;
        private Type valueType;

        public PropertyImpl(String key, Type valueType) {
            this.key = key;
            this.valueType = valueType;
        }

        @Override
        public Object getValue(Object instance) {
            Map map = (Map) instance;
            return map.get(key);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void setValue(Object instance, Object value) {
            Map map = (Map) instance;
            map.put(key, value);
        }

        @Override
        public Type getType() {
            return valueType;
        }

        @Override
        public String getName() {
            return key;
        }
    }
}
