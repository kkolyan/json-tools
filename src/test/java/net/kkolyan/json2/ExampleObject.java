package net.kkolyan.json2;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Map;

/*
{
   "stringKey":"stringValue",
   "integerKey":123,
   "floatKey":123.456,
   "trueKey":true,
   "falseKey":false,
   "nullKey":null,
   "emptyArrayKey":[],
   "arrayKey":[
       {"subKey":"subValue1","a":["a","b","c"]},
       {"subKey":"subValue2","a":["d","e","f"]}

   ],
   "emptyObjectKey":{},
   "objectKey":{
       "aKey":"aValue",
       "bKey":"bValue"
   },
   "stringKey2":"This is a good day to test something"
}
*/
public class ExampleObject {
    private String stringKey;
    private int integerKey;
    private double floatKey;
    private boolean trueKey;
    private boolean falseKey;
    private Object nullKey;
    private List<String> emptyArrayKey;
    private List<SubObject> arrayKey;
    protected EmptyObject emptyObjectKey;
    private Object1 objectKey;
    private Map<String,String> mapKey;
    private String stringKey2;
    private ElementType elementType;

    public String getStringKey() {
        return stringKey;
    }

    public void setStringKey(String stringKey) {
        this.stringKey = stringKey;
    }

    public int getIntegerKey() {
        return integerKey;
    }

    public void setIntegerKey(int integerKey) {
        this.integerKey = integerKey;
    }

    public double getFloatKey() {
        return floatKey;
    }

    public void setFloatKey(double floatKey) {
        this.floatKey = floatKey;
    }

    public boolean isTrueKey() {
        return trueKey;
    }

    public void setTrueKey(boolean trueKey) {
        this.trueKey = trueKey;
    }

    public boolean isFalseKey() {
        return falseKey;
    }

    public void setFalseKey(boolean falseKey) {
        this.falseKey = falseKey;
    }

    public Object getNullKey() {
        return nullKey;
    }

    public void setNullKey(Object nullKey) {
        this.nullKey = nullKey;
    }

    public List<String> getEmptyArrayKey() {
        return emptyArrayKey;
    }

    public void setEmptyArrayKey(List<String> emptyArrayKey) {
        this.emptyArrayKey = emptyArrayKey;
    }

    public List<SubObject> getArrayKey() {
        return arrayKey;
    }

    public void setArrayKey(List<SubObject> arrayKey) {
        this.arrayKey = arrayKey;
    }

    public Object1 getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Object1 objectKey) {
        this.objectKey = objectKey;
    }

    public String getStringKey2() {
        return stringKey2;
    }

    public void setStringKey2(String stringKey2) {
        this.stringKey2 = stringKey2;
    }

    public EmptyObject getEmptyObjectKey() {
        return emptyObjectKey;
    }

    public void setEmptyObjectKey(EmptyObject emptyObjectKey) {
        this.emptyObjectKey = emptyObjectKey;
    }

    public Map<String, String> getMapKey() {
        return mapKey;
    }

    public void setMapKey(Map<String, String> mapKey) {
        this.mapKey = mapKey;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }
}
