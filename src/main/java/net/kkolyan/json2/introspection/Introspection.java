package net.kkolyan.json2.introspection;

import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author nplekhanov
 */
public class Introspection {

    public static Object invokeMethod(Object instance, String name, Object[] args) {
        if (instance == null) {
            throw new NullPointerException();
        }
        for (Method method : instance.getClass().getMethods()) {
            if (method.getName().equalsIgnoreCase(name) && method.getParameterTypes().length == args.length) {
                try {
                    return method.invoke(instance, args);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                } catch (InvocationTargetException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        throw new IllegalStateException();
    }

    public static Property createProperty(FastClass fastClass, String name) {
        Class rawClass = fastClass.getJavaClass();

        Method setter = null;
        Method getter = null;
        Field field = null;

        for (Method method : rawClass.getMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            String n = method.getName();
            if (n.equalsIgnoreCase("set" + name) && parameterTypes.length == 1) {
                setter = method;
            }
            if ((n.equalsIgnoreCase("is" + name) || n.equalsIgnoreCase("get" + name)) && parameterTypes.length == 0) {
                getter = method;
            }
            if (setter != null && getter != null) {
                break;
            }
        }
        if (setter == null || getter == null) {
            for (Class c = rawClass; c != Object.class; c = c.getSuperclass()) {
                for (Field f : c.getDeclaredFields()) {
                    if (f.getName().equalsIgnoreCase(name)) {
                        field = f;
                        break;
                    }
                }
            }
        }
        String displayName = rawClass.getName() + "#" + name;
        if (field == null && getter == null && setter == null) {
            throw new IllegalStateException(displayName);
        }
        return new FastClassProperty(name, displayName, field, getter == null ? null : fastClass.getMethod(getter), setter == null ? null : fastClass.getMethod(setter));
    }

    public static Collection<Property> extractProperties(FastClass fastClass) {
        Class beanClass = fastClass.getJavaClass();

        List<Property> properties = new ArrayList<Property>();

        class Prop {
            String name;
            Field field;
            Method getter;
            Method setter;
        }
        Map<String, Prop> propMap = new HashMap<String, Prop>() {
            @Override
            public Prop get(Object key) {
                Prop p = super.get(key.toString().toLowerCase());
                if (p == null) {
                    p = new Prop();
                    p.name = (String) key;
                    put(key.toString().toLowerCase(), p);
                }
                return p;
            }

            @Override
            public Prop put(String key, Prop value) {
                return super.put(key.toLowerCase(), value);
            }
        };

        for (Class c = beanClass; c != Object.class; c = c.getSuperclass()) {
            for (Field field: c.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Prop p = propMap.get(field.getName());
                p.field = field;
            }
        }

        for (Method method: beanClass.getMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            if (method.getParameterTypes().length == 0) {
                if (method.getName().startsWith("get")) {
                    if (method.getName().equals("getClass")) {
                        continue;
                    }
                    Prop p = propMap.get(methodToProperty(method.getName()));
                    p.getter = method;
                }
                if (method.getName().startsWith("is")) {
                    Prop p = propMap.get(methodToProperty(method.getName()));
                    p.getter = method;
                }
            }
            if (method.getParameterTypes().length == 1) {
                if (method.getName().startsWith("set")) {
                    Prop p = propMap.get(methodToProperty(method.getName()));
                    p.setter = method;
                }
            }
        }

        for (Prop p: propMap.values()) {
            properties.add(new FastClassProperty(
                    p.name, beanClass.getName() + "#" + p.name, p.field,
                    p.getter == null ? null : fastClass.getMethod(p.getter),
                    p.setter == null ? null : fastClass.getMethod(p.setter)));
        }

        return properties;
    }

    private static String methodToProperty(String s) {
        if (s.startsWith("get") || s.startsWith("set")) {
            s = s.substring(3);
        } else if (s.startsWith("is")) {
            s = s.substring(2);
        } else {
            throw new IllegalArgumentException(s);
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

}
