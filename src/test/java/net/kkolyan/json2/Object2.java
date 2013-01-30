package net.kkolyan.json2;

/**
 * @author nplekhanov
 */
public class Object2 {
    public String name;

    public int getNameLength() {
        return name == null ? 0 : name.length();
    }
}
