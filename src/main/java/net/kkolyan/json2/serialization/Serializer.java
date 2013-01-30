package net.kkolyan.json2.serialization;

import net.kkolyan.json2.introspection.DynamicTypeFactory;
import net.kkolyan.json2.introspection.Property;
import net.kkolyan.json2.util.Escaping;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author nplekhanov
 */
public class Serializer {
    private DynamicTypeFactory dynamicTypeFactory = new DynamicTypeFactory();

    public void serialize(Object o, Appendable out) throws IOException {
        if (o == null) {
            out.append("null");
        }
        else if (Types.isAsIs(o.getClass())) {
            out.append(o.toString());
        }
        else if (Types.isQuoted(o.getClass())) {
            out.append('"').append(Escaping.escapedString(o.toString(), '"')).append('"');
        }
        else if (o instanceof Class) {
            out.append('"').append(((Class) o).getCanonicalName()).append('"');
        }
        else if (o instanceof Map) {
            out.append("{");
            int n = 0;
            for (Map.Entry<?, ?> entry: ((Map<?,?>) o).entrySet()) {
                if (n++ > 0) {
                    out.append(",");
                }
                out.append('"').append(entry.getKey().toString()).append('"').append(":");
                serialize(entry.getValue(), out);
            }
            out.append("}");
        }
        else if (o instanceof Collection) {
            out.append("[");
            int n = 0;
            for (Object element: ((Collection) o)) {
                if (n++ > 0) {
                    out.append(",");
                }
                serialize(element, out);
            }
            out.append("]");
        }
        else if (o instanceof Object[]) {
            out.append("[");
            int n = 0;
            for (Object element: ((Object[]) o)) {
                if (n++ > 0) {
                    out.append(",");
                }
                serialize(element, out);
            }
            out.append("]");
        }
        else {
            out.append("{");
            Collection<Property> properties = dynamicTypeFactory.getDynamicType(o.getClass()).getProperties();
            final int[] n = new int[] {0};
            for (final Property property: properties) {
                if (n[0]++ > 0) {
                    out.append(",");
                }
                out.append('"').append(property.getName()).append('"').append(":");
                Object value = property.getValue(o);
                serialize(value, out);
            }
            out.append("}");
        }
    }

    public String serialize(Object o) {
        StringBuilder s = new StringBuilder();
        try {
            serialize(o, s);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return s.toString();
    }

    public void setDynamicTypeFactory(DynamicTypeFactory dynamicTypeFactory) {
        this.dynamicTypeFactory = dynamicTypeFactory;
    }
}
