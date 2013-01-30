package net.kkolyan.json2.introspection;

import net.kkolyan.json2.util.Generics;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author nplekhanov
 */
public class ListDynamicType implements DynamicType {
    private Type elementType;
    private Class rawElementType;

    public ListDynamicType(Type type) {
        elementType = Generics.getGenericTypeParameter(type, 0);
        if (elementType != null) {
            rawElementType = Generics.getRawType(elementType);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object newInstance() {
        if (rawElementType == null) {
            return new ArrayList();
        }
        return Collections.checkedList(new ArrayList(), rawElementType);
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
            List list = (List) instance;
            if (list.size() <= index) {
                return null;
            }
            return list.get(index);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void setValue(Object instance, Object value) {
            List list = (List) instance;
            while (list.size() <= index) {
                list.add(null);
            }
            list.set(index, value);
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
