package net.kkolyan.json2.introspection;

import net.kkolyan.json2.util.Generics;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author nplekhanov
 */
public class ArrayDynamicType implements DynamicType {
    private Type elementType;

    public ArrayDynamicType(Type type) {
        elementType = (Generics.getRawType(type)).getComponentType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object newInstance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Property getProperty(String name) {
        return new PropertyImpl(Integer.parseInt(name), elementType);
    }

    @Override
    public Collection<Property> getProperties() {
        throw new UnsupportedOperationException();
    }

    private static class PropertyImpl implements Property {
        private Type elementType;
        private int index;

        public PropertyImpl(int index, Type elementType) {
            this.elementType = elementType;
            this.index = index;
        }

        @Override
        public Object getValue(Object instance) {
            if (Array.getLength(instance) <= index) {
                return null;
            }
            return Array.get(instance, index);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void setValue(Object instance, Object value) {
            Array.set(instance, index, value);
        }

        @Override
        public Type getType() {
            return elementType;
        }

        @Override
        public String getName() {
            return index + "";
        }
    }
}
