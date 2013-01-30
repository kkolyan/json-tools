package net.kkolyan.json2.introspection;

import net.kkolyan.json2.util.Generics;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nplekhanov
 */
public class PlainObjectDynamicType implements DynamicType {
    private FastClass fastClass;
    private Map<String, Property> properties = new HashMap<String, Property>();

    public PlainObjectDynamicType(Type type) {
        fastClass = FastClass.create(Generics.getRawType(type));
        for (Property property : Introspection.extractProperties(fastClass)) {
            String name = property.getName().toLowerCase();
            properties.put(name, property);
        }
    }

    @Override
    public Object newInstance() {
        try {
            return fastClass.newInstance();
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Property getProperty(String name) {
        Property property = properties.get(name.toLowerCase());
        if (property == null) {
            throw new IllegalStateException("property doesn't exist: " + fastClass.getName() + "#" + name);
        }
        return property;
    }

    @Override
    public Collection<Property> getProperties() {
        return properties.values();
    }
}
