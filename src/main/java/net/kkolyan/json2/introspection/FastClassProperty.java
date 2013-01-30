package net.kkolyan.json2.introspection;

import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public class FastClassProperty implements Property {
    private String displayName;
    private Field field;
    private FastMethod getter;
    private FastMethod setter;
    private Type type;
    private String name;

    public FastClassProperty(String name, String displayName, Field field, FastMethod getter, FastMethod setter) {
        this.name = name;
        this.displayName = displayName;
        this.field = field;
        this.getter = getter;
        this.setter = setter;

        if (getter != null) {
            type = getter.getJavaMethod().getGenericReturnType();
        } else if (setter != null) {
            type = setter.getJavaMethod().getGenericParameterTypes()[0];
        } else if (field != null) {
            type = field.getGenericType();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public Object getValue(Object instance) {
        try {
            if (getter != null) {
                return getter.invoke(instance, new Object[0]);
            }
            if (field != null) {
                field.setAccessible(true);
                return field.get(instance);
            }
            throw new IllegalStateException(displayName + " is write-only property");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void setValue(Object instance, Object value) {
        try {
            if (setter != null) {
                setter.invoke(instance, new Object[] {value});
            } else if (field != null){
                field.setAccessible(true);
                field.set(instance, value);
            } else {
                throw new IllegalStateException(displayName + " is read-only property");
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
