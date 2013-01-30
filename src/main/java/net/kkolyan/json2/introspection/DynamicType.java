package net.kkolyan.json2.introspection;

import java.util.Collection;

/**
 * @author nplekhanov
 */
public interface DynamicType {
    Object newInstance();
    Property getProperty(String name);
    Collection<Property> getProperties();
}
