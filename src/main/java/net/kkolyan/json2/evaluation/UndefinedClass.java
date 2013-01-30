package net.kkolyan.json2.evaluation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
* @author nplekhanov
*/
class UndefinedClass {
    private static final Map<String,UndefinedClass> map = null;
    private static final List<UndefinedClass> list = null;

    static final Type RAW_MAP_TYPE;
    static final Type RAW_LIST_TYPE;

    static {
        try {
            RAW_MAP_TYPE = UndefinedClass.class.getDeclaredField("map").getGenericType();
            RAW_LIST_TYPE = UndefinedClass.class.getDeclaredField("list").getGenericType();
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }
}
