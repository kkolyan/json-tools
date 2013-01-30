package net.kkolyan.json2.evaluation;

import net.kkolyan.json2.introspection.DynamicTypeFactory;
import net.kkolyan.json2.introspection.Introspection;
import net.kkolyan.json2.introspection.Property;
import net.kkolyan.json2.parsing.Location;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author nplekhanov
 */
public class MemberExpression implements Expression {
    private Object instance;
    private String member;
    private Location location;
    private DynamicTypeFactory dynamicTypes;

    public MemberExpression(Object instance, String member, Location location, DynamicTypeFactory dynamicTypes) {
        this.instance = instance;
        this.member = member;
        this.location = location;
        this.dynamicTypes = dynamicTypes;
    }

    private Method getMethod(Class aClass, String name, int arity) {
        for (Method method : aClass.getMethods()) {
            if (method.getName().equalsIgnoreCase(name)) {
                return method;
            }
        }
        throw new IllegalStateException();
    }

    private Property getProperty() {
        if (instance == null) {
            throw new NullPointerException("null-pointer at " + location);
        }
        return dynamicTypes.getDynamicType(instance.getClass()).getProperty(member);
    }

    @Override
    public Callable getAsCallable(int arity) {
        if (instance == null) {
            throw new IllegalStateException("null-pointer at " + location);
        }
        for (Method method : instance.getClass().getMethods()) {
            if (method.getName().equalsIgnoreCase(member)) {

                final Type[] types = method.getGenericParameterTypes();
                return new Callable() {
                    @Override
                    public Object call(Object[] args) {
                        return Introspection.invokeMethod(instance, member, args);
                    }

                    @Override
                    public Type getArgType(int index) {
                        return types[index];
                    }
                };
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public void setValue(Object value) {
        getProperty().setValue(instance, value);
    }

    @Override
    public Object getValue() {
        return getProperty().getValue(instance);
    }

    @Override
    public Type getDesiredType() {
        return getProperty().getType();
    }

    @Override
    public String toString() {
        return "MemberExpression{" + instance +
                "#" + member +  " at=" + location + '}';
    }
}
